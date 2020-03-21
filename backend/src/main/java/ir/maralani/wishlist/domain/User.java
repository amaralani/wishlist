package ir.maralani.wishlist.domain;

import ir.maralani.wishlist.Constants;
import ir.maralani.wishlist.domain.converter.LocaleAttributeConverter;
import ir.maralani.wishlist.domain.converter.TimeZoneAttributeConverter;
import ir.maralani.wishlist.validator.PhoneNumber;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Main actor of the system.
 * Each user is assigned a {@link Role}.
 *
 * @author Aarash Yaadegarnia
 * @author Sajad Hayatlou
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends AuthoringAndMutatingAudit implements Serializable {

    /**
     * Id of an entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Indicates the user type. This type can shed more light on where the user credentials
     * are actually stored
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{user.type.null}")
    private Type type;

    /**
     * Unique and immutable identifier of this user across the system.
     * Username MUST NOT be changed after creation and is used literally
     * to show relations and references to other entities in the system.
     */
    @NotBlank(message = "{user.username.null}")
    @Pattern(regexp = Constants.Validation.USER_USERNAME_REGEX, message = "{user.username.pattern}")
    @Size(min = Constants.Validation.USER_USERNAME_SIZE_MIN,
            max = Constants.Validation.USER_USERNAME_SIZE_MAX,
            message = "{user.username.size}")
    @Column(length = Constants.Validation.USER_USERNAME_SIZE_MAX, unique = true, nullable = false)
    private String username;

    /**
     * Hash of the password.
     * Size constraints are based on used hashing algorithm,
     * and should be updated accordingly if needed.
     */
    @Column(name = "password", length = Constants.Validation.USER_PASSWORD_SIZE_MAX)
    private String password;

    /**
     * First name of the user.
     */
    @Size(max = Constants.Validation.USER_FIRST_NAME_SIZE_MAX, message = "{user.first_name.size}")
    @Column(name = "f_name", length = Constants.Validation.USER_FIRST_NAME_SIZE_MAX)
    private String firstName;

    /**
     * Last nane of the user.
     */
    @Size(max = Constants.Validation.USER_LAST_NAME_SIZE_MAX, message = "{user.last_name.size}")
    @Column(name = "l_name", length = Constants.Validation.USER_LAST_NAME_SIZE_MAX)
    private String lastName;

    /**
     * E-mail address of the user.
     */
    @Email(message = "{user.email.pattern}")
    @Size(max = Constants.Validation.USER_EMAIL_SIZE_MAX, message = "{user.email.size}")
    @Column(length = Constants.Validation.USER_EMAIL_SIZE_MAX, unique = true)
    private String email;

    /**
     * Phone number of the user.
     */
    @PhoneNumber(message = "{user.phone.pattern}")
    @Size(max = Constants.Validation.USER_PHONE_SIZE_MAX, message = "{user.phone.size}")
    @Column(length = Constants.Validation.USER_PHONE_SIZE_MAX, unique = true)
    private String phone;


    /**
     * Whether this user is enabled.
     * Disabled users cannot login to system.
     */
    @NotNull(message = "{user.enabled.null}")
    @Column(nullable = false)
    private Boolean enabled = false;

    /**
     * Whether this user is locked.
     * Locked users cannot login to system.
     */
    @NotNull(message = "{user.locked.null}")
    @Column(nullable = false)
    private Boolean locked = false;

    /**
     * Date and time in future that this user will no longer be considered valid.
     */
    @Future(message = "user.expiration_date.future")
    @Column(name = "expiration_date")
    private ZonedDateTime expirationDate;

    /**
     * Whether this user has to change his/her credentials.
     */
    @NotNull(message = "{user.cred_expired.null}")
    @Column(name = "cred_expired", nullable = false)
    private Boolean credentialExpired = true;

    /**
     * Preferred locale for this user.
     * This will affect many aspects of the user interface.
     */
    @NotNull(message = "{user.locale.null}")
    @Column(name = "lang", length = Constants.Validation.USER_LANG_SIZE_MAX)
    @Convert(converter = LocaleAttributeConverter.class)
    private Locale locale;

    /**
     * Timezone which this user resides in.
     */
    @NotNull(message = "{user.timezone.null}")
    @Column(name = "tz_id", length = Constants.Validation.USER_TZ_SIZE_MAX, nullable = false)
    @Convert(converter = TimeZoneAttributeConverter.class)
    private TimeZone timeZone;

    /**
     * {@linkplain Role} that this user belongs to.
     */
    @NotNull(message = "{user.role.null}")
    @ManyToOne(optional = false)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Role role;

    /**
     * Description about this user.
     */
    @Size(max = Constants.Validation.USER_DESC_SIZE_MAX, message = "{user.description.size}")
    private String description;

    /**
     * @return {@code id}
     * @see #id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return {@code type}
     * @see #type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type {@code type}
     * @see #type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return {@code username}
     * @see #username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username {@code username}
     * @see #username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return {@code password}
     * @see #password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password {@code password}
     * @see #password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return {@code firstName}
     * @see #firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName {@code firstName}
     * @see #firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return {@code lastName}
     * @see #lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName {@code lastName}
     * @see #lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return {@code email}
     * @see #email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email {@code email}
     * @see #email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return {@code phone}
     * @see #phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone {@code phone}
     * @see #phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return {@code enabled}
     * @see #enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled {@code enabled}
     * @see #enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return {@code locked}
     * @see #locked
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * @param locked {@code locked}
     * @see #locked
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @return {@code expirationDate}
     * @see #expirationDate
     */
    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate {@code expirationDate}
     * @see #expirationDate
     */
    public void setExpirationDate(ZonedDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * @return {@code credentialExpired}
     * @see #credentialExpired
     */
    public Boolean getCredentialExpired() {
        return credentialExpired;
    }

    /**
     * @param credentialExpired {@code credentialExpired}
     * @see #credentialExpired
     */
    public void setCredentialExpired(Boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    /**
     * @return {@code locale}
     * @see #locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale {@code locale}
     * @see #locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return {@code timeZone}
     * @see #timeZone
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone {@code timeZone}
     * @see #timeZone
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return {@code role}
     * @see #role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role {@code role}
     * @see #role
     */
    public void setRole(Role role) {
        this.role = role;
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
     * Tests whether the given user is an external user or not
     *
     * @return {@code true} if the user is an external user, {@code false} otherwise
     */
    @Transient
    public boolean isExternal() {
        return getType() == Type.EXTERNAL;
    }

    /**
     * Checks whether the current user account is expired.
     *
     * @return Whether the user is expired
     */
    public boolean isUserExpired() {
        return getExpirationDate() != null && ZonedDateTime.now().isAfter(getExpirationDate());
    }

    /**
     * @return String representation while masking sensitive information.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", credentialExpired=" + credentialExpired +
                ", expirationDate=" + expirationDate +
                ", locale=" + locale +
                ", timeZone=" + timeZone +
                ", role=" + role +
                ", createdBy='" + getCreatedBy() + '\'' +
                ", createdDate=" + getCreatedDate() +
                ", lastModifiedBy='" + getLastModifiedBy() + '\'' +
                ", lastModifiedDate=" + getLastModifiedDate() +
                '}';
    }

    /**
     * Represents the type of the user. The type would have some implications about
     * where user credentials are actually stored. We have two categories of user types:
     * <ul>
     * <li>Internal users are those that we store their credentials and user
     * authentication is equivalent to a database query</li>
     * <li>External users are those users that their credentials and other metadata
     * are stored in an external service like LDAP and we just have a reference to
     * them.</li>
     * </ul>
     */
    public enum Type {

        /**
         * The default and internal database driven user source
         */
        INTERNAL,

        /**
         * User information are stored in an external service
         */
        EXTERNAL
    }
}
