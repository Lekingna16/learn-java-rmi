package edu.iuh.infrastructure.mapper;

import java.util.Map;

public interface GenericDataMapper {

    Map<String, Object> toMap (Object object);

    <T> T toObject (Map<String, Object> map, Class<T> clazz);
}
