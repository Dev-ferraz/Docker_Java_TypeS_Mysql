package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.AcessDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.AuthenticationDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.LoginResponseDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.Security.Validacao_JWT.JwtUtils;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
        try {
            AcessDTO acesso = authService.login(authDto);

            LoginResponseDTO response = new LoginResponseDTO(
                    acesso.getToken(),
                    acesso.getUsuarioId(),
                    acesso.getUsername()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("⚠️ Usuário ou senha inválidos.");
        }
    }

    @GetMapping("/validarToken")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (jwtUtils.validateJwtToken(token)) {
                return ResponseEntity.ok("Token válido.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Erro ao validar o token: " + e.getMessage());
        }
    }
}
