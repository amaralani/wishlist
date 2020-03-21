

package ir.maralani.wishlist.domain;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

/**
 * Entity class contains only modify auditing fields.
 *
 * @author Sajad Hayatlou
 */
@MappedSuperclass
public class MutatingAudit {

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
