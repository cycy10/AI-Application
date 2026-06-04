package org.backend.contentgenerator.Security;


import org.backend.contentgenerator.Models.LoginModel;
import org.backend.contentgenerator.Models.UserPrincipal;
import org.backend.contentgenerator.Repository.LoginRepo;
import org.backend.contentgenerator.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfigSecurity{

//    private LoginRepo repo;
//    public ConfigSecurity(LoginRepo repo){
//        this.repo = repo;
//    }

    @Autowired
    UserDetailsService userDetailService;

    @Autowired
    JWTFilter jwtFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable()).formLogin(form->form.disable()).httpBasic(basic->basic.disable()).authorizeHttpRequests(request->request.requestMatchers("/login","/signup").permitAll().anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);;
        return  http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        return new UserDetailsService() {
//
//            @Override
//            public UserDetails loadUserByUsername(String username)
//                    throws UsernameNotFoundException {
//
//                LoginModel user = repo.findByUsername(username);
//
//                if(user == null) {
//                    throw new UsernameNotFoundException(
//                            "User not found");
//                }
//                return new UserPrincipal(user);
//            }
//        };
//    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoding());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }
    @Bean
    public AuthenticationManager authManger(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoding(){
        return new BCryptPasswordEncoder(10);
    }


}
