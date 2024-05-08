package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //IoC에 등록해주기
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    //1. password는 알아서 체킹하니까 신경 쓸 필요가 없다.
    //2. 리턴이 잘 되면 자동으로 UserDetails 타입을 세션을 만든다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        //스프링 시큐리티가 패스워드가 맞는지 아닌지는 알아서 비교해주기 때문에
        //우리는 Login할 때 오는 username만 있는지 확인해주면 된다.

        if(userEntity==null){
            return null;
        }else{
            return new PrincipalDetails(userEntity);
        }
    }
}
