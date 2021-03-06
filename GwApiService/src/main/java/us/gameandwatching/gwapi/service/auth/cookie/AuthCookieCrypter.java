package us.gameandwatching.gwapi.service.auth.cookie;

import java.util.Base64;

import javax.crypto.Cipher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import us.gameandwatching.gwapi.core.AuthCookie;
import us.gameandwatching.gwapi.service.auth.SecretsConfig;

@Singleton
public class AuthCookieCrypter {

  private final SecretsConfig secretsConfig;
  private final ObjectMapper objectMapper;

  @Inject
  public AuthCookieCrypter(SecretsConfig secretsConfig) {
    this.secretsConfig = secretsConfig;
    this.objectMapper = new ObjectMapper();
  }

  public String encrypt(AuthCookie authCookie) {
    try {
      Cipher cipher = Cipher.getInstance(SecretsConfig.COOKIE_CRYPT_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, secretsConfig.getCookieKey());
      byte[] encryptedCookie = cipher.doFinal(objectMapper.writeValueAsBytes(authCookie));
      return Base64.getUrlEncoder().encodeToString(encryptedCookie);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  public AuthCookie decrypt(String authCookie) {
    try {
      Cipher cipher = Cipher.getInstance(SecretsConfig.COOKIE_CRYPT_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, secretsConfig.getCookieKey());
      byte[] decryptedCookie = cipher.doFinal(Base64.getUrlDecoder().decode(authCookie));
      return objectMapper.readValue(decryptedCookie, AuthCookie.class);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }
}
