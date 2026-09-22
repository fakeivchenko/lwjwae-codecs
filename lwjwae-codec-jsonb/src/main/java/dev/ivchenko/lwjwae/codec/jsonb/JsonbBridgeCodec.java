package dev.ivchenko.lwjwae.codec.jsonb;

import dev.ivchenko.lwjwae.bridge.codec.BridgeCodec;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import lombok.RequiredArgsConstructor;

/**
 * A {@link BridgeCodec} over Jakarta JSON Binding.
 *
 * <p>The codec is registered as a service, so the presence of the module on the classpath is
 * enough, together with an implementation such as Eclipse Yasson. This module deliberately doesn't
 * choose the implementation: an application on Quarkus or another Jakarta stack already has one.
 * The page half is JSON.
 */
@RequiredArgsConstructor
public class JsonbBridgeCodec implements BridgeCodec {
  /**
   * The page half: {@code JSON.stringify} and {@code JSON.parse}, with {@code undefined} sent as
   * {@code null}, because {@code JSON.stringify(undefined)} is no string.
   */
  private static final String PAGE_SCRIPT =
      "{ encode: (value) => JSON.stringify(value === undefined ? null : value),"
          + " decode: (text) => JSON.parse(text) }";

  private final Jsonb jsonb;

  /** Creates a codec over the {@code Jsonb} that {@link JsonbBuilder#create()} finds. */
  public JsonbBridgeCodec() {
    this(JsonbBuilder.create());
  }

  @Override
  public String encode(Object value) {
    try {
      return this.jsonb.toJson(value);
    } catch (JsonbException e) {
      throw new IllegalArgumentException("Not serializable as JSON: " + value, e);
    }
  }

  @Override
  public <T> T decode(String value, Class<T> type) {
    try {
      return this.jsonb.fromJson(value, type);
    } catch (JsonbException e) {
      throw new IllegalArgumentException("Not a " + type.getSimpleName() + ": " + value, e);
    }
  }

  @Override
  public String pageScript() {
    return PAGE_SCRIPT;
  }
}
