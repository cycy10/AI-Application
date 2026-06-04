package org.backend.contentgenerator.Security;

import org.backend.contentgenerator.Models.LoginModel;
import org.backend.contentgenerator.Models.UserPrincipal;
import org.backend.contentgenerator.Repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    LoginRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginModel data = repo.findByUsername(username);
        return new UserPrincipal(data);
    }
}
