package com.mng.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonBuilder {

    ObjectNode json;

    private JsonBuilder() {
        this.json = JsonNodeFactory.instance.objectNode();
    }

    public static JsonBuilder newObject() {
        return new JsonBuilder();
    }

    public static ArrayBuilder newArray() {
        return new JsonBuilder.ArrayBuilder();
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

    public ObjectNode build() {
        return json;
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public static class ArrayBuilder {
        ArrayNode json;

        private ArrayBuilder() {
            this.json = JsonNodeFactory.instance.arrayNode();
        }

        public JsonBuilder.ArrayBuilder put(JsonNode element) {
            json.add(element);
            return this;
        }


        public JsonBuilder.ArrayBuilder put(int element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(double element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(float element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(boolean element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(byte element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(char element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(short element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(long element) {
            json.add(element);
            return this;
        }

        public JsonBuilder.ArrayBuilder put(String element) {
            json.add(element);
            return this;
        }

        @Override
        public String toString() {
            return json.toString();
        }

        public ArrayNode build() {
            return json;
        }

        public ArrayNode buildAsArray() {
            return json;
        }
    }
}
