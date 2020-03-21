package ir.maralani.wishlist;

public class Constants {
    /**
     * Spring Profiles
     */
    public static class Profiles {

        public static final String DEV = "dev";
        public static final String PROD = "prod";

        /**
         * No-Op private constructor enforcing final status
         */
        private Profiles() {
            throw new IllegalAccessError("Cannot be instantiated.");
        }
    }

    public static class Validation {

        public static final String STARTUP_MISSING_PROPERTIES_MESSAGE =
                "\n[ERROR]: You should provide keystore parameters using "
                        + "one of the methods discussed in the installation manual.\n";

        public static final int PASSWORD_MIN_LENGTH = 8;
        // Make sure this is always less than 72 bytes, otherwise it would be truncated
        public static final int PASSWORD_MAX_LENGTH = 64;
        public static final int PASSWORD_MIN_UPPERCASE = 1;
        public static final int PASSWORD_MIN_LOWERCASE = 1;
        public static final int PASSWORD_MIN_DIGIT = 1;

        public static final String USER_USERNAME_REGEX = "^[_'.@A-Za-z0-9-]*$";
        public static final int USER_USERNAME_SIZE_MIN = 1;
        public static final int USER_USERNAME_SIZE_MAX = 64;
        public static final int USER_PASSWORD_SIZE_MAX = 60;
        public static final int USER_FIRST_NAME_SIZE_MAX = 48;
        public static final int USER_LAST_NAME_SIZE_MAX = 48;
        public static final int USER_EMAIL_SIZE_MAX = 64;
        public static final int USER_PHONE_SIZE_MAX = 32;
        public static final int USER_LANG_SIZE_MAX = 5;
        public static final int USER_TZ_SIZE_MAX = 48;
        public static final int USER_DESC_SIZE_MAX = 255;


        public static final String ROLE_NAME_REGEX = "^[_'.@A-Za-z0-9-]*$";
        public static final int ROLE_NAME_SIZE_MIN = 5;
        public static final int ROLE_NAME_SIZE_MAX = 59; // 64 - 5 (ROLE_ prefix)
        public static final int ROLE_NAME_PREFIXED_SIZE_MAX = 64;

        public static final int ROLE_DESCRIPTION_SIZE_MIN = 1;
        public static final int ROLE_DESCRIPTION_SIZE_MAX = 255;


        public static final int SYSTEM_METRICS_LABEL_MAX_SIZE = 64;

        public static final int HTTP_SESSION_TIMEOUT_MIN = 30;
        public static final int HTTP_SESSION_TIMEOUT_DEFAULT = 600;

        public static final int LOGIN_ATTEMPT_SETTINGS_RESET_INTERVAL_MIN = 900;
        public static final int LOGIN_ATTEMPT_SETTINGS_RESET_INTERVAL_MAX = 3600;

        public static final int LOGIN_ATTEMPT_SETTINGS_MAX_FAILED_MIN = 3;
        public static final int LOGIN_ATTEMPT_SETTINGS_MAX_FAILED_MAX = 100;

        public static final int BROADCAST_MESSAGE_SIZE_MIN = 1;
        public static final int BROADCAST_MESSAGE_SIZE_MAX = 1024;

        /**
         * @see <a href="http://stackoverflow.com/a/163684/1393484">Here's what RegexBuddy uses!</a>
         */
        public static final String LDAP_URL_REGEX = "^ldaps?://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

        public static final int MFA_TRANSACTION_ID_SIZE_MAX = 32 + 4;
        public static final int MFA_PROVIDER_TIMEOUT_MIN = 5;
        public static final int MFA_SCHEMES_SIZE_MAX = 128;
        public static final int MFA_ACCEPTTO_API_ENDPOINT_SIZE_MAX = 256;
        public static final int MFA_ACCEPTTO_NOTIFICATION_ENDPOINT_SIZE_MAX = 256;
        public static final int MFA_ACCEPTTO_UID_PLAIN_SIZE_MAX = 256;
        public static final int MFA_ACCEPTTO_UID_CIPHER_SIZE_MAX =
                MFA_ACCEPTTO_UID_PLAIN_SIZE_MAX + 16 - (255 % 16) + 16; // AES block size + IV
        public static final int MFA_ACCEPTTO_SECRET_PLAIN_SIZE_MAX = 256;
        public static final int MFA_ACCEPTTO_SECRET_CIPHER_SIZE_MAX =
                MFA_ACCEPTTO_SECRET_PLAIN_SIZE_MAX + 16 - (255 % 16) + 16; // AES block size + IV
        // Min number of time-step that are checked during the validation process
        public static final int MFA_WINDOW_SIZE_MIN = 1;
        public static final int MFA_WINDOW_SIZE_MAX = 5;
        public static final int MFA_CODE_LENGTH_MIN = 6;
        public static final int MFA_CODE_LENGTH_MAX = 8;

        public static final int CLIENT_FINGERPRINT_SIZE_MAX = 2 * 1024;

        /**
         * No-Op private constructor enforcing final status
         */
        private Validation() {
            throw new IllegalAccessError("Cannot be instantiated.");
        }
    }

    public static class Crypto {
        public static final String ALGORITHM_PROPERTY = "ENCRYPTION_ALGORITHM";
        public static final String KEY_PROPERTY = "ENCRYPTION_KEY";
        public static final String DEFAULT_KEY = "Jk7q93nlSyaxXVmz31kJbPNS1SsYO0kCB217S37J";
    }
}
