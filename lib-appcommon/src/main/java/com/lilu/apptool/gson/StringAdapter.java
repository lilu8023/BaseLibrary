package com.lilu.apptool.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
public class StringAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) {

        try {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String read(JsonReader in) {

        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            } else {
                return in.nextString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
