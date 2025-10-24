# ⚠️ Development-Only Build Script

**Warning:** This script is intended for development purposes only.  
It is tailored to a specific local environment and may not work on other systems without modification.

---

# vue-webimage-interop Build Script

This directory contains a build script for compiling the `vue-webimage-interop` Java project and generating a WebAssembly image using GraalVM's `mx web-image`.

---

## Files

### `build.sh`

Automates the full build process:

1. **Stores the original Java environment**  
   Captures `JAVA_HOME` and `PATH` so they can be restored before running `mx`.

2. **Forces JDK 25 for Maven**  
   Temporarily sets `JAVA_HOME` and `PATH` to use OpenJDK 25 for compiling the project.

3. **Loads MX configuration**  
   Sources `mx.config` to retrieve the GraalVM build directory (`MX_WORKDIR`), which is where the `mx web-image` command is executed.

4. **Auto-detects project root**  
   Dynamically sets `PROJECT_DIR` based on the location of the script.

5. **Builds the Maven project**  
   Runs `mvn clean package` to produce a shaded JAR with all dependencies.

6. **Restores original Java environment**  
   Ensures `mx` runs with its intended Java setup.

7. **Runs `mx web-image`**  
   Executes the GraalVM WebAssembly build inside the configured MX work directory.

8. **Moves the output file**  
   Transfers the generated `.js` file to `build-output/` for easy access.

---

### `mx.config`

Stores the path to the directory where `mx` is executed:

```bash
MX_WORKDIR="/path/to/webimage/dir"
