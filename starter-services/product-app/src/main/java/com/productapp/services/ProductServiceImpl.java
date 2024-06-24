package com.productapp.services;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public String receiveString(String str) {
        if (str.equals("hi")) {
            return "hello@@@@@@@@@!!!!!!";
        } else {
            throw new RuntimeException("Error!!!SSSSSSSS");
        }

    }

}
