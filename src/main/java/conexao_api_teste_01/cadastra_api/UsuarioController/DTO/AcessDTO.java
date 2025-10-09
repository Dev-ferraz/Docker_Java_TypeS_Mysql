package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;

public class AcessDTO {
    private String token;
    private String username;
    private Long usuarioId;

    public AcessDTO() {}

    public AcessDTO(String token, String username, Long usuarioId) {
        this.token = token;
        this.username = username;
        this.usuarioId = usuarioId;
    }

    // Construtor para erro interno (opcional)
    public AcessDTO(String mensagemErro) {
        this.token = null;
        this.username = mensagemErro;
        this.usuarioId = null;
    }

    // Getters e setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
