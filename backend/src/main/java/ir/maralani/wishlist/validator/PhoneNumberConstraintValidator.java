package ir.maralani.wishlist.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates a phone number, In all possible international formats.
 * <p>
 * Null values are considered valid, If this is not a desired outcome
 * they should be validated with {@link javax.validation.constraints.NotNull}
 *
 * @author Amir Maralani
 */
public class PhoneNumberConstraintValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber phoneNumber) {
        // No-Op
    }

    /**
     * Validator.
     *
     * @param phoneNumber PhoneNumber number to validate
     * @param context     Validator context
     * @return Whether validation was successful
     */
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty())
            return true;

        Phonenumber.PhoneNumber parsedNumber;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        // Try to parse the given string as phone number in specified locale
        try {
            parsedNumber = phoneUtil.parse(phoneNumber, getLocale());
        } catch (NumberParseException e) {
            // Failed to parse it properly
            return false;
        }

        // Only mobile phone numbers are capable of receiving SMS
        return phoneUtil.isValidNumber(parsedNumber)
            && phoneUtil.getNumberType(parsedNumber) == PhoneNumberUtil.PhoneNumberType.MOBILE;
    }

    /**
     * Use default locale. supporting non-English locales doesn't carry its own weight.
     *
     * @return Proper locale as string
     */
    private String getLocale() {
        return "US";
    }
}
