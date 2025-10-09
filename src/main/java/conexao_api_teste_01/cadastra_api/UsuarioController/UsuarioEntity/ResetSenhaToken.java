package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonBackReference;  // <-- importar
import jakarta.persistence.*;

@Entity
@Table(name = "reset_senha_token_novo", 
       uniqueConstraints = { @UniqueConstraint(columnNames = "token") } )
public class ResetSenhaToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto_increment no MySQL
    private Long id;

    @Column(nullable = false, length = 36)
    private String token;

    @Column(name = "data_expiracao", nullable = false)
    private Instant dataExpiracao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference  // <-- anotação para evitar recursão infinita
    private UsuarioEntity usuario;

    public ResetSenhaToken() {}

    public ResetSenhaToken(String token, Instant dataExpiracao, UsuarioEntity usuario) {
        this.token = token;
        this.dataExpiracao = dataExpiracao;
        this.usuario = usuario;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Instant getDataExpiracao() {
        return dataExpiracao;
    }
    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
    public UsuarioEntity getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}
