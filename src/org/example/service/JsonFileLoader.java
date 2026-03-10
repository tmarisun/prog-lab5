package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.data.*;
import org.example.data.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Stack;


public class JsonFileLoader {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static Stack<City> loadCollection(String filename) throws IOException {

        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("File : " + filename + " not found. Please check the file path.");
        }

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        File jsonFile = new File(filename);

        return mapper.readValue(jsonFile, new TypeReference<Stack<City>>() {});

    }

}