package com.sbnz.adsys.sevice;

import com.sbnz.adsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService { // } implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  //    @Override
  //    public User loadUserByUsername(String email) throws UsernameNotFoundException {
  //        return userRepository.findByEmail(email)
  //                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " +
  // email));
  //    }
}
