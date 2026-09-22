package dev.ivchenko.lwjwae.codec.jackson;

import dev.ivchenko.lwjwae.bridge.codec.BridgeCodec;
import dev.ivchenko.lwjwae.testing.contract.JsonBridgeCodecContractTest;

class JacksonBridgeCodecTest extends JsonBridgeCodecContractTest {
  @Override
  protected Class<? extends BridgeCodec> codecType() {
    return JacksonBridgeCodec.class;
  }

  @Override
  protected BridgeCodec codec() {
    return new JacksonBridgeCodec();
  }
}
