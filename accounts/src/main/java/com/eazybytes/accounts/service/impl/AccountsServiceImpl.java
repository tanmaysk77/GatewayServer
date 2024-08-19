package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dtos.AccountsDto;
import com.eazybytes.accounts.dtos.CustomerDto;
import com.eazybytes.accounts.entities.Account;
import com.eazybytes.accounts.entities.Customer;
import com.eazybytes.accounts.exceptions.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exceptions.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repositories.AccountRepository;
import com.eazybytes.accounts.repositories.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

// Whenever there is a single constructor of a class you do not need to autowire the properties using @autowire
    //The single constructor will accept all these parameters and autowire bydefault

    @Override
    public void createAccountService(CustomerDto customerDTO) {

        Customer newCustomer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> matchedNumber = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());

            if (matchedNumber.isPresent()) {
                throw new CustomerAlreadyExistsException("A customer is already registered with this number " + customerDTO.getMobileNumber());
                 // As this is a runtime exception it can be surrounded in try catch here or will be automatically be propagated to the caller method and  till JVM if not handled anywhere using try catch
                // Throws is not required with runtime exception it will propogate automatically
                // But if this was a checked exception you had to handle it here using try catch here only or use throws to propagate to the caller method
                // Everywhere we have to take care of handling this scenarios where ever this exception is called so instead we can make use of global exception  handling
            }

        // newCustomer.setCreatedBy("Anonymous");        These values are set using CreatedBy and CreatedAt annotations in base class
        // newCustomer.setCreatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(newCustomer);                       // Customer Id will be created automaticallyy by spring Data JPA after Saving
       accountRepository.save(createNewAccount(savedCustomer));                                            // Now as customer is created we have to create account also for it using the customer iD of returned customer
    }


    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    public Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);       // We are not creating account number in entity class using automated stratergy instead we are creating it here

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
//        newAccount.setCreatedBy("Anonymous");                             These values are set using CreatedBy and CreatedAt annotations in base class
//        newAccount.setCreatedAt(LocalDateTime.now());
        return newAccount;
    }


    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
     Customer customerDetails =
             customerRepository.findByMobileNumber(mobileNumber).orElseThrow (
                ()-> new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );

     // We have to return account details also with cusotmer details

      Account accountDetails =
              accountRepository.findByCustomerId(customerDetails.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","AccountNumber",customerDetails.getCustomerId().toString())
        );

      // We cannot send all data only useful data we have to send from customer and account

        CustomerDto customerDTO = CustomerMapper.mapToCustomerDto(customerDetails, new CustomerDto());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accountDetails, new AccountsDto()));

        return customerDTO;


    }

    @Override
    public boolean updateAccount(CustomerDto customerDTO) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDTO.getAccountsDTO();
        if(accountsDto !=null )
        {
            Account accounts = accountRepository.
                    findById(accountsDto.getAccountNumber())                                                                                    // So we do not want to allow to update the account number if updated it will not be found in DB and result in error
                    .orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
                    );

            AccountsMapper.mapToAccounts(accountsDto, accounts);                // So when we pass object as parameter  Java passes object references as value and not actual object (Pass by reference concept)
                                                                                                                        // So if you change the object states from different method it will the same reference object
                                                                                                                        // If you do the same with primitive type changes done in differnent method will not be reflected in this method (Pass by value concept)
            Account accountDetails = accountRepository.save(accounts);

            Long customerId = accountDetails.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            // Once account is updated update the information in customer also
            CustomerMapper.mapToCustomer(customerDTO,customer);                 // So all customer Realted updtes or Account Related update is done here
            customerRepository.save(customer);                                                      // Save() is very intelliject if it does not find any Primary key inside object it will insert of a new record operation
                                                                                                                            // In our case we have the custId already so primary key is there it will do the update operation
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
