package org.example.xml_to_json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.xml_to_json.model.Flower;
import org.example.xml_to_json.service.FlowerConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class XmlToJsonApplication implements CommandLineRunner {

    @Autowired
    private FlowerConversionService conversionService;

    public static void main(String[] args) throws IOException {

        SpringApplication.run(XmlToJsonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // XML to JSON
        Flower flowerFromXml = conversionService.readXml(new ClassPathResource("static/flower.xml"));
        String json = conversionService.toJson(flowerFromXml);
        System.out.println("This is JSON: " + json);

        // JSON to XML
        Flower flowerFromJson = conversionService.readJson(new ClassPathResource("static/flower.json"));
        String xml = conversionService.toXml(flowerFromJson);
        System.out.println("This is XML: " + xml);
    }
}
