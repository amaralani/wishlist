

package ir.maralani.wishlist.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.TimeZone;

/**
 * Simple JPA Converter for {@code TimeZone} to {@code String}.
 *
 * @author Aarash Yaadegarnia
 */
@Converter
public class TimeZoneAttributeConverter implements AttributeConverter<TimeZone, String> {

    /**
     * Use {@link java.time.ZoneId} to store as a string.
     *
     * @param timeZone {@code TimeZone} to convert from
     * @return target {@code String} type
     */
    @Override
    public String convertToDatabaseColumn(TimeZone timeZone) {
        return (timeZone != null) ? timeZone.getID() : null;
    }

    /**
     * @param zoneId {@code String} to convert from
     * @return target {@code TimeZone} type
     */
    @Override
    public TimeZone convertToEntityAttribute(String zoneId) {
        return (zoneId != null) ? TimeZone.getTimeZone(zoneId) : null;
    }
}
