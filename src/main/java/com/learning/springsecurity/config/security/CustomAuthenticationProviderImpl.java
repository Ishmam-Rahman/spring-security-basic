package com.learning.springsecurity.config.security;

import com.learning.springsecurity.entity.User;
import com.learning.springsecurity.repository.AttemptsRepository;
import com.learning.springsecurity.repository.UserRepository;
import com.learning.springsecurity.service.SecurityUserServiceImpl;
import java.util.ArrayList;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
public class CustomAuthenticationProviderImpl implements AuthenticationProvider {
  private final SecurityUserServiceImpl userService;
  private final UserRepository userRepository;
  private final AttemptsRepository attemptsRepository;
  private final PasswordEncoder passwordEncoder;

  public CustomAuthenticationProviderImpl(SecurityUserServiceImpl userService,
                                          UserRepository userRepository,
                                          AttemptsRepository attemptsRepository,
                                          PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.attemptsRepository = attemptsRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userName = authentication.getName();
    String password = authentication.getCredentials().toString();
    User user = userRepository.findUserByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("Invalid username!"));
    if (passwordEncoder.matches(password, user.getPassword())) {
      return new UsernamePasswordAuthenticationToken(user, password, new ArrayList<>());
    } else {
      throw new BadCredentialsException("Invalid credentials!!");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }
}
