package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;

public class Categoria_DTO {

    private String nome;

    public Categoria_DTO() {}

    public Categoria_DTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}