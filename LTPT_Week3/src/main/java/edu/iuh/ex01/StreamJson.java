package edu.iuh.ex01;

import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StreamJson {

    public static void main(String[] args) {

        Employee employee = new Employee(23729131, "Le Thi Kim Ngan", 5000000);

       String fileName = "src/main/resources/json/empStream.json";
//        writeStreamJson (employee, fileName);

        Employee emp = readFromFileStream(fileName);
        System.out.println(emp);
    }

    private static Employee readFromFileStream(String fileName) {
        String currentKey = "";
        Employee emp = new Employee();
        try (
                FileReader fileReader = new FileReader(fileName);
                JsonParser parser = Json.createParser(fileReader);

                ) {
            while (parser.hasNext()) {

                JsonParser.Event event = parser.next();
                switch (event) {
                    case KEY_NAME :
                        currentKey = parser.getString();
                        break;
                    case VALUE_STRING:
                        if ("name".equals(currentKey)){
                            emp.setName(parser.getString());

                        }
                        break;
                    case VALUE_NUMBER:
                        if ("id".equals(currentKey)){
                            emp.setId(parser.getLong());
                        }
                        if ("salary".equals(currentKey)){
                            emp.setSalary(parser.getBigDecimal().doubleValue());
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emp;
    }

    private static void writeStreamJson(Employee employee, String fileName) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                JsonGenerator generator = Json.createGenerator(fileWriter);
                ) {
            generator.writeStartObject()
                    .write("id", employee.getId())
                    .write("name", employee.getName())
                    .write("salary", employee.getSalary())
                    .writeEnd();
            System.out.println("Successful!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
