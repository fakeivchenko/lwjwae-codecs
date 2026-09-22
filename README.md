<h1 align="center">lwjwae-codecs</h1>

<p align="center">
  <b>JSON codecs for the typed bridge of lwjwae</b><br/>
</p>

<p align="center">
  <a href="https://github.com/fakeivchenko/lwjwae-codecs/actions/workflows/tests.yml"><img alt="Tests" src="https://github.com/fakeivchenko/lwjwae-codecs/actions/workflows/tests.yml/badge.svg?branch=dev"></a>
  <a href="https://github.com/fakeivchenko/lwjwae-codecs/actions/workflows/release.yml"><img alt="Release" src="https://github.com/fakeivchenko/lwjwae-codecs/actions/workflows/release.yml/badge.svg?branch=release"></a>
  <a href="https://repo.ivchenko.dev/#/releases/dev/ivchenko/lwjwae/codec/lwjwae-codec-jackson"><img alt="Latest release" src="https://repo.ivchenko.dev/api/badge/latest/releases/dev/ivchenko/lwjwae/codec/lwjwae-codec-jackson?color=40c14a&name=release"></a>
  <img alt="Java 25" src="https://img.shields.io/badge/Java-25-blue">
  <img alt="GraalVM native-image ready" src="https://img.shields.io/badge/GraalVM-native--image%20ready-2f6fd6">
  <a href="LICENSE"><img alt="Apache 2.0" src="https://img.shields.io/badge/license-Apache%202.0-green"></a>
</p>

Codecs for the typed bridge of [lwjwae](https://github.com/fakeivchenko/lwjwae): `bind(name, Class, handler)` and
`emit(name, Object)` need a `BridgeCodec` to turn values into text and back. The core carries no
serialization library, so you add one of these modules to the runtime classpath, and the codec is
found through `ServiceLoader`.

| Module                 | Library              | Notes                                                                                                                          |
|------------------------|----------------------|--------------------------------------------------------------------------------------------------------------------------------|
| `lwjwae-codec-jackson` | Jackson Databind     | Pass your own `ObjectMapper` to the constructor to reuse its configuration.                                                    |
| `lwjwae-codec-gson`    | Gson                 | Reads records since Gson 2.10. Types without a no-argument constructor go through `Unsafe`, which a native image doesn't have. |
| `lwjwae-codec-jsonb`   | Jakarta JSON Binding | The API only. Add an implementation such as Eclipse Yasson, or rely on the one your Jakarta stack already has.                 |

## Installation

The modules are published to `https://repo.ivchenko.dev/releases` under the group
`dev.ivchenko.lwjwae.codec`, versioned separately from the library:

```kotlin
repositories {
    maven("https://repo.ivchenko.dev/releases")
}

dependencies {
    implementation("dev.ivchenko.lwjwae:lwjwae-core:VERSION")
    runtimeOnly("dev.ivchenko.lwjwae.codec:lwjwae-codec-jackson:CODECS_VERSION")
}
```

`runtimeOnly` is enough when the codec is discovered through `ServiceLoader`; use
`implementation` to construct one yourself, for example with your own `ObjectMapper`. Each module
brings its JSON library as an `api` dependency, except `lwjwae-codec-jsonb`, which brings the
Jakarta JSON Binding API and leaves the implementation to you:

```kotlin
dependencies {
    runtimeOnly("dev.ivchenko.lwjwae.codec:lwjwae-codec-jsonb:CODECS_VERSION")
    runtimeOnly("org.eclipse:yasson:3.0.4")
}
```

Put one module on the classpath. With more than one, `ServiceLoader` returns the first it finds,
which is classpath order, so set the codec explicitly instead:

```java
ApplicationParameters.builder()
    .codec(new JacksonBridgeCodec(mapper))
    .build();
```

Every codec here speaks JSON, and each states so in its `pageScript()`: `JSON.stringify` and
`JSON.parse` in the page. The core provides no default; a codec for another format returns the
matching JavaScript instead.

## Tests

Each module runs two suites from the test fixtures of `lwjwae-core`:

- `JsonBridgeCodecContractTest`, headless: discovery, round trips with nesting and escapes, the exact
  JSON of simple values, `null`, bad input, and the page half.
- `BridgeContractTest`, with a real window: the codec on the classpath and the backend of this
  machine, so the page half and the Java half are proved against a real engine. Skipped without a
  display.

```bash
./gradlew check          # Both; the display tests skip on a headless machine
./gradlew test           # Headless only
./gradlew displayTest    # Windows only
```

The codecs build against the released library from `https://repo.ivchenko.dev/releases`. The
version is `lwjwaeVersion` in [`gradle.properties`](gradle.properties); bump it when a new `lwjwae`
comes out. To try a change in the library before it's released, publish it to Maven
local from its checkout (`./gradlew publishToMavenLocal -PprojectVersion=X.Y.Z`), set `lwjwaeVersion` to
that version, and add `mavenLocal()` to the repositories for the time being.

## Releases

Every push to `release` runs the tests, computes a version from the commit messages (Conventional
Commits), publishes the three modules to the Maven repository, and creates a tag and a GitHub
release, the same way the library does.
