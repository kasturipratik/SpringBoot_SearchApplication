package com.example.customerdatabase;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastnameIgnoreCase(String lastname);
    List<Customer> findByCityIgnoreCase(String city);
/*
    List<Customer> countCustomerByCompany();*/
}
