package pl.abbl.reactchat.definitions;

public class SecurityConstants {
    public static final String SECRET = "SuPerSECRETkeyToChangeOnProduction";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_IN_URL = "/signIn";
}
