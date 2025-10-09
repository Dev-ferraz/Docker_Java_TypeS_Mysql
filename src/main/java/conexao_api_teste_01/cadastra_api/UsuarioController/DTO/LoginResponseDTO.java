package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;

public class LoginResponseDTO {
    private String token;
    private Long usuarioId;
    private String nome;

    public LoginResponseDTO(String token, Long usuarioId, String nome) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.nome = nome;
    }

    // Getters e setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
