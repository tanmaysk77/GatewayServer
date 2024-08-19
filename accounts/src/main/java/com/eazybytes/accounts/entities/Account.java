package com.eazybytes.accounts.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity{

        @Column(name = "customer_id")
        private Long customerId;


        @Column(name = "account_number")
        @Id
        private Long accountNumber;

        @Column(name = "account_type")
        private String accountType;

        @Column(name = "branch_address")
        private String branchAddress;
    }


