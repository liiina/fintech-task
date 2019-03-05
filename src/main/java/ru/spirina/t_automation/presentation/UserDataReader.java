package ru.spirina.t_automation.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserDataReader {

    public List<String> readStringParameters(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            String str;
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
        } catch (IOException exception) {
            System.out.println("Exception while reading file: " + exception);
        }
        return list;
    }
}
