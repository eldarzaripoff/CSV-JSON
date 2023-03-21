package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        String fileName = "data.csv";

        List<Employee> list = parseCSV(columnMapping, fileName);

        Type listType = new TypeToken<List<T>>() {}.getType();

        String json = listToJson(list, listType);

        //String result = writeString(json);
        writeString(json);

        System.out.println(json);



    }

    private static void writeString(String json) {
        try(FileWriter file = new FileWriter("new_data.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> list, Type listType) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        builder.setPrettyPrinting();
        String json = gson.toJson(list, listType);
        return json;
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> staff = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            staff = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staff;
    }
}