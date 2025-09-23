package org.example.xmltojson;

import org.example.xmltojson.model.Flower;
import org.example.xmltojson.service.FlowerConversionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SpringBootApplication
public class XmlToJsonApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(XmlToJsonApplication.class);
    private final FlowerConversionService conversionService;

    public XmlToJsonApplication(FlowerConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(XmlToJsonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // XML to JSON
        Flower flowerFromXml = conversionService.readXml(new ClassPathResource("static/flower.xml"));
        String json = conversionService.toJson(flowerFromXml);
        log.info("This is JSON: {}", json);

        // JSON to XML
        Flower flowerFromJson = conversionService.readJson(new ClassPathResource("static/flower.json"));
        String xml = conversionService.toXml(flowerFromJson);
        log.info("This is XML: {}", xml);
    }
}
