package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ResetSenhaToken;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;

public interface ResetSenhaTokenRepository extends JpaRepository<ResetSenhaToken, Long> {
    Optional<ResetSenhaToken> findByToken(String token);

    // Método para deletar todos os tokens associados a um usuário
    void deleteAllByUsuario(UsuarioEntity usuario);
    // Adicione este método para buscar tokens válidos
    List<ResetSenhaToken> findAllByUsuarioAndDataExpiracaoAfter(UsuarioEntity usuario, Instant now);
}
