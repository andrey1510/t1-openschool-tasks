package com.securityjwt.services;

public interface SampleService {
    String checkPerformerAccess();

    String checkCustomerAccess();

    String checkPerformerOrCustomerAccess();

    String checkUserAccess();
}
