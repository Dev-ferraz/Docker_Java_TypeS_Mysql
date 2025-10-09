package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;



import org.springframework.beans.BeanUtils;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.ProdutoEntity;

public class ProdutoRequestDTO {
    private String nome;
    private Double preco;
    private String descricao;
    private Long categoriaId;
    private Long usuarioId;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}