package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // Verifica se já existe usuário com determinado e-mail
    boolean existsByEmail(String email);

    // Busca usuário pelo e-mail (case sensitive)
    Optional<UsuarioEntity> findByEmail(String email);

    // Busca usuário pelo e-mail ignorando maiúsculas/minúsculas
    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);
}
