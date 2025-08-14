package tn.esprit.tic.timeforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String mail;
    private String password;


    public String getUsername() {
        return mail;
    }

    public String getPassword() {
        return password;
    }


}
