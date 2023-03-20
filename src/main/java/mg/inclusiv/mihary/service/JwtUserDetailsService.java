package mg.inclusiv.mihary.service;


import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository userInfoRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur user = userInfoRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable,: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getMdpUtilisateur(),
                new ArrayList<>());
    }

}
