package com.dollarsbank.v3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dollarsbank.v3.model.Checking;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {

}
