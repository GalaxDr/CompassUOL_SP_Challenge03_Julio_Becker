package br.com.compassuol.sp.challenge.msauth.dtos;

import br.com.compassuol.sp.challenge.msauth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private UserRole role;
}
