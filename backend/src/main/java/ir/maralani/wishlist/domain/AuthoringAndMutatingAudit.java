

package ir.maralani.wishlist.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

/**
 * Entity including auditing fields.
 *
 * @author Sajad Hayatlou
 */
@MappedSuperclass
public class AuthoringAndMutatingAudit {

    /**
     * Username of the principal responsible for creation of this record
     */
    @CreatedBy
    private String createdBy;

    /**
     * {@code ZonedDateTime} representing date/time in which this record was created
     */
    @CreatedDate
    private ZonedDateTime createdDate;

    /**
     * Username of the principal responsible for modifying this record
     */
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * {@code ZonedDateTime} representing date/time in which this record was modified
     */
    @LastModifiedDate
    private ZonedDateTime lastModifiedDate;

    /**
     * @return {@code createdBy}
     * @see #createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy {@code createdBy}
     * @see #createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return {@code createdDate}
     * @see #createdDate
     */
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate {@code createdDate}
     * @see #createdDate
     */
    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return {@code lastModifiedBy}
     * @see #lastModifiedBy
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * @param lastModifiedBy {@code lastModifiedBy}
     * @see #lastModifiedBy
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * @return {@code lastModifiedDate}
     * @see #lastModifiedDate
     */
    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @param lastModifiedDate {@code lastModifiedDate}
     * @see #lastModifiedDate
     */
    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
