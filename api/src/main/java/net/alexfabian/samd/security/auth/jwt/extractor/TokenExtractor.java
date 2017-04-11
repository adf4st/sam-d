package net.alexfabian.samd.security.auth.jwt.extractor;

/**
 * Created by alexfabian on 2/20/17.
 */
public interface TokenExtractor {
    public String extract(String payload);
}
