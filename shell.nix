# shell.nix
{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "java21-env";

  buildInputs = with pkgs; [
    jdk21
    maven
    gradle
    sqlite
  ];

  shellHook = ''
    export JAVA_HOME="${pkgs.jdk21}/lib/openjdk"
    export PATH="$JAVA_HOME/bin:$PATH"

    # Fix: Use relative or absolute path, but avoid absolute /Scripts
    # Assuming Scripts directory is in your project root
    if [ -d "./Scripts" ]; then
      chmod +x ./Scripts/*.sh
    fi

    # Fix: Remove spaces around the equals sign in alias
    alias expose="./Scripts/expose_jars.sh"

    echo "Java 21 environment loaded"
    java --version
  '';
}