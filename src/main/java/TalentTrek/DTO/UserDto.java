package TalentTrek.DTO;

import lombok.Data;

@Data
public class UserDto {

    private int id;
    private String username;
    private String type;
    private String email;
    private String password;
}
