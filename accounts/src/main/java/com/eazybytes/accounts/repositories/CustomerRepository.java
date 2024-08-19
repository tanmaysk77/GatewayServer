package com.eazybytes.accounts.repositories;

import com.eazybytes.accounts.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    // My JPA framework will provide findById method only on primary value


    Optional<Customer> findByMobileNumber(String mobileNumber);                     // Spring will run select query based o colum name provided after findBy
}
