package edu.iuh.ex01;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;

public class EncodeJson {
    public static void main(String[] args) {
        Employee emp = new Employee(23729131, "Le Thi Kim Ngan", 123000);

        JsonObject json = toJson(emp);

        String filePath = "src/main/resources/json/emp.json";
        writeToFile (json, filePath);



    }

    private static void writeToFile(JsonObject json, String filePath) {

        try (
                FileWriter fileWriter = new FileWriter(filePath);
                JsonWriter jsonWriter = Json.createWriter(fileWriter);
                ) {
            jsonWriter.writeObject(json);
            System.out.println("Successful!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonObject toJson(Employee emp) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", emp.getId())
                .add("name", emp.getName())
                .add("salary", emp.getSalary())
                .build();
        return jsonObject;


    }
}
