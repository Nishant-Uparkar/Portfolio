package com.nishant.portfolio.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nishant.portfolio.Entity.ContactMsg;
public interface ContactMsgRepository extends JpaRepository<ContactMsg, Long> {
    
    Optional<ContactMsg> findTopByEmailOrderBySubmittedAtDesc(String email);
}
