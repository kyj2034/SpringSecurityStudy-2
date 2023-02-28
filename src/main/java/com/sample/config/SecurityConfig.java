package com.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("deprecation")
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        
        http.authorizeHttpRequests()
                .requestMatchers("/user/**").authenticated()     
                				// user주소에 대해서 인증을 요구한다
                .requestMatchers("/manager/**").hasAnyRole("ROLE_ADMIN", "ROLE_MANAGER")
                				// manager주소는 ROLE_MANAGER권한이나 ROLE_ADMIN권한이 있어야 접근할 수 있다.
                				// access String 인자가 들어가면 안됨... 
                .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")	
                				// admin주소는 ROLE_ADMIN권한이 있어야 접근할 수 있다.
                .anyRequest().permitAll()
                .and()					//추가
                .formLogin()				// form기반의 로그인인 경우
                    .loginPage("/loginForm")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
                    .usernameParameter("id")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
                    .passwordParameter("pw")		// 로그인 시 form에서 가져올 값
                    .loginProcessingUrl("/login")		// 로그인을 처리할 URL 입력
                    .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
                    .failureUrl("/loginForm")		//로그인 실패 시 /loginForm으로 이동
                .and()
                .logout()					// logout할 경우
                	.logoutUrl("/logout")			// 로그아웃을 처리할 URL 입력
                    .logoutSuccessUrl("/");			// 로그아웃 성공 시 "/"으로 이동// 나머지주소는 인증없이 접근 가능하다
        
        return http.build();
    }
}
