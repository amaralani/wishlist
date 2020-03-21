package ir.maralani.wishlist.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Audit aware bean which is used to get information about the current logged in user
 * to be used in updating {@link org.springframework.data.annotation.CreatedBy} in JPA entities.
 * <p>
 * This is actually one of the reasons we chose not to let users change their username.
 * Working with {@code Authentication} objects instead of plain strings is a bit tricky and troublesome.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /**
     * Get current user's username.
     *
     * @return username
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        return Optional.of(((UserDetails) authentication.getPrincipal()).getUsername());
    }
}
