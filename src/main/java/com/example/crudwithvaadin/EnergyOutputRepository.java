package com.example.crudwithvaadin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyOutputRepository extends JpaRepository<EnergyOutput, Integer> {
    List<EnergyOutput> findAll();

    List<EnergyOutput> findByTransactionId(String transactionId);

}
