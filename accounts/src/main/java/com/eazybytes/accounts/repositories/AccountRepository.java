package com.eazybytes.accounts.repositories;

import com.eazybytes.accounts.entities.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>
{

    Optional<Account> findByCustomerId(Long customerId);
    // Find by runs a select and returns a object


    @Modifying                                // This will tell Spring DATA JPA we are modifying DB DATa using this method For userdefined methods we have to mention if we are modifying the data in database use this annotaion
    @Transactional                          // This tells to spring datat JPA rollback the transaction if anything in between fails rollback to original state
     void deleteByCustomerId(Long customerId);
     // delete by runs a delete query and returns a void
    // These 2 annotions are bydefault handled by fefault methods but as this is a custom method we have to mention this

}
