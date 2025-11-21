#!/bin/bash
set -euo pipefail

# === CONFIGURATION ===
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUTPUT_DIR="$PROJECT_DIR/output"
MAIN_CLASS="io.github.atur256.graalvmvueinterop.Main"
MAIN_JAR="$PROJECT_DIR/target/graalvm-vue-interop-0.0.1.jar"
CUSTOM_OUTPUT="$PROJECT_DIR/html-demo"

# === ARGUMENTS OR CONFIG FILE ===
if [[ $# -ge 2 ]]; then
  echo "Using command-line arguments for configuration..."
  GRAALVM_BIN="$1"
  JAVA_HOME_OVERRIDE="$2"
else
  CONFIG_FILE="$PROJECT_DIR/build-script/build.config"
  if [[ ! -f "$CONFIG_FILE" ]]; then
    echo "Usage: $0 <GRAALVM_BIN> <JAVA_HOME_OVERRIDE>"
    echo "Or provide a build.config file with GRAALVM_BIN and JAVA_HOME_OVERRIDE."
    exit 1
  fi
  echo "Loading configuration from $CONFIG_FILE..."
  source "$CONFIG_FILE"
fi

# === FORCE CONFIGURED JDK ===
export JAVA_HOME="$JAVA_HOME_OVERRIDE"
export PATH="$JAVA_HOME/bin:$PATH"

## === STEP 1: Compile and package with Maven ===
echo "Building Maven project..."
cd "$PROJECT_DIR"
MAVEN_OPTS="--enable-native-access=ALL-UNNAMED" mvn clean package

## === STEP 2: Run web-image build ===
echo "Running web-image from $GRAALVM_BIN..."
"$GRAALVM_BIN/web-image" \
  -H:+UnlockExperimentalVMOptions \
  -H:ReflectionConfigurationFiles=/home/arthur/Oracle/Java/vue-webimage-interop/reflect.json \
  -o "$CUSTOM_OUTPUT/app" \
  -Ob -cp "$MAIN_JAR" "$MAIN_CLASS"

echo "Build complete. Output is ready in: $CUSTOM_OUTPUT"
