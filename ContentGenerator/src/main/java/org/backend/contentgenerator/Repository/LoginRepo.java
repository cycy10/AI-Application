package org.backend.contentgenerator.Repository;

import org.backend.contentgenerator.Models.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepo extends JpaRepository<LoginModel,Integer> {

    LoginModel findByUsername(String username);

    boolean existsByUsername(String username);
}
