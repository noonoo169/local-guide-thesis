package com.example.localguidebe.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;

public class JsonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String convertObjectToJson(Object obj) throws Exception {
    return objectMapper.writeValueAsString(obj);
  }

  public static <T> T convertJsonToObject(String json, Class<T> clazz) throws Exception {
    return objectMapper.readValue(json, clazz);
  }

  public static <T> List<T> convertJsonToList(String json, Class<T> clazz) throws Exception {
    return objectMapper.readValue(
        json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
  }

  public static <T> Set<T> convertJsonToSet(String json, Class<T> clazz) throws Exception {
    return objectMapper.readValue(
        json, objectMapper.getTypeFactory().constructCollectionType(Set.class, clazz));
  }

  public static <T> T convertJsonToObject(String json, TypeReference<T> typeReference)
      throws Exception {
    return objectMapper.readValue(json, typeReference);
  }
}
