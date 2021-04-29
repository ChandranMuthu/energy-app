package com.example.crudwithvaadin.repository;

import com.example.crudwithvaadin.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    UserDetails findByUserName(final String userName);
}
