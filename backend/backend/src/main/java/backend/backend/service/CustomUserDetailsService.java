
package backend.backend.service;

import backend.backend.entity.User;
import backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(
                email.trim().toLowerCase()).orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Email hoặc mật khẩu không đúng"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword() == null
                        ? ""
                        : user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .disabled(!user.isEmailVerified())
                .build();
    }
}
