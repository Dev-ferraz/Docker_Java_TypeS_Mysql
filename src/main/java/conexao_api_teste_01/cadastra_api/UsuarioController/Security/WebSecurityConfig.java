package conexao_api_teste_01.cadastra_api.UsuarioController.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import conexao_api_teste_01.cadastra_api.UsuarioController.Security.Validacao_JWT.AuthEntryPointJwt;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthFilterToken authFilterToken() {
        return new AuthFilterToken();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 🔹 Recursos públicos (HTML, JS, CSS, imagens, favicon)
                .requestMatchers(
                    "/", "/index.html", "/favicon.ico",
                    "/static/**", "/assets/**", "/Components_Js/**", "/css/**",
                    "/Components_style_css/**", "/Components_imgs_site/**",
                    "/api_html/**",
                    "/pg_workspace/**",
                    "/usuario/**",
                    "/auth/login",
                    "/auth/validarToken"
                ).permitAll()
                
                // 🔹 API protegida
                .requestMatchers("/api/categorias/**", "/api/produtos/**").authenticated()
                
                // 🔹 Qualquer outra requisição precisa estar autenticada
                .anyRequest().authenticated()
            )
            // 🔹 Configura sessão stateless (JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 🔹 Tratamento de erros de autenticação
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        // 🔹 Adiciona filtro JWT antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
