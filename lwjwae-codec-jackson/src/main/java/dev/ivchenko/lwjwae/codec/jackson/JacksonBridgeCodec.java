package dev.ivchenko.lwjwae.codec.jackson;

import dev.ivchenko.lwjwae.bridge.codec.BridgeCodec;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * A {@link BridgeCodec} over a Jackson 3 {@link ObjectMapper}.
 *
 * <p>The codec is registered as a service, so the presence of the module on the classpath is
 * enough. An application with a configured mapper of its own passes the mapper to the constructor
 * and sets the codec in {@code ApplicationParameters} instead. The page half is JSON.
 *
 * <p>Jackson 3 writes {@code java.time} values as ISO-8601 text and reads records without any
 * module, so the default mapper covers what a page usually exchanges.
 */
@RequiredArgsConstructor
public class JacksonBridgeCodec implements BridgeCodec {
  /**
   * The page half: {@code JSON.stringify} and {@code JSON.parse}, with {@code undefined} sent as
   * {@code null}, because {@code JSON.stringify(undefined)} is no string.
   */
  private static final String PAGE_SCRIPT =
      "{ encode: (value) => JSON.stringify(value === undefined ? null : value),"
          + " decode: (text) => JSON.parse(text) }";

  private final ObjectMapper mapper;

  /** Creates a codec over a mapper with the Jackson defaults. */
  public JacksonBridgeCodec() {
    this(JsonMapper.builder().build());
  }

  @Override
  public String encode(Object value) {
    try {
      return this.mapper.writeValueAsString(value);
    } catch (JacksonException e) {
      throw new IllegalArgumentException("Not serializable as JSON: " + value, e);
    }
  }

  @Override
  public <T> T decode(String value, Class<T> type) {
    try {
      return this.mapper.readValue(value, type);
    } catch (JacksonException e) {
      throw new IllegalArgumentException("Not a " + type.getSimpleName() + ": " + value, e);
    }
  }

  @Override
  public String pageScript() {
    return PAGE_SCRIPT;
  }
}
