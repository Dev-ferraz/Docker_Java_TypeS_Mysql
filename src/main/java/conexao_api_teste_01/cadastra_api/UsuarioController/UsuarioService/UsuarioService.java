package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.UsuarioDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ResetSenhaToken;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.TipoSituacaoUsuario.TipoSituacaoUsuario;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioVerificadorEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.ResetSenhaTokenRepository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.UsuarioRepository;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.UsuarioVerificadorRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService {

    private static final long TOKEN_EXPIRACAO_SEGUNDOS = 60 * 30; // 30 minutos

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioVerificadorRepository usuarioVerificadorRepository;

    @Autowired
    private ResetSenhaTokenRepository resetSenhaTokenRepository;

    // ------------------ LISTAR TODOS ------------------
    public List<UsuarioEntity> listaTodos() {
        return usuarioRepository.findAll();
    }

    // ------------------ CADASTRAR NOVO USUÁRIO ------------------
    public void inserirNovoUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO);
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuarioEntity.setSituacao(TipoSituacaoUsuario.PENDENTE);
        usuarioEntity = usuarioRepository.save(usuarioEntity);

        // Gera código de verificação
        UsuarioVerificadorEntity verificador = new UsuarioVerificadorEntity();
        verificador.setUsuario(usuarioEntity);

        String codigoNumerico = gerarCodigoNumerico(6);
        verificador.setUuid(codigoNumerico);
        verificador.setDataExpiracao(Instant.now().plusMillis(900000)); // 15 min
        usuarioVerificadorRepository.save(verificador);

        usuarioEntity.setCodigoValidacao(codigoNumerico);
        usuarioRepository.save(usuarioEntity);

        enviarEmailDeConfirmacao(usuarioDTO.getEmail(), codigoNumerico);
        log.info("Usuário cadastrado e e-mail enviado para {}", usuarioDTO.getEmail());
    }

    private String gerarCodigoNumerico(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private void enviarEmailDeConfirmacao(String email, String codigoValidacao) {
        String mensagem = String.format(
            "Olá,\n\nCadastro realizado com sucesso.\n\n" +
            "Seu código de validação é:\n==============================\n%s\n==============================\n\n" +
            "Se você não realizou este cadastro, ignore este e-mail.\n\nAtenciosamente,\nEquipe do Sistema",
            codigoValidacao
        );
        emailService.enviarEmailTexto(email, "Confirmação de Cadastro", mensagem);
    }

    // ------------------ BUSCAR POR ID ------------------
    public Optional<UsuarioEntity> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // ------------------ VERIFICAR CADASTRO ------------------
    public ResponseEntity<String> verificarCadastro(String uuid) {
        UsuarioVerificadorEntity usuarioVerificacao = usuarioVerificadorRepository.findByUuid(uuid)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado ou já verificado"));

        if (usuarioVerificacao.getDataExpiracao().isAfter(Instant.now())) {
            UsuarioEntity usuario = usuarioVerificacao.getUsuario();
            usuario.setSituacao(TipoSituacaoUsuario.ATIVO);
            usuarioRepository.save(usuario);
            usuarioVerificadorRepository.delete(usuarioVerificacao);
            return ResponseEntity.ok("Usuário verificado com sucesso!");
        } else {
            usuarioVerificadorRepository.delete(usuarioVerificacao);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código expirado, solicite um novo.");
        }
    }

    // ------------------ ALTERAR ------------------
    public UsuarioEntity alterar(Long id, UsuarioEntity usuarioAtualizado) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + id));
    }

    // ------------------ EXCLUIR ------------------
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    // ------------------ LOGIN ------------------
public boolean autenticar(String email, String senha) {
    UsuarioEntity usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

    if (!passwordEncoder.matches(senha, usuario.getSenha())) {
        throw new BadCredentialsException("Senha incorreta");
    }

    if (usuario.getSituacao() != TipoSituacaoUsuario.ATIVO) {
        throw new BadCredentialsException("Usuário ainda não validado. Confirme seu email.");
    }

    return true;
}

    // ------------------ RESET DE SENHA ------------------
    @Transactional
    public void solicitarResetSenha(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Email não cadastrado"));

        Optional<ResetSenhaToken> tokenExistenteOpt = resetSenhaTokenRepository
            .findAllByUsuarioAndDataExpiracaoAfter(usuario, Instant.now())
            .stream()
            .findFirst();

        if (tokenExistenteOpt.isPresent()) {
            throw new IllegalStateException("Já existe um link de redefinição válido enviado para este e-mail.");
        }

        resetSenhaTokenRepository.deleteAllByUsuario(usuario);

        ResetSenhaToken token = new ResetSenhaToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUsuario(usuario);
        token.setDataExpiracao(Instant.now().plusSeconds(TOKEN_EXPIRACAO_SEGUNDOS));
        resetSenhaTokenRepository.save(token);

       String url = "http://localhost:5174/API_html/05_PG_redefinirSenha.html?token=" + token.getToken();

        String mensagem = String.format(
            "Olá %s,\n\nClique no link abaixo para redefinir sua senha:\n%s\n\nEste link expira em 30 minutos.",
            usuario.getNome(), url
        );
        emailService.enviarEmailTexto(email, "Redefinição de Senha", mensagem);
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new IllegalArgumentException("A nova senha não pode estar vazia.");
        }

        ResetSenhaToken tokenEntity = resetSenhaTokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Token inválido ou expirado."));

        if (tokenEntity.getDataExpiracao().isBefore(Instant.now())) {
            resetSenhaTokenRepository.delete(tokenEntity);
            throw new IllegalArgumentException("Token expirado. Solicite um novo link.");
        }

        UsuarioEntity usuario = tokenEntity.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        resetSenhaTokenRepository.deleteAllByUsuario(usuario);
    }
}
