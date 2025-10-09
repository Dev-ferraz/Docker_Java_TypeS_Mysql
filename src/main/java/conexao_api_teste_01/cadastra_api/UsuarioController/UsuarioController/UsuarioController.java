package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioController;



import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ProdutoEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService.ProdutoService;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService.UsuarioService;
import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.UsuarioDTO;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ------------------ LISTAR USUÁRIOS ------------------
    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listaTodos());
    }

    // ------------------ CADASTRAR USUÁRIO ------------------
    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.inserirNovoUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso! Verifique seu e-mail.✔");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar usuário.");
        }
    }

    // ------------------ BUSCAR POR ID ------------------
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------ ATUALIZAR DADOS ------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> alterarUsuario(@PathVariable Long id, @RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity usuarioAtualizado = usuarioService.alterar(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário.");
        }
    }

    // ------------------ EXCLUIR ------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------ VERIFICAR CADASTRO POR CÓDIGO ------------------
    @GetMapping("/verificarCadastro/{codigo}")
    public ResponseEntity<String> verificarCadastro(@PathVariable String codigo) {
        return usuarioService.verificarCadastro(codigo);
    }

    // ------------------ RESET DE SENHA ------------------
    @PostMapping("/solicitar-reset-senha")
    public ResponseEntity<String> solicitarResetSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("⚠️O campo 'email' é obrigatório.");
        }
        try {
            usuarioService.solicitarResetSenha(email);
            return ResponseEntity.ok("E-mail de redefinição enviado com sucesso.✔");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("⚠️Erro ao processar solicitação.");
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");

        if (token == null || token.trim().isEmpty() || novaSenha == null || novaSenha.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Os campos 'token' e 'novaSenha' são obrigatórios.");
        }
        try {
            usuarioService.redefinirSenha(token, novaSenha);
            return ResponseEntity.ok("Senha redefinida com sucesso.✔");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("⚠️Erro ao redefinir senha.");
        }
    }

    // ------------------ LOGIN ------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String senha = body.get("senha");

        if (login == null || senha == null) {
            return ResponseEntity.badRequest().body("⚠️Login e senha são obrigatórios.");
        }

        try {
            boolean autenticado = usuarioService.autenticar(login, senha);
            if (autenticado) {
                return ResponseEntity.ok("Login realizado com sucesso!✔");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("⚠️Credenciais inválidas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("⚠️Erro ao processar login.");
        }
    }

}