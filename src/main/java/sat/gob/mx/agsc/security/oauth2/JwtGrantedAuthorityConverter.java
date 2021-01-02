package sat.gob.mx.agsc.security.oauth2;

import sat.gob.mx.agsc.security.SecurityUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public JwtGrantedAuthorityConverter() {
        // Bean extracting authority.
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        System.out.println("decoder entra2");
        return SecurityUtils.extractAuthorityFromClaims(jwt.getClaims());
    }
}
