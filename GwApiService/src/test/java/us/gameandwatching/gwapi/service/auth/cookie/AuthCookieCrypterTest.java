package us.gameandwatching.gwapi.service.auth.cookie;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import us.gameandwatching.gwapi.core.AuthCookie;
import us.gameandwatching.gwapi.service.auth.SecretsConfig;

public class AuthCookieCrypterTest {

  private static final String TEST_KEY = RandomStringUtils.randomAlphanumeric(SecretsConfig.KEY_LENGTH);
  private static final int USER_ID = 1;
  private static final long AUTHENTICTED_AT = 100L;

  private SecretsConfig secretsConfig;
  private AuthCookieCrypter authCookieCrypter;

  @Before
  public void setup() {
    secretsConfig = mock(SecretsConfig.class);
    when(secretsConfig.getCookieKey()).thenReturn(new SecretKeySpec(TEST_KEY.getBytes(StandardCharsets.UTF_8),
        SecretsConfig.COOKIE_CRYPT_ALGORITHM));
    authCookieCrypter = new AuthCookieCrypter(secretsConfig);
  }

  @Test
  public void itEncryptsAndDecryptesAuthCookies() {
    AuthCookie originalAuthCookie = AuthCookie.builder()
        .setUserId(USER_ID)
        .setAuthenticatedAt(AUTHENTICTED_AT)
        .build();

    String encryptedCookie = authCookieCrypter.encrypt(originalAuthCookie);
    AuthCookie decryptedCookie = authCookieCrypter.decrypt(encryptedCookie);

    assertEquals(originalAuthCookie.getUserId(), decryptedCookie.getUserId());
    assertEquals(originalAuthCookie.getAuthenticatedAt(), decryptedCookie.getAuthenticatedAt());
  }
}
