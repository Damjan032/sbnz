package com.sbnz.adsys.service;

import com.sbnz.adsys.model.User;
import com.sbnz.adsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
  }
  
  public Iterable<User> getAll() {
    return null;
  }
  
  public User getById(UUID id) {
    return null;
  }
  
  public User create(User entity) throws Exception {
    return null;
  }
  
  public boolean delete(UUID id) throws Exception {
    return false;
  }
  
  public User update(UUID id, User entity) throws Exception {
    return null;
  }
  
  public User findByEmail(String email) {
    return this.loadUserByUsername(email);
  }
  
}
