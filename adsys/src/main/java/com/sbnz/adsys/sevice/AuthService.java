package com.sbnz.adsys.sevice;

import com.sbnz.adsys.dto.LoginDTO;
import com.sbnz.adsys.event.LoginEvent;
import com.sbnz.adsys.model.User;
import com.sbnz.adsys.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final KieService kieService;
    private final UserRepository userRepository;

  public boolean login(LoginDTO loginDTO) {
    KieSession loginSession = kieService.getLoginSession();
    Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
    if (!user.isPresent()) return false;
    if (!user.get().getPassword().equals(loginDTO.getPassword())) {
      LoginEvent loginEvent = new LoginEvent(new Date(), user.get(), false);
      loginSession.insert(loginEvent);
      loginSession.fireAllRules();
      userRepository.save(loginEvent.getUser());
      if (!loginEvent.getUser().isEnabled()) {
        System.out.println("Not allowed to login. You can not login after three failed attempts. Try again after 3 minutes.");
      }
      return false;
    }
    LoginEvent loginEvent = new LoginEvent(new Date(), user.get(),true);
    loginSession.insert(loginEvent);
    loginSession.fireAllRules();
    userRepository.save(user.get());
    return true;
  }
}
