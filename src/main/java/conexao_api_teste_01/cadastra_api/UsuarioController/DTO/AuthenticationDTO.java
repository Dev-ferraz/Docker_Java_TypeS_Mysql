package conexao_api_teste_01.cadastra_api.UsuarioController.DTO;

public class AuthenticationDTO {
    private String email;
    private String password;

    // Getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
