package dev.ivchenko.lwjwae.codec.jackson;

import dev.ivchenko.lwjwae.testing.contract.BridgeContractTest;

/**
 * Runs the bridge against a real engine with this codec on the classpath, which is the only way to
 * prove that the page half and the Java half agree.
 */
class JacksonBridgeTest extends BridgeContractTest {
  @Override
  protected boolean isThisPlatform() {
    return true;
  }
}
