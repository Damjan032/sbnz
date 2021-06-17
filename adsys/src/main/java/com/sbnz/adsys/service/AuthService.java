package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AuthTokenDTO;
import com.sbnz.adsys.dto.BasicUserInfoDTO;
import com.sbnz.adsys.dto.LoginDTO;
import com.sbnz.adsys.event.LoginEvent;
import com.sbnz.adsys.exception.AuthException;
import com.sbnz.adsys.model.User;
import com.sbnz.adsys.repository.UserRepository;
import com.sbnz.adsys.security.TokenUtils;
import lombok.AllArgsConstructor;
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

    public AuthTokenDTO login(LoginDTO loginDTO) {
        KieSession loginSession = kieService.getLoginSession();
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
        if (!optionalUser.isPresent()) return null;
        User user = optionalUser.get();
        if (!user.getPassword().equals(loginDTO.getPassword()) || !user.isEnabled()){
            LoginEvent loginEvent = new LoginEvent(new Date(), user, false);
            loginSession.insert(loginEvent);
            loginSession.fireAllRules();
            userRepository.save(loginEvent.getUser());
            if (!loginEvent.getUser().isEnabled()) {
                System.out.println("Not allowed to login. You can not login after three failed attempts. Try again in 5 minutes.");
                throw new AuthException("Acc blocked");
            }
            throw new AuthException("Wrong password");
        }
        return AuthTokenDTO.builder()
                .accessToken(this.tokenUtils.generateToken(user.getEmail()))
                .authorities(user.getAuthorities())
                .user(BasicUserInfoDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build())
                .build();
    }
}
