package tn.esprit.tic.timeforge.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.tic.timeforge.Entity.Role;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
    private List<RoleDto> roles;
    private int cin;

}
