package com.learning.springsecurity.repository;

import com.learning.springsecurity.entity.Attempts;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptsRepository extends JpaRepository<Attempts, Long> {
  Optional<Attempts> findAttemptsByUserName(String userName);
}
