package com.securewebapp.auth;

import org.springframework.data.jpa.repository.JpaRepository;

// repository that generates the SQL queries that will query the 'User' database and return the results
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}