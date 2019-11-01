package com.example.services.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.services.auth.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
