package br.com.compassuol.sp.challenge.msauth.controller;

import br.com.compassuol.sp.challenge.msauth.config.TokenService;
import br.com.compassuol.sp.challenge.msauth.dtos.AuthDTO;
import br.com.compassuol.sp.challenge.msauth.dtos.LoginResponseDTO;
import br.com.compassuol.sp.challenge.msauth.dtos.RegisterDTO;
import br.com.compassuol.sp.challenge.msauth.entity.User;
import br.com.compassuol.sp.challenge.msauth.repository.UserRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()) != null) {
            return new ResponseEntity("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
        User user = new User(registerDTO.getUsername(),encodedPassword, registerDTO.getRole());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
