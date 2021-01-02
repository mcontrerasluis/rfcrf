package sat.gob.mx.agsc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user. 
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        System.out.println("extractPrincipal");
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication instanceof JwtAuthenticationToken) {
            return (String) ((JwtAuthenticationToken)authentication).getToken().getClaims().get("preferred_username");
        } else if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            Map<String, Object> attributes = ((DefaultOidcUser) authentication.getPrincipal()).getAttributes();
            if (attributes.containsKey("preferred_username")) {
                return (String) attributes.get("preferred_username");
            }
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        System.out.println("extractPrincipal1");
        return null;
    }


    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        System.out.println("isAuthenticated");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean isCurrentUserInRole(String authority) {
        System.out.println("isCurrentUserInRole");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).anyMatch(authority::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        System.out.println("authentication");
        Collection<? extends GrantedAuthority> authorities = authentication instanceof JwtAuthenticationToken ?
            extractAuthorityFromClaims(((JwtAuthenticationToken) authentication).getToken().getClaims())
            : authentication.getAuthorities();
        return authorities.stream()
            .map(GrantedAuthority::getAuthority);
    }

    public static List<GrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
        System.out.println("extractAuthorityFromClaims");
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    @SuppressWarnings("unchecked")
    private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        System.out.println("getRolesFromClaims");
        return (Collection<String>) claims.getOrDefault("groups",
            claims.getOrDefault("roles", new ArrayList<>()));
    }

    private static List<GrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        System.out.println("mapRolesToGrantedAuthorities");
        return roles.stream()
            .filter(role -> role.startsWith("ROLE_"))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}
