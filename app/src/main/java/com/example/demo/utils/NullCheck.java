package com.example.demo.utils;

import java.util.HashMap;
import java.util.UUID;

public class NullCheck {

    /**
     *  Integer 형 Null 체크
     * @param params
     * @param key
     * @return
     */
    public int intCheck(HashMap<String, Object> params, String key){
        if(params.get(key) != null && !"".equals(params.get(key))) {
            return Integer.valueOf(params.get(key).toString());
        } else {
            return 0;
        }
    }

    /**
     *  String 형 Null 체크
     * @param params
     * @param key
     * @return
     */
    public String stringCheck(HashMap<String, Object> params, String key){
        if(params.get(key) != null && !"".equals(params.get(key))) {
            return params.get(key).toString();
        } else {
            return " ";
        }
    }
    public UUID getModelValue(String data) {
        return data == null ? null : UUID.fromString(data);
    }
}
