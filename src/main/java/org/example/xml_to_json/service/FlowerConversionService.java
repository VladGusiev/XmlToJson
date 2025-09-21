package org.example.xml_to_json.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.example.xml_to_json.model.Flower;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FlowerConversionService {

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public FlowerConversionService(ObjectMapper objectMapper, XmlMapper xmlMapper) {
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    public Flower readXml(Resource xmlResource) throws IOException {
        try (InputStream is = xmlResource.getInputStream()) {
            return xmlMapper.readValue(is, Flower.class);
        }
    }

    public Flower readJson(Resource jsonResource) throws IOException {
        try (InputStream is = jsonResource.getInputStream()) {
            return objectMapper.readValue(is, Flower.class);
        }
    }

    public String toJson(Flower flower) throws IOException {
        return objectMapper.writeValueAsString(flower);
    }

    public String toXml(Flower flower) throws IOException {
        return xmlMapper.writeValueAsString(flower);
    }
}
