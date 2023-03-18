package mg.inclusiv.mihary.service;

import lombok.RequiredArgsConstructor;
import mg.inclusiv.mihary.entity.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UtilisateurService userService;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur apiUser = userService.readUserByUsername(username);
        if (apiUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(apiUser.getLogin(),
                apiUser.getMdpUtilisateur(), getAuthorities(apiUser.getTypeUtilisateur().toString()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
