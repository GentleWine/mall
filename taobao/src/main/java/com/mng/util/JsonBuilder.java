package com.mng.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 用于创建JSON
 */
public class JsonBuilder {

    private final ObjectNode json;

    private JsonBuilder() {
        this.json = JsonNodeFactory.instance.objectNode();
    }

    public static JsonBuilder newObject() {
        return new JsonBuilder();
    }

    public static ArrayBuilder newArray() {
        return new ArrayBuilder();
    }

    public JsonBuilder put(String key, JsonNode element) {
        json.set(key, element);
        return this;
    }

    public JsonBuilder put(String key, int element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, short element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, byte element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, long element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, double element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, float element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, char element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, boolean element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, String element) {
        json.put(key, element);
        return this;
    }

    public JsonBuilder put(String key, Enum<?> element) {
        json.put(key, element.toString());
        return this;
    }

    public JsonBuilder put(String key, Object element) {
        json.put(key, element.toString());
        return this;
    }

    public ObjectNode build() {
        return json;
    }

    public JSONObject buildAsJsonObject() {
        return JSON.parseObject(this.toString());
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public static class ArrayBuilder {

        private final ArrayNode json;

        private ArrayBuilder() {
            this.json = JsonNodeFactory.instance.arrayNode();
        }

        public ArrayBuilder put(JsonNode element) {
            json.add(element);
            return this;
        }


        public ArrayBuilder put(int element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(double element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(float element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(boolean element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(byte element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(char element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(short element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(long element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(String element) {
            json.add(element);
            return this;
        }

        public ArrayBuilder put(Enum<?> element) {
            json.add(element.toString());
            return this;
        }

        public ArrayBuilder put(Object element) {
            json.add(element.toString());
            return this;
        }

        @Override
        public String toString() {
            return json.toString();
        }

        public ArrayNode build() {
            return json;
        }

        public JSONArray buildAsJsonArray() {
            return JSON.parseArray(this.toString());
        }
    }
}
