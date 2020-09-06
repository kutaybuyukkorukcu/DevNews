package com.scalx.devnews.repository;

import com.scalx.devnews.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);
}
