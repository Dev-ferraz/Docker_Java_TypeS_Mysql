package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.AcessDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.AuthenticationDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.Security.Validacao_JWT.JwtUtils;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

 public AcessDTO login(AuthenticationDTO authDto) {
    try {
        // Autenticação via email e senha
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
        );

        // Se passou pela autenticação
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera token JWT
        String token = jwtUtils.generateTokenFromUserDetailsImpl(userDetails);

        // 🔹 Aqui precisa enviar também o usuarioId
        return new AcessDTO(token, userDetails.getUsername(), userDetails.getId());

    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Credenciais inválidas");
    }
}

}
