package com.example.customerdatabase;

import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Employer, Long> {
}
