/**
 * JSON for the typed bridge of lwjwae through Gson.
 *
 * <p>The codec reaches lwjwae as a service, so an application on the module path finds it without
 * naming it, as on the class path. The types that the bridge carries must be readable by Gson: the
 * package of a record or a class that goes through it opens to {@code com.google.gson}.
 */
module dev.ivchenko.lwjwae.codec.gson {
  requires dev.ivchenko.lwjwae;
  requires com.google.gson;
  requires static lombok;

  exports dev.ivchenko.lwjwae.codec.gson;

  provides dev.ivchenko.lwjwae.bridge.codec.BridgeCodec with
      dev.ivchenko.lwjwae.codec.gson.GsonBridgeCodec;
}
