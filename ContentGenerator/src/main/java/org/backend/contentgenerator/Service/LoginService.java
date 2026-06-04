package org.backend.contentgenerator.Service;

import org.backend.contentgenerator.Dto.LoginDto;
import org.backend.contentgenerator.Dto.LoginResponse;
import org.backend.contentgenerator.Models.LoginModel;
import org.backend.contentgenerator.Models.UserPrincipal;
import org.backend.contentgenerator.Repository.LoginRepo;
import org.backend.contentgenerator.Security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private LoginRepo repo;
    private AuthenticationManager authMan;
    private PasswordEncoder encoder;

    public LoginService(LoginRepo repo,AuthenticationManager authMan,PasswordEncoder encoder){
        this.repo = repo;
        this.authMan = authMan;
        this.encoder = encoder;
    }

    @Autowired
    JWTService token;
    public ResponseEntity<LoginResponse> loginVerfication(LoginDto credentials) {
        LoginResponse res = new LoginResponse();
        try{
            Authentication authentication = authMan.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword()));
            if(authentication.isAuthenticated()){

                res.setLoginMessage("Successfull login");
                res.setToken(token.generateToken(credentials.getUsername()));
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
            }
        }
        catch (BadCredentialsException e) {
            res.setLoginMessage("Invalid Username or password");
            res.setToken("null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        return null;
    }

    public ResponseEntity<String> registration(LoginDto details) {
        if(!repo.existsByUsername(details.getUsername())){
            LoginModel userData = new LoginModel();
            userData.setUsername(details.getUsername());
            String password = encoder.encode(details.getPassword());
            userData.setPassword(password);
            repo.save(userData);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exsits");

    }

}
