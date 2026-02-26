package edu.iuh.ex02.main;

import edu.iuh.ex02.entity.Address;
import edu.iuh.ex02.entity.Person;
import edu.iuh.ex02.entity.PhoneNumber;
import jakarta.json.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class ObjectModel {
    public static void main(String[] args) {

        String fileName = "src/main/resources/json/person.json";
        Person person = readFromFile (fileName);
        System.out.println(person);

        String file = "src/main/resources/json/personWrite.json";
        JsonObject personObject = toJson(person);
        writeToFile(personObject, file);



    }

    private static JsonObject toJson (Person person) {
        JsonObject addressObject = Json.createObjectBuilder()
                .add("streetAddress", person.getAddress().getStreetAddress())
                .add("city", person.getAddress().getCity())
                .add("state", person.getAddress().getState())
                .add("postalCode", person.getAddress().getPotalCode())
                .build();
        List<PhoneNumber> phones = person.getPhoneNumbers();
        JsonArrayBuilder phoneArray = Json.createArrayBuilder();

        for (PhoneNumber phoneNumber : phones) {
            JsonObject phoneObject = Json.createObjectBuilder()
                    .add ("type", phoneNumber.getType())
                    .add ("number", phoneNumber.getNumber())
                    .build();
            phoneArray.add(phoneObject);
        }

        JsonObject personObject = Json.createObjectBuilder()
                .add("firstName", person.getFirstName())
                .add("lastName", person.getLastName())
                .add("address", addressObject)
                .add("phoneNumbers", phoneArray)
                .build();
        return personObject;
    }

    private static void writeToFile (JsonObject person, String file) {
        try (
                FileWriter fileWriter = new FileWriter(file);
                JsonWriter jsonWriter = Json.createWriter(fileWriter);
                ) {
jsonWriter.writeObject(person);
            System.out.println("Success!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static Person readFromFile (String fileName) {
        Person person = new Person();
        try (
                FileReader fileReader = new FileReader(fileName);
                JsonReader jsonReader = Json.createReader(fileReader);

                ) {
            JsonObject jsonObject = jsonReader.readObject();
            person.setFirstName(jsonObject.getString("firstName"));
            person.setLastName(jsonObject.getString("lastName"));
            person.setAge(jsonObject.getJsonNumber("age").intValue());
            JsonObject addressObject = jsonObject.getJsonObject("address");
            Address address = new Address();

            address.setStreetAddress(addressObject.getString("streetAddress"));
            address.setCity(addressObject.getString("city"));
            address.setState(addressObject.getString("state"));
            address.setPotalCode(addressObject.getJsonNumber("postalCode").intValue());

            person.setAddress(address);

            JsonArray jsonArray = jsonObject.getJsonArray("phoneNumbers");
            List<PhoneNumber> phoneNumbers = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                PhoneNumber phoneNumber = new PhoneNumber();
                JsonObject phoneObject = jsonArray.getJsonObject(i);
                phoneNumber.setType(phoneObject.getString("type"));
                phoneNumber.setNumber(phoneObject.getString("number"));
                phoneNumbers.add(phoneNumber);
            }

            person.setPhoneNumbers(phoneNumbers);

            return person;



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
