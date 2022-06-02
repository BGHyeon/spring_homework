package com.sparta.configurations;

import com.sparta.jwtsecurity.CustomLogout;
import com.sparta.jwtsecurity.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

//    private final MemberAuthService authService;

//    private final AuthFailHandler failhandler;
    private final CustomAccessDenied denieHander;
    private final JwtAuthFilter filter;

    private final CustomLogout logoutHnadler;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RequestMatcher matcher = new RequestMatcher() {
            @Override
            public boolean matches(HttpServletRequest request) {
                return false;
            }
        };
        http.csrf().disable();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // JWT Token 검사로직
        http
                .authorizeRequests()
                .antMatchers("/member/**").permitAll()
                .antMatchers("/notice/**").permitAll()
                .antMatchers("/main").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/js/**").permitAll()
                .mvcMatchers("/v2/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/member/login")
//                .defaultSuccessUrl("/main",true)
//                .failureHandler(failhandler)
//                .permitAll()
//                .and()
                .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessHandler(logoutHnadler).permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(denieHander)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(authService).passwordEncoder(getPasswordEncoder());
//    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // JWT 추가
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
