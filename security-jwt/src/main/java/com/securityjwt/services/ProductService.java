package com.securityjwt.services;

public interface ProductService {
    String checkPerformerAccess();

    String checkCustomerAccess();

    String checkBothAccess();

    String checkUserAccess();
}
