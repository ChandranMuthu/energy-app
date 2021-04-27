package com.example.crudwithvaadin.repository;

import com.example.crudwithvaadin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(final String userName);
}
