package edu.iuh.infrastructure.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.iuh.infrastructure.mapper.GenericDataMapper;

import java.util.Map;

public class JacksonDataMapper implements GenericDataMapper {
    private ObjectMapper mapper;

    public JacksonDataMapper() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Map<String, Object> toMap(Object object) {
        if (object == null)
            return null;
        return mapper.convertValue(object, Map.class);
    }

    @Override
    public <T> T toObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null)
            return null;
        return mapper.convertValue(map, clazz);

    }
}
