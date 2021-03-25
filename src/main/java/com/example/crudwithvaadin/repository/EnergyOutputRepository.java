package com.example.crudwithvaadin.repository;

import com.example.crudwithvaadin.model.EnergyOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyOutputRepository extends JpaRepository<EnergyOutput, Integer> {
    List<EnergyOutput> findAll();

    List<EnergyOutput> findByTransactionId(String transactionId);

    List<EnergyOutput> findByDeviceId(String deviceId);

    @Query(value = "SELECT DISTINCT deviceId from EnergyOutput ORDER BY deviceId")
    List<String> getDistinctDeviceId();

}
