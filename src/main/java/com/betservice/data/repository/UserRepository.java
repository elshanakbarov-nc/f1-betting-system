package com.betservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betservice.data.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
