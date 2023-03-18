package mg.inclusiv.mihary.entity;


import lombok.Data;

@Data
public class UserCreateRequest {
    private String login;
    private String mdpUtilisateur;
    private String emailUtilisateur;
    private String type;
}