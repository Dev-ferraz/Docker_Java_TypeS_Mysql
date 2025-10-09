package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ProdutoEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    // Verifica se já existe produto com o mesmo nome, usuário e categoria
    boolean existsByNomeAndUsuarioIdAndCategoriaId(String nome, Long usuarioId, Long categoriaId);
}
