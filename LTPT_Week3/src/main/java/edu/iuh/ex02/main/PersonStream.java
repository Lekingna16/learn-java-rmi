package edu.iuh.ex02.main;

import edu.iuh.ex02.entity.Address;
import edu.iuh.ex02.entity.Person;
import edu.iuh.ex02.entity.PhoneNumber;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonStream {

    public static void main(String[] args) {

        String fileName = "src/main/resources/json/person.json";
        Person person = readFromFileStream(fileName);
        String filePath = "src/main/resources/json/person2.json";
        writeToFileStream(person, filePath);

    }

    public static Person readFromFileStream (String fileName) {
        Person person = new Person();
        String currentKey = "";
        String context = "person";
        Address address = new Address();
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber currentPhone = null;

        try (
                FileReader fileReader = new FileReader(fileName);
                JsonParser parser = Json.createParser(fileReader);
                ) {
            while (parser.hasNext()){
                JsonParser.Event event = parser.next();
                switch (event){
                    case KEY_NAME:
                        currentKey = parser.getString();
                        if ("address".equals(currentKey)){
                            context = "address";
                        }
                        else if ("phoneNumbers".equals(currentKey)){
                            context = "phoneNumbers";
                        }
                        break;
                    case START_OBJECT:
                        if ("phoneNumbers".equals(context)) {
                            currentPhone = new PhoneNumber();
                            context = "phoneObject";
                        }
                        break;
                    case VALUE_STRING:
                        if ("person".equals(context)){
                            if ("firstName".equals(currentKey))
                                person.setFirstName(parser.getString());
                            else if ("lastName".equals(currentKey))
                                person.setLastName(parser.getString());
                        }
                        else if ("address".equals(context)){
                            if ("streetAddress".equals(currentKey))
                                address.setStreetAddress(parser.getString());
                            else if ("city".equals(currentKey))
                                address.setCity(parser.getString());
                            else if ("state".equals(currentKey))
                                address.setState(parser.getString());
                            else if ("postalCode".equals(currentKey))
                                address.setPotalCode(parser.getInt());
                        }
                        else if ("phoneObject".equals(context)){
                            if ("type".equals(currentKey))
                                currentPhone.setType(parser.getString());
                            else if ("number".equals(currentKey))
                                currentPhone.setNumber(parser.getString());
                        }
                        break;
                    case VALUE_NUMBER:
                       if ("person".equals(context) && "age".equals(currentKey)){
                           person.setAge(parser.getInt());
                       }
                       break;
                    case END_OBJECT:
                        if ("address".equals(context)){
                            person.setAddress(address);
                            context = "person";
                        }
                        else if ("phoneObject".equals(context)){
                            phoneNumbers.add(currentPhone);
                            context = "phoneNumbers";
                        }
                        break;
                    case END_ARRAY:
                        if ("phoneNumbers".equals(context)){
                            person.setPhoneNumbers(phoneNumbers);
                            context = "person";
                        }
                        break;
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public static  void writeToFileStream (Person person, String fileName) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                JsonGenerator generator = Json.createGenerator(fileWriter);
                ) {
generator.writeStartObject()
        .write("firstName", person.getFirstName())
        .write("lastName", person.getLastName())
        .write("age", person.getAge());
generator.writeStartObject("address")
        .write("streetAddress", person.getAddress().getStreetAddress())
        .write("city", person.getAddress().getCity())
        .write("state", person.getAddress().getState())
        .write("postalCode", person.getAddress().getPotalCode())
        .writeEnd();
generator.writeStartArray("phoneNumbers");
for (PhoneNumber phoneNumber : person.getPhoneNumbers()){
    generator.writeStartObject()
            .write("type", phoneNumber.getType())
            .write("number", phoneNumber.getNumber())
            .writeEnd();
}
generator.writeEnd();
generator.writeEnd();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
