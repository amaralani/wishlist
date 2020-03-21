

package ir.maralani.wishlist.domain.converter;

import ir.maralani.wishlist.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import static ir.maralani.wishlist.Constants.Crypto.ALGORITHM_PROPERTY;
import static ir.maralani.wishlist.Constants.Crypto.KEY_PROPERTY;

/**
 * Encrypts and decrypts CLOB stored data to/from plain text format.
 */
@Component
@Converter
public class JPACryptoConverter implements AttributeConverter<String, String> {

    private Logger logger = LoggerFactory.getLogger(JPACryptoConverter.class);

    private static String ALGORITHM = null;
    private static byte[] KEY = null;

    private final Environment environment;

    @Autowired
    public JPACryptoConverter(Environment environment) {
        this.environment = environment;
        String key;
        String algorithm;
        algorithm = this.environment.getProperty(ALGORITHM_PROPERTY);
        key = this.environment.getProperty(KEY_PROPERTY);

        if (algorithm == null || key == null) {
            logger.warn("Could not encryption properties, using insecure encryption key.");
            algorithm = "AES/ECB/PKCS5Padding";
            key = Constants.Crypto.DEFAULT_KEY;
        }

        ALGORITHM = algorithm;
        KEY = key.getBytes();
    }

    @Override
    public String convertToDatabaseColumn(String sensitive) {
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return new String(Base64Utils.encode(c.doFinal(sensitive.getBytes())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String sensitive) {
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64Utils.decode(sensitive.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
