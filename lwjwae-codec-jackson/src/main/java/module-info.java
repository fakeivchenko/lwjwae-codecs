/**
 * JSON for the typed bridge of lwjwae through Jackson.
 *
 * <p>The codec reaches lwjwae as a service, so an application on the module path finds it without
 * naming it, as on the class path. The types that the bridge carries must be readable by Jackson:
 * the package of a record or a class that goes through it opens to {@code tools.jackson.databind}.
 */
module dev.ivchenko.lwjwae.codec.jackson {
  requires dev.ivchenko.lwjwae;
  requires tools.jackson.databind;
  requires static lombok;

  exports dev.ivchenko.lwjwae.codec.jackson;

  provides dev.ivchenko.lwjwae.bridge.codec.BridgeCodec with
      dev.ivchenko.lwjwae.codec.jackson.JacksonBridgeCodec;
}
