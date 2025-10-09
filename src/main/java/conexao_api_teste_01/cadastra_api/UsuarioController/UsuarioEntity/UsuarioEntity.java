package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import conexao_api_teste_01.cadastra_api.UsuarioController.DTO.UsuarioDTO;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.TipoSituacaoUsuario.TipoSituacaoUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cadastroUsuario_TF")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSituacaoUsuario situacao;

    @Column(name = "codigo_validacao", nullable = true)
    private String codigoValidacao;

    // Tokens de reset de senha
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ResetSenhaToken> tokens;

    // Produtos cadastrados pelo usuário
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProdutoEntity> produtos;

    // Construtor que copia as propriedades do DTO
    public UsuarioEntity(UsuarioDTO usuario) {
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();
        this.email = usuario.getEmail();
        // situacao será definido no Service ao cadastrar
    }

    public UsuarioEntity() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public TipoSituacaoUsuario getSituacao() { return situacao; }
    public void setSituacao(TipoSituacaoUsuario situacao) { this.situacao = situacao; }

    public String getCodigoValidacao() { return codigoValidacao; }
    public void setCodigoValidacao(String codigoValidacao) { this.codigoValidacao = codigoValidacao; }

    public List<ResetSenhaToken> getTokens() { return tokens; }
    public void setTokens(List<ResetSenhaToken> tokens) { this.tokens = tokens; }

    public List<ProdutoEntity> getProdutos() { return produtos; }
    public void setProdutos(List<ProdutoEntity> produtos) { this.produtos = produtos; }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UsuarioEntity other = (UsuarioEntity) obj;
        return Objects.equals(id, other.id);
    }
}
