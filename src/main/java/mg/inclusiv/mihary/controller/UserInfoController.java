package mg.inclusiv.mihary.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.exception.ValidationException;
import mg.inclusiv.mihary.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@Tag(name = "UserInfo", description = "API for userinfo")
@RestController
@CrossOrigin
public class UserInfoController {


    final
    private UtilisateurRepository userInfoRepository;

//    private HashData hashData = new HashData();

    public UserInfoController(UtilisateurRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @Operation(summary = "Création utilisateur", description = "Création nouvel utilisateur avec les infos nécessaires", tags = { "userinfo" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation réussie",
                    content = @Content(schema = @Schema(implementation = Utilisateur.class))) })
    @PostMapping("/user")
    public Boolean create(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        String username = body.get("username");
        if (userInfoRepository.existsByLogin(username)){

            throw new ValidationException("Nom d'utilisateur déjà utilisé");

        }

        String password = body.get("password");
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
//        String hashedPassword = hashData.get_SHA_512_SecurePassword(password);
        String fullname = body.get("fullname");
        String email = body.get("email");
        userInfoRepository.save(new Utilisateur(username, encodedPassword, fullname,email));
        return true;
    }

}

