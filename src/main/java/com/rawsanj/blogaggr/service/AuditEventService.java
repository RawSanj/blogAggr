package com.rawsanj.blogaggr.service;

import com.rawsanj.blogaggr.config.audit.AuditEventConverter;
import com.rawsanj.blogaggr.domain.PersistentAuditEvent;
import com.rawsanj.blogaggr.repository.PersistenceAuditEventRepository;
import java.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing audit events.
 * <p/>
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
public class AuditEventService {

    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    private AuditEventConverter auditEventConverter;

    @Inject
    public AuditEventService(
        PersistenceAuditEventRepository persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    public List<AuditEvent> findAll() {
        return auditEventConverter.convertToAuditEvent(persistenceAuditEventRepository.findAll());
    }

    public List<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate) {
        List<PersistentAuditEvent> persistentAuditEvents =
            persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate);

        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }

    public Optional<AuditEvent> find(String id) {
        return Optional.ofNullable(persistenceAuditEventRepository.findOne(id)).map
            (auditEventConverter::convertToAuditEvent);
    }
}
