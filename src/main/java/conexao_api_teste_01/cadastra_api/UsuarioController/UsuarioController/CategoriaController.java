package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioController;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService.CategoriaService;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.CategoriaEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<?> cadastrarCategoria(@RequestBody Map<String, String> body) {
        try {
            String nome = body.get("nome");
            CategoriaEntity categoria = categoriaService.cadastrarCategoria(nome);
            return ResponseEntity.status(201).body(Map.of(
                    "id", categoria.getId(),
                    "nome", categoria.getNome(),
                    "message", "Categoria cadastrada com sucesso!"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao cadastrar categoria."));
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoriaEntity>> listarCategorias() {
        try {
            List<CategoriaEntity> categorias = categoriaService.listarTodas();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String novoNome = body.get("nome");
            CategoriaEntity categoria = categoriaService.atualizarCategoria(id, novoNome);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirCategoria(@PathVariable Long id) {
        try {
            categoriaService.excluirCategoria(id);
            return ResponseEntity.ok(Map.of("message", "Categoria excluída com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }
}
