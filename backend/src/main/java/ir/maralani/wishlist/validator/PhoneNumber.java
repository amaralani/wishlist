package ir.maralani.wishlist.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates a phone number, In all possible international formats.
 *
 */
@Documented
@Constraint(validatedBy = PhoneNumberConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "{phone_number_invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
