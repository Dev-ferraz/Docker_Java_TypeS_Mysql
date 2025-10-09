package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.CategoriaEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Cadastrar categoria
    public CategoriaEntity cadastrarCategoria(String nome) {
        if (categoriaRepository.existsByNome(nome)) {
            throw new IllegalArgumentException("Categoria já existe!");
        }
        CategoriaEntity novaCategoria = new CategoriaEntity(nome);
        return categoriaRepository.save(novaCategoria);
    }

    // Listar todas as categorias
    public List<CategoriaEntity> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Atualizar categoria
    public CategoriaEntity atualizarCategoria(Long id, String novoNome) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada!"));
        categoria.setNome(novoNome);
        return categoriaRepository.save(categoria);
    }

    // Excluir categoria
    public void excluirCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada!");
        }
        categoriaRepository.deleteById(id);
    }
}
