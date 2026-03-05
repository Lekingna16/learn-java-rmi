package edu.iuh.insfractructure.mapper;

import java.util.Map;

public interface GenericDataMapper {
    Map<String, Object> toMap(Object object);
    <T> T toObject(Map<String, Object> data, Class<T> clazz);
}
