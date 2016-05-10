package nk.ssb.smashdb.core.users;

import org.immutables.value.Value;

import nk.ssb.smashdb.core.SmashDBStyle;

@SmashDBStyle
@Value.Immutable
public interface UserIF extends UserFields {
  int getId();
}
