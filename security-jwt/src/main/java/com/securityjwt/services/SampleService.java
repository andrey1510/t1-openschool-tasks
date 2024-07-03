package com.securityjwt.services;

public interface SampleService {
    String checkPerformerAccess();

    String checkCustomerAccess();

    String checkBothAccess();

    String checkUserAccess();
}
