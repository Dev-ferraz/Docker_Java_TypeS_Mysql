package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ProdutoEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService.ProdutoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // ======================
    // CADASTRO DE PRODUTO
    // ======================
    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody Map<String, Object> body) {
        try {
            String nome = (String) body.get("nome");

            String precoStr = body.get("preco").toString();
            precoStr = precoStr.replace(".", "").replace(",", ".");
            Double preco = Double.valueOf(precoStr);

            String descricao = body.get("descricao") != null ? (String) body.get("descricao") : "";

            Map<String, Object> usuarioMap = (Map<String, Object>) body.get("usuario");
            Long usuarioId = Long.valueOf(usuarioMap.get("id").toString());

            Map<String, Object> categoriaMap = (Map<String, Object>) body.get("categoria");
            Long categoriaId = Long.valueOf(categoriaMap.get("id").toString());

            ProdutoEntity produtoSalvo = produtoService.cadastrarProduto(nome, preco, descricao, usuarioId, categoriaId);

            return ResponseEntity.status(201).body(Map.of(
                "id", produtoSalvo.getId(),
                "nome", produtoSalvo.getNome(),
                "preco", produtoSalvo.getPreco(),
                "descricao", produtoSalvo.getDescricao(),
                "categoria", produtoSalvo.getCategoria(),
                "usuario", produtoSalvo.getUsuario(),
                "message", "Produto cadastrado com sucesso!"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            if (e.getMessage().contains("Produto já existe")) {
                return ResponseEntity.badRequest().body(Map.of("message", "⚠️ Esse produto já foi cadastrado!"));
            }
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao cadastrar produto."));
        }
    }

    // ======================
    // LISTAR PRODUTOS
    // ======================
    @GetMapping
    public ResponseEntity<?> listarProdutos() {
        try {
            List<ProdutoEntity> produtos = produtoService.listarProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao listar produtos."));
        }
    }

    // ======================
    // ATUALIZAR PRODUTO
    // ======================
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            String nome = (String) body.get("nome");

            Double preco = Double.valueOf(body.get("preco").toString());
            String descricao = body.get("descricao") != null ? (String) body.get("descricao") : "";

            Map<String, Object> categoriaMap = (Map<String, Object>) body.get("categoria");
            Long categoriaId = Long.valueOf(categoriaMap.get("id").toString());

            Map<String, Object> usuarioMap = (Map<String, Object>) body.get("usuario");
            Long usuarioId = Long.valueOf(usuarioMap.get("id").toString());

            ProdutoEntity produtoAtualizado = produtoService.atualizarProduto(id, nome, preco, descricao, usuarioId, categoriaId);

            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao atualizar produto."));
        }
    }

    // ======================
    // DELETAR PRODUTO
    // ======================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id) {
        try {
            produtoService.excluirProduto(id);
            return ResponseEntity.ok(Map.of("message", "Produto excluído com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao excluir produto."));
        }
    }
}
