package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 JPA를 상속하면 자동으로 IoC 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {
    //JPA method names : Query method
    User findByUsername(String username);
}
