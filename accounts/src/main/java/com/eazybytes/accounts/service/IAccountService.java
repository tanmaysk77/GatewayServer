package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dtos.CustomerDto;

public interface IAccountService {

    void createAccountService(CustomerDto customerDTO);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDTO);

    boolean deleteAccount(String mobileNumber);

}