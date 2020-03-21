package ir.maralani.wishlist.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

/**
 * Simple JPA Converter for {@code Locale} to {@code String}.
 *
 * @author Aarash Yaadegarnia
 */
@Converter
public class LocaleAttributeConverter implements AttributeConverter<Locale, String> {

    /**
     * @param locale {@code Locale} to convert from
     * @return target {@code String} type
     */
    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return (locale != null) ? locale.toLanguageTag() : null;
    }

    /**
     * @param languageTag {@code String} to convert from
     * @return target {@code Locale} type
     */
    @Override
    public Locale convertToEntityAttribute(String languageTag) {
        if (languageTag != null && !languageTag.isEmpty()) {
            return Locale.forLanguageTag(languageTag);
        } else
            return null;
    }
}
