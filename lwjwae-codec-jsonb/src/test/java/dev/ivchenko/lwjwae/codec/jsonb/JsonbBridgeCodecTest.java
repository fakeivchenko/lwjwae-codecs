package dev.ivchenko.lwjwae.codec.jsonb;

import dev.ivchenko.lwjwae.bridge.codec.BridgeCodec;
import dev.ivchenko.lwjwae.testing.contract.JsonBridgeCodecContractTest;

class JsonbBridgeCodecTest extends JsonBridgeCodecContractTest {
  @Override
  protected Class<? extends BridgeCodec> codecType() {
    return JsonbBridgeCodec.class;
  }

  @Override
  protected BridgeCodec codec() {
    return new JsonbBridgeCodec();
  }
}
