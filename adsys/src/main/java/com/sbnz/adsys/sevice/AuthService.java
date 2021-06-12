package com.sbnz.adsys.sevice;

import com.sbnz.adsys.dto.AuthTokenDto;
import com.sbnz.adsys.dto.LoginDTO;
import com.sbnz.adsys.event.LoginEvent;
import com.sbnz.adsys.exception.AuthException;
import com.sbnz.adsys.model.User;
import com.sbnz.adsys.repository.UserRepository;
import com.sbnz.adsys.security.TokenUtils;
import lombok.AllArgsConstructor;
import org.h2.security.auth.AuthConfigException;
import org.kie.api.runtime.KieSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final KieService kieService;
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authManager;
    
  public AuthTokenDto login(LoginDTO loginDTO) {
    KieSession loginSession = kieService.getLoginSession();
    Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
    if (!user.isPresent()) return null;
    try{
    User user2 = (User) this.authManager.authenticate(usernamePasswordAuthenticationToken).getPrincipal();
    }
    catch (Exception e){
      LoginEvent loginEvent = new LoginEvent(new Date(), user.get(), false);
      loginSession.insert(loginEvent);
      loginSession.fireAllRules();
      userRepository.save(loginEvent.getUser());
      if (!loginEvent.getUser().isEnabled()) {
        System.out.println("Not allowed to login. You can not login after three failed attempts. Try again after 5 minutes.");
        throw new AuthException("Acc blocked");
      }
     throw new AuthException("Wrong password");
    }
    LoginEvent loginEvent = new LoginEvent(new Date(), user.get(),true);
    loginSession.insert(loginEvent);
    loginSession.fireAllRules();
    userRepository.save(user.get());
    return new AuthTokenDto(
            user.get().getId(),
            user.get().getUsername(),
            this.tokenUtils.generateToken( user.get().getEmail()),
            user.get().getAuthorities()
    );
  }
}
