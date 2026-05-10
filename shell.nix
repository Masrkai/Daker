# This shell is intended to be used on Nix/NixOS systems
# while it's mostly unnecessary if you enabled java on system-wide
# it should be a reference point so no one gets confused

# java eco system is VERY complicated many SDKs and builds
# the chosen build system is gradle and we choose kotlin
# instead of groovy for this project while we are forced
# by the doctors to use java i totally understand kotlin
# was way to go here if i really want that eco-system

# I am also well aware that my current team won't ever
# open let alone use this file however this is a
# reference point for my own more than anyone

# this is not written by Ai, this is written by and for Masrkai
# the founder and main contributor of this project

{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "java21-env";

  buildInputs = with pkgs; [
    jdk21
    gradle

    gource
    ffmpeg-full
  ];

  shellHook = ''
    export JAVA_HOME="${pkgs.jdk21}/lib/openjdk"
    export PATH="$JAVA_HOME/bin:$PATH"

    if [ -d "./Scripts" ]; then
      chmod +x ./Scripts/*.sh
    fi

    alias expose="./Scripts/expose_jars.sh"

    echo "Shell info:"
    java --version
    echo $JAVA_HOME
  '';
}