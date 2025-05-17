package org.example.authservice.servicetests;

import org.example.authservice.model.User;
import org.example.authservice.model.dto.UserRegisterRequest;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_ShouldCreateUser_WhenUsernameAndEmailAreUnique() {
        UserRegisterRequest request = new UserRegisterRequest("john_doe", "john@example.com", "password");

        String encodedPassword = "{bcrypt}encoded_password";
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setUsername("john_doe");
        savedUser.setEmail("john@example.com");
        savedUser.setPassword(encodedPassword);
        savedUser.setRoles(Set.of("USER"));

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.register(request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john_doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getPassword()).isEqualTo(encodedPassword);
        assertThat(result.getRoles()).containsExactlyInAnyOrder("USER");

        verify(userRepository, times(1)).existsByUsername("john_doe");
        verify(userRepository, times(1)).existsByEmail("john@example.com");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_ShouldThrowException_WhenUsernameExists() {

        UserRegisterRequest request = new UserRegisterRequest("john_doe", "john@example.com", "password");
        when(userRepository.existsByUsername("john_doe")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username already in use");

        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_ShouldThrowException_WhenEmailExists() {

        UserRegisterRequest request = new UserRegisterRequest("john_doe", "john@example.com", "password");
        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email already in use");

        verify(userRepository, times(1)).existsByUsername("john_doe");
        verify(userRepository, times(1)).existsByEmail("john@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoadUserByUsername_ShouldReturnUser_WhenFound() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("encoded_pass");
        user.setRoles(Set.of("USER"));

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("john_doe");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("john_doe");
        assertThat(userDetails.getAuthorities()).hasSize(1);
    }

    @Test
    void testLoadUserByUsername_ShouldThrowException_WhenNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.loadUserByUsername("unknown"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}