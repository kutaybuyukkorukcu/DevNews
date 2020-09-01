package com.scalx.devnews.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseEntity<U> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;

    private U createdBy;

    private U lastModifiedBy;

    public boolean isNew() {
        return this.id == null;
    }

    // If the LocalDateTime creates a conflict in entities refactor to (hypothetically) Optional.of(this.createdDate)

    public Optional<LocalDateTime> getCreatedDate() {
        return null == this.createdDate ? Optional.empty() : Optional.of(LocalDateTime.ofInstant(Instant.from(this.createdDate), ZoneId.of("Africa/Addis_Ababa")));
    }

    public Optional<LocalDateTime> getLastModifiedDate() {
        return null == this.lastModifiedDate ? Optional.empty() : Optional.of(LocalDateTime.ofInstant(Instant.from(this.lastModifiedDate), ZoneId.of("Africa/Addis_Ababa")));
    }

    public Optional<U> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }

    public Optional<U> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedBy);
    }
}
