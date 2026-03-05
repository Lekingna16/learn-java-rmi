package edu.iuh.insfractructure.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.iuh.insfractructure.mapper.GenericDataMapper;

import java.util.Map;

public class JacksonDataMapper implements GenericDataMapper {
    private ObjectMapper objectMapper;

    public JacksonDataMapper(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Object> toMap(Object object) {
        if(object == null){
            return null;
        }
        return objectMapper.convertValue(object, Map.class);
    }

    @Override
    public <T> T toObject(Map<String, Object> data, Class<T> clazz) {
        if(data == null)
            return null;
        return objectMapper.convertValue(data, clazz);
    }
}
