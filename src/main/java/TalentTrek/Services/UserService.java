package TalentTrek.Services;

import TalentTrek.DTO.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto user, int userId);
    UserDto getUserById(int userId);
    void deleteUser(int userId);
    boolean authenticateUser(String email, String password);
}
