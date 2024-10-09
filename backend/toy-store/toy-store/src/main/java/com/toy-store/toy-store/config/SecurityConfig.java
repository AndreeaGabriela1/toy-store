package com.petshop.petshop.config;

        import lombok.RequiredArgsConstructor;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.HttpMethod;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.web.SecurityFilterChain;
        import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET , "/chat-socket/**" , "/topic/**", "/chat/**", "/messages").permitAll() // Allow access to WebSocket endpoints
                        .requestMatchers(HttpMethod.POST, "/login", "/register", "/forgot-password", "/user-messages", "/save-message").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/review-id").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/category", "/category-delete", "/user-delete", "/user-edit","/category-edit").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pet-create", "/pet-delete", "/pet-edit").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/product-create", "/product-delete", "/product-edit").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.GET, "/product","/category","/pet").hasAuthority("CUSTOMER")
                        .anyRequest().permitAll())
        ;
        return http.build();
    }
}