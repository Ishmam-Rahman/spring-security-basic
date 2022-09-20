package com.learning.springsecurity.repository;

import com.learning.springsecurity.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

  Optional<User> findUserByUserName(String userName);
}
