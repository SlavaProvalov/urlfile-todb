package com.provalov.urlFileReader.util;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viacheslav Provalov
 */

@Service
public class EntityReader {
    private static final String UTF8_BOM = "\uFEFF";

    public static List<String> readUnicodeJava11(String fileName) {

        List<String> rows = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) {
                    rows.add(line);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return rows;
    }

    @SneakyThrows
    public static List<String> readUFromUrl(String fileName) {
        List<String> rows = new ArrayList<>();
        URL oracle = new URL(fileName);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.equals("")) {
                    rows.add(removeUTF8BOM(inputLine));
                }
            }
        }
        return rows;
    }

    private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }
}
