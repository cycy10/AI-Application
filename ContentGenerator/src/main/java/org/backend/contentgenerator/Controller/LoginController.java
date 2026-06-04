package org.backend.contentgenerator.Controller;


import org.backend.contentgenerator.Dto.LoginDto;
import org.backend.contentgenerator.Dto.LoginResponse;
import org.backend.contentgenerator.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private LoginService service;

    public LoginController(LoginService service){
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto credentials){
        return service.loginVerfication(credentials);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody  LoginDto details){
        return service.registration(details);
    }

    @GetMapping("/data")
    public String helo(){
        return "hello";
    }




}
