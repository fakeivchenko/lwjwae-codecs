package dev.ivchenko.lwjwae.codec.gson;

import dev.ivchenko.lwjwae.bridge.codec.BridgeCodec;
import dev.ivchenko.lwjwae.testing.contract.JsonBridgeCodecContractTest;

class GsonBridgeCodecTest extends JsonBridgeCodecContractTest {
  @Override
  protected Class<? extends BridgeCodec> codecType() {
    return GsonBridgeCodec.class;
  }

  @Override
  protected BridgeCodec codec() {
    return new GsonBridgeCodec();
  }
}
