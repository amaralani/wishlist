package ir.maralani.wishlist.domain;

import ir.maralani.wishlist.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Simple RoleName in the system as is usually defined in RBAC.
 *
 * @author Aarash Yaadegarnia
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    /**
     * Id of the entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Human readable name of this role.
     */
    @NotBlank(message = "{role.name.null}")
    @Pattern(regexp = Constants.Validation.ROLE_NAME_REGEX, message = "{role.name.pattern}")
    @Size(min = Constants.Validation.ROLE_NAME_SIZE_MIN, max = Constants.Validation.ROLE_NAME_PREFIXED_SIZE_MAX, message = "{role.name.size}")
    @Column(length = Constants.Validation.ROLE_NAME_PREFIXED_SIZE_MAX, unique = true, nullable = false)
    private String name;

    /**
     * Description of this role.
     */
    @NotBlank(message = "{role.description.null}")
    @Size(min = Constants.Validation.ROLE_DESCRIPTION_SIZE_MIN,
            max = Constants.Validation.ROLE_DESCRIPTION_SIZE_MAX,
            message = "{role.description.size}")
    @Column(name = "description", length = Constants.Validation.ROLE_DESCRIPTION_SIZE_MAX, nullable = false)
    private String description;

    /**
     * Whether this is an internal system role.
     * Internal system are immutable and cannot be removed or modified on any way.
     */
    @NotNull(message = "{role.internal.null}")
    @Column(name = "internal", nullable = false)
    private Boolean internal;

    /**
     * Collection of {@link User} which are assigned to this role and all the privileges it grants.
     */
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "role")
    private Set<User> users;

    /**
     * @return {@code id}
     * @see #id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return {@code name}
     * @see #name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name {@code name}
     * @see #name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@code description}
     * @see #description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description {@code description}
     * @see #description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return {@code internal}
     * @see #internal
     */
    public Boolean getInternal() {
        return internal;
    }

    /**
     * @param internal {@code internal}
     * @see #internal
     */
    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    /**
     * @return {@code users}
     * @see #users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users {@code users}
     * @see #users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * @return Name of the role as a human readable string.
     */
    @Override
    public String toString() {
        return name;
    }

}
