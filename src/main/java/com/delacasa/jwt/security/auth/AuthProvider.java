package com.delacasa.jwt.security.auth;

import com.delacasa.jwt.entity.ApiRole;
import com.delacasa.jwt.entity.ApiUser;
import com.delacasa.jwt.service.ApiUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final ApiUserService apiUserService;
    private final PasswordEncoder passwordEncoder;


    // Essaie d'authentifier l'utilisateur en utilisant un objet Authentication fournit par UsernameAndPasswordFilter via l'authenticationManager.
    // BadCredentialsException indique que l'authentification a foirée (si je me souviens bien)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = (String) authentication.getPrincipal();
        final String password = (String) authentication.getCredentials();
        

        final ApiUser user = apiUserService.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new UsernamePasswordAuthenticationToken(username, null, setAuthorities(user));
        // Objet Authentication authResult utilisé par la méthode successfulAuthentication de UsernameAndPasswordFilter.
    }

    //Indique quel type d'authentification est géré.
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    //Objet par default utilisé par spring security pour gérer les authorisations.
    private List<GrantedAuthority> setAuthorities(final ApiUser user) {
        final List<GrantedAuthority> output = new ArrayList<>();
        final ApiRole role = user.getRole();

        if (role.getName() != null) {
            output.add(new SimpleGrantedAuthority(sanitizeRoleName((role.getName()))));
        }
        return output;
    }

    //Par default spring security veut un prefix devant les roles pour différencier les roles des autres permissions.
    private String sanitizeRoleName(final String roleName) {
        return roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
    }


}
