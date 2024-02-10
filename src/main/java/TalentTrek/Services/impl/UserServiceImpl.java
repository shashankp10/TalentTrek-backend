package TalentTrek.Services.impl;

import TalentTrek.DTO.UserDto;
import TalentTrek.Entities.User;
import TalentTrek.Exceptions.CustomException;
import TalentTrek.Repo.UserRepo;
import TalentTrek.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto user, int userId) {
        return null;
    }

    @Override
    public UserDto getUserById(int userId) {
        return null;
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomException("User not found with id: " + userId));
        userRepo.delete(user);
    }
    @Override
    public boolean authenticateUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(encryptPassword(password))) {
            return true; // Authentication successful
        }
        return false; // Authentication failed
    }
    private User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setType(userDto.getType());
        user.setPassword(encryptPassword(userDto.getPassword()));
        return user;
    }
    private UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setType(user.getType());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
