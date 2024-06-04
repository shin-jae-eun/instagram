package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@RequiredArgsConstructor
@EnableWebSecurity //해당 파일로 시큐리티를 활성화 시키겠다
@Configuration //IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "subscribe/**","/comment/**", "/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") //GET를 낚아챔
                .loginProcessingUrl("/auth/signin") //POST를 낚아채서 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login() //form 로그인도 하는데, oauth2로그인도 할꺼야 !!
                .userInfoEndpoint() //근데 oauth2로그인을 하면 최종응답을 code로하지말고 회원정보로 바로 돌려줘~ 라는 뜻
                .userService(oAuth2DetailsService);
    }
}
