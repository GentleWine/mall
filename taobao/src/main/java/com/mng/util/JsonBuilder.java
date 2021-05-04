package com.mng.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 用于创建JSON
 */
@SuppressWarnings("unused")
public class JsonBuilder {

    private final JsonObject json;

    private JsonBuilder() {
        this.json = new JsonObject();
    }

    public static JsonBuilder newObject() {
        return new JsonBuilder();
    }

    public static ArrayBuilder newArray() {
        return new ArrayBuilder();
    }

    public JsonBuilder put(String key, JsonElement element) {
        json.add(key, element);
        return this;
    }

    public JsonBuilder put(String key, int element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, short element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, byte element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, long element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, double element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, float element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, char element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, boolean element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, String element) {
        json.addProperty(key, element);
        return this;
    }

    public JsonBuilder put(String key, Enum<?> element) {
        json.addProperty(key, element.toString());
        return this;
    }

    public JsonBuilder put(String key, Object element) {
        json.addProperty(key, element.toString());
        return this;
    }

    public String build() {
        return json.toString();
    }

    public JsonObject buildAsJsonObject() {
        return json;
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public static class ArrayBuilder {

        private final JsonArray json;

        private ArrayBuilder() {
            this.json = new JsonArray();
        }

        public ArrayBuilder put(JsonElement element) {
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

        public String build() {
            return json.toString();
        }

        public JsonArray buildAsJsonArray() {
            return json;
        }
    }
}
