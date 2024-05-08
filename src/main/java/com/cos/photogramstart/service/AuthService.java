package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // 1.IoC등록, 2.트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional //write(insrt, update, delete)할 때 이 어노테이션을 사용하겠다는 뜻
    public User 회원가입(User user){
        //회원가입 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER"); //관리자는 ROLE_ADMIN
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
