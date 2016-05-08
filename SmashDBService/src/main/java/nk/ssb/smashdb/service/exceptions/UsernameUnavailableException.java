package nk.ssb.smashdb.service.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nk.ssb.smashdb.core.ExceptionEntity;

public class UsernameUnavailableException extends WebApplicationException {

  private final static String CODE = "USERNAME_UNAVAILABLE";

  public UsernameUnavailableException() {
    super(Response.status(Status.BAD_REQUEST)
        .entity(ExceptionEntity.builder()
            .setCode(CODE)
            .build())
        .build());
  }
}
