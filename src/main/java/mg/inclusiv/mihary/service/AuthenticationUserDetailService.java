package mg.inclusiv.mihary.service;

import lombok.RequiredArgsConstructor;
import mg.inclusiv.mihary.entity.Utilisateur;
import mg.inclusiv.mihary.repository.UtilisateurRepository;
import mg.inclusiv.mihary.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {

    @Autowired
    private final UtilisateurRepository utilisateurRepository;

   /* @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> apiUser = userService.findByLogin(username);
        if (apiUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(apiUser.get().getLogin(),
                apiUser.get().getMdpUtilisateur(), getAuthorities(apiUser.get().getTypeUtilisateur().toString()));
    }
*/
   @Override
   @Transactional
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Utilisateur user = utilisateurRepository.findByLogin(username)
               .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

       return UserDetailsImpl.build(user);
   }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
