package com.eazybytes.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> { // Since our createBy and UpdatedBy are of type String mention string

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");          // this method will add the value to createdBy at start and updatedBy when record is updated automatically for @CreatedBy @LastModifiedBy
    }
}
