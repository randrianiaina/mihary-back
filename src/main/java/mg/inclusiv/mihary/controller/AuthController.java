package mg.inclusiv.mihary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import mg.inclusiv.mihary.configuration.JwtToken;
import mg.inclusiv.mihary.interceptor.JwtRequest;
import mg.inclusiv.mihary.interceptor.JwtResponse;
import mg.inclusiv.mihary.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;

@RestController
@CrossOrigin
@Tag(name = "Authentication", description = "API l'authentification")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Operation(summary = "Authentification", description = "Authentification en utilisant login et mot de passe", tags = { "authenficaiton" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opération réussie",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))) })
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {


        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService

                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {

            throw new Exception("Utilisateur désactivé", e);

        } catch (BadCredentialsException e) {

            throw new Exception("Couple mot de passe /login incorrecte", e);

        }

    }

}
