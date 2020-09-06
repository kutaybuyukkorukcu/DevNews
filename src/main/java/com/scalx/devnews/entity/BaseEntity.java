package com.scalx.devnews.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_active")
    private boolean isActive;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public boolean isNew() {
        return this.id == null;
    }

    // If the LocalDateTime creates a conflict in entities refactor to (hypothetically) Optional.of(this.createdDate)


    public Optional<Date> getCreatedDate() {
        return null == this.createdDate ?
                Optional.empty() : Optional.of(this.createdDate);
//                Optional.empty() : Optional.of(LocalDateTime.ofInstant((
//                        Instant.from(this.createdDate.toInstant()), ZoneId.of("Africa/Addis_Ababa")));
    }

    public Optional<Date> getLastModifiedDate() {
        return null == this.lastModifiedDate ?
                Optional.empty() : Optional.of(this.createdDate);
//                Optional.empty() : Optional.of(LocalDateTime.ofInstant(
//                        Instant.from(this.lastModifiedDate.toInstant()), ZoneId.of("Africa/Addis_Ababa")));
    }

    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(this.createdBy);
    }

    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedBy);
    }
}
