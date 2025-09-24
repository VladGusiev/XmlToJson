package org.example.xmltojson.controller;

import org.example.xmltojson.service.FlowerConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Provides two ways to convert XML to JSON:
 * - REST: POST /api/convert/xml-to-json (returns application/json)
 * - MVC (no JavaScript needed): POST /convert (renders index view with output filled)
 */
@Controller
public class ConvertController {

    private final FlowerConversionService flowerConversionService;

    public ConvertController(FlowerConversionService flowerConversionService) {
        this.flowerConversionService = flowerConversionService;
    }

    // REST endpoint: XML string -> JSON string
    @PostMapping(path = "/api/convert/xml-to-json",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, MediaType.TEXT_PLAIN_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> xmlToJson(@RequestBody String xml) {
        if (xml == null || xml.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Request body is empty. Please provide XML.");
        }
        try {
            var flower = flowerConversionService.readXml(xml);
            var json = flowerConversionService.toJson(flower);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(json);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Invalid XML or conversion error: " + e.getMessage());
        }
    }

    // MVC form post: renders the same index view with the output pre-filled (no JS required)
    @PostMapping(path = "/convert", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String convertForm(@RequestParam(name = "input", required = false) String xml,
                              Model model) {
        String output;
        if (xml == null || xml.isBlank()) {
            output = "Please paste XML first.";
        } else {
            try {
                var flower = flowerConversionService.readXml(xml);
                output = flowerConversionService.toJson(flower);
            } catch (IOException e) {
                output = "Invalid XML or conversion error: " + e.getMessage();
            }
        }
        model.addAttribute("input", xml == null ? "" : xml);
        model.addAttribute("output", output);
        return "index";
    }
}
