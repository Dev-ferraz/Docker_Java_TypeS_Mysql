package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;

public class Produto_DTO {

    private String nome;
    private Double preco;
    private String descricao;
    private Long categoriaId; // apenas o ID da categoria
    private Long usuarioId;   // apenas o ID do usuário logado

    public Produto_DTO() {}

    public Produto_DTO(String nome, Double preco, String descricao, Long categoriaId, Long usuarioId) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.usuarioId = usuarioId;
    }

    // ===== Getters e Setters =====
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
