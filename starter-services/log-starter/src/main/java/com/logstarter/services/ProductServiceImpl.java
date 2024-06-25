package com.logstarter.services;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public String receiveString(String str) {
        if (str.equals("send input string")) {
            return "received output string";
        } else {
            throw new RuntimeException("Intentional error");
        }

    }

}
