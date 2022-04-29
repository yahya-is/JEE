package ma.emsi.patientsmvc.sec;

import ma.emsi.patientsmvc.sec.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        String econdePWD=passwordEncoder.encode("1234");
//        System.out.println(econdePWD);
//        auth.inMemoryAuthentication().withUser("user1").password(econdePWD).roles("USER").and()
//                .withUser("admin").password(passwordEncoder.encode("1111")).roles("USER","ADMIN").and()
//                .withUser("user2").password(passwordEncoder.encode("2345")).roles("USER");
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
//                .authoritiesByUsernameQuery("select username as principal , role as role from users_roles where username=?")
//                .rolePrefix("ROLE_")
//                .passwordEncoder(passwordEncoder);
        auth.userDetailsService(userDetailsService);


    }

    @Override
    //specification des droits d'accees
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/vvv").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }

}