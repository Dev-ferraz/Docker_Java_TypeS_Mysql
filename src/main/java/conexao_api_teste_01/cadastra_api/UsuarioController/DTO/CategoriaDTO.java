package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;


import org.springframework.beans.BeanUtils;


public class CategoriaDTO {

    private Long id;
    private String nome;

    public CategoriaDTO(CategoriaDTO categoria) {
        BeanUtils.copyProperties(categoria, this);
    }

    public CategoriaDTO() {}

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
}