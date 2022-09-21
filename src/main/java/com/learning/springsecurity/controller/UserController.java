package com.learning.springsecurity.controller;

import com.learning.springsecurity.dto.UserDto;
import com.learning.springsecurity.entity.User;
import com.learning.springsecurity.repository.UserRepository;
import com.learning.springsecurity.service.SecurityUserServiceImpl;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final SecurityUserServiceImpl userService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public UserController(SecurityUserServiceImpl userService, UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/get-all-user")
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  @GetMapping("/get-current-user")
  public User getCurrentUser() {
     User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return user;
  }

  @PostMapping("/register")
  public User createUser(@RequestBody UserDto userDto) {
    User user = new User();
    user.setUserName(userDto.getUserName());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setAccountNonLocked(user.getAccountNonLocked());
    userRepository.save(user);
    return user;
  }

  @PostMapping("/login")
  public String logIn(@RequestBody UserDto userDto) {
    String sessionId = null;
    try {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (BadCredentialsException e){
      return "Bad credentials!";
    }


    return "Ok";
  }
}
