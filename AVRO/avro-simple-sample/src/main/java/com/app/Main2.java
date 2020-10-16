package com.app;

import java.util.Map;

public class Main2 {

    public static void main(String[] args) {
        MyTransformerBinary myTransformer = new MyTransformerBinary();
        byte[] data = myTransformer.serialize();
        Map<String, Object> map =  myTransformer.deserialize(data);
        System.out.println(map);
    }
}
