# shell.nix
{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "java21-env";

  buildInputs = with pkgs; [
    jdk21
    maven
    gradle
  ];

  shellHook = ''
    export JAVA_HOME="${pkgs.jdk21}/lib/openjdk"
    export PATH="$JAVA_HOME/bin:$PATH"

    echo "Java 21 environment loaded"
    java --version
  '';
}