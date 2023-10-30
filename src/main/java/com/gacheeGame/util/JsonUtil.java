package com.gacheeGame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gacheeGame.handler.CustomBadRequestException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil
{
    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper)
    {
        JsonUtil.objectMapper = objectMapper;
    }

    // Json 문자열을 JsonObject로 변환
    public static JsonNode jsonStringToJsonObject(String input)
    {
        JsonNode returnValue;

        try
        {
            returnValue =  objectMapper.readTree(input);
        }
        catch (JsonMappingException e)
        {
            throw new RuntimeException(e);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }

        return returnValue;
    }

    public static ObjectNode ObjectToJsonObject(String key, Object value)
    {
        try
        {
            ObjectNode returnValue = JsonNodeFactory.instance.objectNode();
            returnValue.putPOJO(key, value);

            return returnValue;
        } catch (Exception e)
        {
            throw new CustomBadRequestException("Object를 Json으로 변환하는 과정에서 오류가 발생하였습니다.");
        }
    }

    public static ObjectNode ObjectToJsonObject(Map<String, Object> map)
    {
        try
        {
            ObjectNode returnValue = JsonNodeFactory.instance.objectNode();
            returnValue = objectMapper.valueToTree(map);

            return returnValue;
        } catch (Exception e)
        {
            throw new CustomBadRequestException("Map을 Json으로 변환하는 과정에서 오류가 발생하였습니다.");
        }
    }
}
