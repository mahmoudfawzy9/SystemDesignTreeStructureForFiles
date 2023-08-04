package com.mahmoud.stc.config;


import com.mahmoud.stc.enums.Role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final JwtAuthenticationFilter jwtAuthFilter;

@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private static  RequestMatcher protectedUrlList ;
    private static  RequestMatcher publicUrlList ;

    AuthenticationProvider provider;

   //as it's sample application : )
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable());
        return http.build();
}

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .and()
                .cors((Customizer<CorsConfigurer<HttpSecurity>>) (configurer -> {
                    CorsConfigurationSource source = corsConfigurationSource();
                    configurer.configurationSource(source);
                }));
        http
                .authorizeRequests()
                .requestMatchers("/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**").permitAll()
                .requestMatchers("/**").hasRole(Role.ADMIN.name())
                ;
    }

    private List<AuthPattern> permissions = asList(
            //url pattern	-------------------------	Method	------------	Roles
            patternOf( "/items/stc-assessments"			,HttpMethod.POST	, setOf(Role.ADMIN)),
            patternOf( "/items/stc-assessments/backend"	,HttpMethod.POST	, setOf(Role.ADMIN, Role.EDIT)),
            patternOf( "/items/assessment.pdf"				,HttpMethod.POST	, setOf(Role.ADMIN)),
            patternOf( "/items/{id}"		                ,HttpMethod.GET		, setOf(Role.ADMIN)),
            patternOf( "/items/{id}"			     		,HttpMethod.DELETE		, setOf(Role.ADMIN))
            );

    private List<AuthPattern> PUBLIC_URLS =
            asList(
                    patternOf("/callbacks/**")
                    , patternOf("/user/v2/register")
                    , patternOf("/user/login/**")
                    , patternOf("/user/register")
                    , patternOf("/items/avatar")

            );


    public SecurityConfig(final AuthenticationProvider authenticationProvider) {
        super();

        //allow created threads to inherit the parent thread security context
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        this.provider = authenticationProvider;

        List<RequestMatcher> protectedrequestMatcherList = permissions.stream().map(this::toAntPathRequestMatcher).collect(toList());
        protectedUrlList = new OrRequestMatcher( protectedrequestMatcherList );


        List<RequestMatcher> publicRequestMatcherList = PUBLIC_URLS.stream().map(this::toAntPathRequestMatcher).collect(toList());
        publicUrlList = new OrRequestMatcher( publicRequestMatcherList );
    }
    private static AuthPattern patternOf(String urlPattern , HttpMethod method  , Set<Role> roles) {
        return new AuthPattern(urlPattern, method, roles);
    };

    private static AuthPattern patternOf(String urlPattern ) {
        return patternOf(urlPattern, null , getAllRoles());
    }
    private static HashSet<Role> setOf(Role... roles) {
        return new HashSet<Role>(Arrays.asList(roles));
    }

    private static Set<Role> getAllRoles(){
        return new HashSet<>(Arrays.asList(Role.values()));
    }

    private AntPathRequestMatcher toAntPathRequestMatcher(AuthPattern pattern) {
        String method = Optional.ofNullable(pattern.getHttpMethod())
                .map(HttpMethod::toString)
                .orElse(null);
        return new AntPathRequestMatcher( pattern.getUrlPattern(), method);
    }

}
