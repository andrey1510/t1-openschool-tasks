package com.productapp.services;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public String receiveString() {
        return "String from other service";
    }

}
