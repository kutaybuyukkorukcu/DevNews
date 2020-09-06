package com.scalx.devnews.helper;

import com.scalx.devnews.dto.BaseRequest;
import com.scalx.devnews.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class FieldSetter<DTO extends BaseRequest, DAO extends BaseEntity> {

    public DAO setFieldsWhenCreate(DTO request, DAO entity) {

        entity.setActive(true);
        entity.setCreatedBy(request.getAccountName());
        entity.setLastModifiedBy(request.getAccountName());
        entity.setCreatedDate(java.sql.Date.valueOf(LocalDate.now()));
        entity.setLastModifiedDate(java.sql.Date.valueOf(LocalDate.now()));

        return entity;
    }

    public DAO setFieldsWhenUpdate(DTO request, DAO entity) {

        entity.setActive(true);
        entity.setLastModifiedBy(request.getAccountName());
        entity.setLastModifiedDate(java.sql.Date.valueOf(LocalDate.now()));

        return entity;
    }

    public DAO setFieldsWhenDelete(DTO request, DAO entity) {

        entity.setActive(false);
        entity.setLastModifiedBy(request.getAccountName());
        entity.setLastModifiedDate(java.sql.Date.valueOf(LocalDate.now()));

        return entity;
    }
}
