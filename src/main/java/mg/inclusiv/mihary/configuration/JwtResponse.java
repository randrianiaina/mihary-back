package mg.inclusiv.mihary.configuration;

import lombok.*;
import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    private List<String> roles;
    private String type = "Bearer";

    public JwtResponse(String token, String username, String email, List<String> roles) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
