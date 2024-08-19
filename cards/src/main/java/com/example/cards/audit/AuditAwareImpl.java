package com.example.cards.audit;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //getCurrentAuditor() method is typically used in conjunction with Spring Data JPA’s auditing features to automatically populate fields
        // such as @CreatedBy and @LastModifiedBy with the current user or auditor’s information.
        return Optional.of("CARDS_MS");
    }
}