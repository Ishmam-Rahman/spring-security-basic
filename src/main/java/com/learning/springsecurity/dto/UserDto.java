package com.learning.springsecurity.dto;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserDto {
  @Id
  private String userName;
  private String password;
  private Boolean accountNonLocked;
}
