package backend.backend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.backend.dto.user.UserAddDTO;
import backend.backend.dto.user.UserDTO;
import backend.backend.dto.user.UserRoleUpdateDTO;
import backend.backend.dto.user.UserSearchDTO;
import backend.backend.dto.user.UserUpdateDTO;
import backend.backend.entity.User;
import backend.backend.enums.AuthProvider;
import backend.backend.enums.UserRole;
import backend.backend.mapper.UserMapper;
import backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findUserById(id);
        return userMapper.toDTO(user);
    }

    public UserDTO AddUser(UserAddDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = userMapper.toEntity(dto);

        // Mã hóa password trước khi lưu
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Thiết lập giá trị mặc định
        user.setRole(UserRole.CUSTOMER);
        user.setProvider(AuthProvider.LOCAL);

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        User existingUser = userRepository.findUserById(id);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        // Kiểm tra email và username có bị trùng với người dùng khác không
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsernameAndIdNot(dto.getUsername(), id)) {
            throw new RuntimeException("Username already exists");
        }

        // Cập nhật thông tin người dùng
        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhone(dto.getPhone());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDTO(updatedUser);
    }

    public UserDTO updateUserRole(Long id, UserRoleUpdateDTO dto) {
        User existingUser = userRepository.findUserById(id);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        existingUser.setRole(dto.getRole());
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findUserById(id);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        userRepository.delete(existingUser);
    }

    public List<UserDTO> searchUsers(UserSearchDTO dto) {
        List<User> users = userRepository.SearchUser(dto.getSearchText(), dto.getRole(),
                dto.getEmailVerified() != null ? dto.getEmailVerified() : false,
                dto.getProvider());
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

}
