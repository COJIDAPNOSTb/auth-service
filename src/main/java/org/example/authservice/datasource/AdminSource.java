package org.example.authservice.datasource;

import jakarta.annotation.PostConstruct;
import org.example.authservice.model.User;
import org.example.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class AdminSource {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSource(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void initAdmin() {
      if(userRepository.findByUsername("admin").isEmpty()) {
          User admin = new User();
          admin.setId(UUID.randomUUID());
          admin.setUsername("admin");
          admin.setPassword(passwordEncoder.encode("admin"));
          admin.setEmail("admin@admin.com");
          admin.setRoles(Collections.singleton("ADMIN"));
          userRepository.save(admin);
      }
      else{
          return;
      }
}

}
