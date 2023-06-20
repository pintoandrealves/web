package com.aap.web.service;

import com.aap.web.dto.RegistrationDto;
import com.aap.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUserName(String username);
}
