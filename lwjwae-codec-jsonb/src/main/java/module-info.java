/**
 * JSON for the typed bridge of lwjwae through JSON-B.
 *
 * <p>The codec reaches lwjwae as a service, so an application on the module path finds it without
 * naming it, as on the class path. The types that the bridge carries must be readable by JSON-B:
 * the package of a record or a class that goes through it opens to {@code jakarta.json.bind}.
 */
module dev.ivchenko.lwjwae.codec.jsonb {
  requires dev.ivchenko.lwjwae;
  requires jakarta.json.bind;
  requires static lombok;

  exports dev.ivchenko.lwjwae.codec.jsonb;

  provides dev.ivchenko.lwjwae.bridge.codec.BridgeCodec with
      dev.ivchenko.lwjwae.codec.jsonb.JsonbBridgeCodec;
}
