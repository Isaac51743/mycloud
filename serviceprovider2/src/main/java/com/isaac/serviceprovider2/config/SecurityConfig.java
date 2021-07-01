package com.isaac.serviceprovider2.config;

import com.isaac.serviceprovider2.handler.MyAccessDeniedHandler;
import com.isaac.serviceprovider2.handler.MyAuthenticationFailureHandler;
import com.isaac.serviceprovider2.handler.MyAuthenticationSuccessHandler;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource source;

    @Autowired
    private MyAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // assign different roles for different urls.
        // can be replaced by @Secured or @PreAuthorize
        http.authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
//                .antMatchers("/security/vip1").hasAuthority("vip1")
//                .antMatchers("/security/vip2").hasAuthority("vip2")
//                .antMatchers("/security/vip3").hasAnyAuthority("admin", "vip3")
//                .antMatchers("/security/vip4").hasRole("vip4") // actually gives "ROLE_vip4"
//                .antMatchers("/security/vip5").hasAnyRole("vip1", "vip4") // actually gives "ROLE_vip1" and "ROLE_vip4"
                .antMatchers("/authority/limitip").hasIpAddress("127.0.0.1") // limit the endpoint/resource which can only be visited on a specific server
                .anyRequest().authenticated(); // rest all requests must be authenticated

        // enable remember me
        http.rememberMe()
                .tokenRepository(persistentTokenRepository()) // set the repository bean to save token to the database
                .tokenValiditySeconds(60) // set the valid duration for a token
//                .rememberMeParameter("my-remember-me") // customize the key of input remember-me
                .userDetailsService(userDetailsService);


        // close csrf protection
        http.csrf().disable();

        // customize the login settings
        http.formLogin()
                .usernameParameter("my_username") // customize the key of input username
                .passwordParameter("my_password") // customize the key of input password
                .loginProcessingUrl("/login") // set the login endpoint, and call userDetailsService automatically, same as the form action in login.html
                .successForwardUrl("/tomain") // set the redirected url when user login in successfully, should be a POST request
//                .successHandler(new MyAuthenticationSuccessHandler("https://www.google.com/")) // redirect to api on another server, conflict with successForwardUrl()
                .failureForwardUrl("/toerror") // set the redirected url when user login in failed, should be a POST request
//                .failureHandler(new MyAuthenticationFailureHandler("https://www.youtube.com/")) // redirect to api on another server, conflict with failureForwardUrl()
                .loginPage("/login.html"); // customize login page


        // customize 403 forbidden exception
        http.exceptionHandling()
                .accessDeniedHandler(deniedHandler);

        http.logout()
                .logoutSuccessUrl("/login.html");
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         method1: get valid username and password from the database
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

            // method2: hard code valid username and password in memory
            // encrypt password
//        String encyptedPassword1 = new BCryptPasswordEncoder().encode("isaac");
//        String encyptedPassword2 = new BCryptPasswordEncoder().encode("pass");

//        auth.inMemoryAuthentication()
//                // assign valid users with different roles
//                .withUser("isaac").password(encyptedPassword1).roles("vip1", "vip2")
//                .and()
//                .withUser("barry").password(encyptedPassword2).authorities("vip1");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(source);
//        jdbcTokenRepository.setCreateTableOnStartup(true); // create a token table when application start up (use once)
        return jdbcTokenRepository;
    }
}
