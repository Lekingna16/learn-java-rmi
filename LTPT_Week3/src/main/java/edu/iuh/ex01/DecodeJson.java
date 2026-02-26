package edu.iuh.ex01;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DecodeJson {
    public static void main(String[] args) {

        String fileName = "src/main/resources/json/emp.json";
        Employee emp = readFromFile (fileName);

        System.out.println(emp);
    }

    public static Employee readFromFile(String fileName) {

        try (
                FileReader fileReader = new FileReader(fileName);
                JsonReader jsonReader = Json.createReader(fileReader);

                ) {
            JsonObject jsonObject = jsonReader.readObject();

            Employee employee = new Employee();
            employee.setId(jsonObject.getJsonNumber("id").longValue());
            employee.setName(jsonObject.getString("name"));
            employee.setSalary(jsonObject.getJsonNumber("salary").doubleValue());

            return employee;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
