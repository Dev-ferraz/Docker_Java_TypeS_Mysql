package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.CategoriaEntity;
import java.util.Optional;

@Repository
public interface CategoriaRepositor extends JpaRepository<CategoriaEntity, Long> {

    // Método para buscar categoria pelo nome ignorando maiúsculas/minúsculas
    Optional<CategoriaEntity> findByNomeIgnoreCase(String nome);
}
