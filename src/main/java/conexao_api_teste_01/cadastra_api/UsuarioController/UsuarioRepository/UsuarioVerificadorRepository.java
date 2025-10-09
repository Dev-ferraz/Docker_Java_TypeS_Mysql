package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioVerificadorEntity;

public interface UsuarioVerificadorRepository extends JpaRepository<UsuarioVerificadorEntity, Long> {
    Optional<UsuarioVerificadorEntity> findByUsuarioId(Long usuarioId);

    // Adicionando o método para buscar pelo UUID
    Optional<UsuarioVerificadorEntity> findByUuid(String uuid);
}
