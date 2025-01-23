package com.example.library.security;
import com.example.library.AppScopeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AppUserDetailsService appUserDetailsService;

    public SecurityConfig(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AppScopeBean appScopeBean) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/register",
                                "/login",
                                "/js/**",
                                "/css/**",
                                "/img/**",
                                "/"
                        ).permitAll()
                        .requestMatchers("/allactiveloans").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/insertloan").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/returnloan/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/allbooks").hasRole("ADMIN")
                        .requestMatchers("/insertbook").hasRole("ADMIN")
                        .requestMatchers("/bookdelete/**").hasRole("ADMIN")
                        .requestMatchers("/bookedit/**").hasRole("ADMIN")
                        .requestMatchers("/doupdatebook").hasRole("ADMIN")
                        .requestMatchers("/allauthors").hasRole("ADMIN")
                        .requestMatchers("/insertauthor").hasRole("ADMIN")
                        .requestMatchers("/authordelete/**").hasRole("ADMIN")
                        .requestMatchers("/authoredit/**").hasRole("ADMIN")
                        .requestMatchers("/doupdateauthor").hasRole("ADMIN")
                        .requestMatchers("allcategories").hasRole("ADMIN")
                        .requestMatchers("/insertcategory").hasRole("ADMIN")
                        .requestMatchers("/categorydelete/**").hasRole("ADMIN")
                        .requestMatchers("/categoryedit/**").hasRole("ADMIN")
                        .requestMatchers("/doupdatecategory").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .successHandler((request, response, authentication) -> {
                            appScopeBean.setNumberofusers(appScopeBean.getNumberofusers() + 1);
                            response.sendRedirect("/showdashboard");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            appScopeBean.setNumberofusers(appScopeBean.getNumberofusers() - 1);
                            response.sendRedirect("/login");
                        })
                        .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

}
