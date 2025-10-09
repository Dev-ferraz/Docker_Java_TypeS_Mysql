package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;

import org.springframework.beans.BeanUtils;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.TipoSituacaoUsuario.TipoSituacaoUsuario;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;

public class UsuarioDTO {

    private Long id;    
    private String nome;    
    private String senha;
    private String email;
    private TipoSituacaoUsuario situacao;

    // Construtor que copia propriedades da entity
    public UsuarioDTO(UsuarioEntity usuario) {
        BeanUtils.copyProperties(usuario, this);
    }

    public UsuarioDTO() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public TipoSituacaoUsuario getSituacao() {
        return situacao;
    }
    public void setSituacao(TipoSituacaoUsuario situacao) {
        this.situacao = situacao;
    }
}
