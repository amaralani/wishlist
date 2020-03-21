package ir.maralani.wishlist.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Simple wrapper that lets us inject a
 * {@link org.springframework.security.core.annotation.AuthenticationPrincipal}
 * in web tier to get {@link org.springframework.security.core.userdetails.User} compatible instance.
 *
 * Also, we use this to wrap a {@link org.springframework.security.core.userdetails.User},
 * so we can use the added ID field to lookup the actual {@link User} from datasource.
 *
 */
public class UserDetailsWrapper extends org.springframework.security.core.userdetails.User {

    /**
     * Id to help with looking up the actual {@link User} from DB.
     */
    private final Long id;

    /**
     * @param id          Id of the actual {@link User} to be looked up from DB.
     * @param username    username
     * @param password    password
     * @param authorities authorities
     */
    public UserDetailsWrapper(long id, String username, String password,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    /**
     * @param id                    Id of the actual {@link User} to be looked up from DB.
     * @param username              username
     * @param password              password
     * @param enabled               enabled
     * @param accountNonExpired     accountNonExpired
     * @param credentialsNonExpired credentialsNonExpired
     * @param accountNonLocked      accountNonLocked
     * @param authorities           authorities
     */
    public UserDetailsWrapper(long id, String username, String password, boolean enabled,
                              boolean accountNonExpired, boolean credentialsNonExpired,
                              boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    /**
     * @return {@code id}
     * @see #id
     */
    public Long getId() {
        return id;
    }
}
