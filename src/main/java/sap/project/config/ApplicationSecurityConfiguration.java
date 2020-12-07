package sap.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sap.project.service.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return  new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/stock/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/representatives/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/sales/salesAdmin").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/homeAdmin").hasAnyAuthority("ROLE_ADMIN")

                .antMatchers("/clients/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/sales/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/homeUser").hasAnyAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .successHandler(customLoginSuccessHandler)
                .and()
                .logout().permitAll()
                .and()
                ;
    }
}
