package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ProdutoEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.CategoriaEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.ProdutoRepository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.CategoriaRepository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.UsuarioRepository;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ======================
    // CADASTRAR PRODUTO
    // ======================
    public ProdutoEntity cadastrarProduto(String nome, Double preco, String descricao, Long usuarioId, Long categoriaId) throws Exception {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setDescricao(descricao);
        produto.setUsuario(usuario);
        produto.setCategoria(categoria);

        // Verifica duplicidade
        if (produtoRepository.existsByNomeAndUsuarioIdAndCategoriaId(
                produto.getNome(),
                usuarioId,
                categoriaId
        )) {
            throw new Exception("Produto já existe");
        }

        return produtoRepository.save(produto);
    }

    // ======================
    // LISTAR PRODUTOS
    // ======================
    public List<ProdutoEntity> listarProdutos() {
        return produtoRepository.findAll();
    }

    // ======================
    // ATUALIZAR PRODUTO
    // ======================
    public ProdutoEntity atualizarProduto(Long id, String nome, Double preco, String descricao, Long usuarioId, Long categoriaId) throws Exception {
        ProdutoEntity produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setDescricao(descricao);
        produto.setUsuario(usuario);
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    // ======================
    // EXCLUIR PRODUTO
    // ======================
    public void excluirProduto(Long id) throws Exception {
        ProdutoEntity produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }
}
