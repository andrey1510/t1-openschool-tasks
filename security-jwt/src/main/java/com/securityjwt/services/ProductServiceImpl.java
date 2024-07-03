package com.securityjwt.services;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public String checkPerformerAccess(){
        return "Доступ разрешен.";
    }

    @Override
    public String checkCustomerAccess(){
        return "Доступ разрешен.";
    }

    @Override
    public String checkBothAccess(){
        return "Доступ разрешен.";
    }

    @Override
    public String checkUserAccess(){
        return "Доступ разрешен.";
    }

}
