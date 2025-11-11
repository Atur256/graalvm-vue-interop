#!/bin/bash

# === STORE ORIGINAL JAVA ENV ===
ORIGINAL_JAVA_HOME="$JAVA_HOME"
ORIGINAL_PATH="$PATH"

# === FORCE JDK 25 ===
export JAVA_HOME=/usr/lib/jvm/java-25-openjdk
export PATH=$JAVA_HOME/bin:$PATH

# === LOAD MX CONFIG ===
source ./mx.config
MX_DIR="$MX_WORKDIR"

# === CONFIGURATION ===
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JAR_PATH="$PROJECT_DIR/target/graalvm-vue-interop-1.0.0.jar"
MAIN_CLASS="io.github.atur256.graalvmvueinterop.Main"
MX_OUTPUT="io.github.atur256.graalvmvueinterop.main.js"
CUSTOM_OUTPUT="$PROJECT_DIR/html-demo"

## === STEP 1: Build with Maven ===
echo "Building Maven project..."
cd "$PROJECT_DIR" || exit 1
mvn clean package

# === STEP 2: Restore original Java before mx ===
export JAVA_HOME="$ORIGINAL_JAVA_HOME"
export PATH="$ORIGINAL_PATH"

# === STEP 3: Run mx web-image in Graal workdir ===
echo "Running mx web-image in $MX_DIR..."
cd "$MX_DIR" || exit 1
mx web-image -Ob -H:-ClosureCompiler -cp "$JAR_PATH" "$MAIN_CLASS"

# === STEP 4: Move output to build-output ===
echo "Copying output to $CUSTOM_OUTPUT..."
mkdir -p "$CUSTOM_OUTPUT"
mv "$MX_OUTPUT" "$CUSTOM_OUTPUT/app.js"

echo "Build complete. Output is ready in: $CUSTOM_OUTPUT"

