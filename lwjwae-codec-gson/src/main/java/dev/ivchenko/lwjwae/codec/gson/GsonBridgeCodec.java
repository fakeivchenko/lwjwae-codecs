package dev.ivchenko.lwjwae.codec.gson;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import dev.ivchenko.lwjwae.bridge.codec.BridgeCodec;
import lombok.RequiredArgsConstructor;

/**
 * A {@link BridgeCodec} over a {@link Gson} instance.
 *
 * <p>The codec is registered as a service, so the presence of the module on the classpath is
 * enough. An application with a configured {@code Gson} of its own passes it to the constructor and
 * sets the codec in {@code ApplicationParameters} instead. The page half is JSON.
 *
 * <p>Gson reads records since version 2.10 and needs nothing else for them. A class without a
 * no-argument constructor is instantiated through {@code Unsafe}, which is the Gson default and
 * works on a running JVM but not in a native image; register a {@code TypeAdapter} for such a type
 * or give it a constructor.
 */
@RequiredArgsConstructor
public class GsonBridgeCodec implements BridgeCodec {
  /**
   * The page half: {@code JSON.stringify} and {@code JSON.parse}, with {@code undefined} sent as
   * {@code null}, because {@code JSON.stringify(undefined)} is no string.
   */
  private static final String PAGE_SCRIPT =
      "{ encode: (value) => JSON.stringify(value === undefined ? null : value),"
          + " decode: (text) => JSON.parse(text) }";

  private final Gson gson;

  /** Creates a codec over a {@code Gson} with its defaults. */
  public GsonBridgeCodec() {
    this(new Gson());
  }

  @Override
  public String encode(Object value) {
    return this.gson.toJson(value);
  }

  @Override
  public <T> T decode(String value, Class<T> type) {
    try {
      return this.gson.fromJson(value, type);
    } catch (JsonParseException e) {
      throw new IllegalArgumentException("Not a " + type.getSimpleName() + ": " + value, e);
    }
  }

  @Override
  public String pageScript() {
    return PAGE_SCRIPT;
  }
}
