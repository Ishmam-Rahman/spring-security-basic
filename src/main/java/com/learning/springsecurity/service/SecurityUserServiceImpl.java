package com.learning.springsecurity.service;

import com.learning.springsecurity.entity.User;
import com.learning.springsecurity.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public SecurityUserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
    return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),new ArrayList<>());
  }

  public void createUser(User user) {
    userRepository.save(user);
  }
}
