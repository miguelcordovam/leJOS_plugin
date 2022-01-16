# lejOS plugin for IntelliJ IDEA

**Author:** Miguel Cordova, miguelcordovadev@gmail.com

## Setup instructions

1. Install plugin and restart IDE.
2. Create a lejOS project.
3. Go to File > Settings > Tools > LejOS Plugin
4. Set EV3_HOME and brick's ip (Default is 10.0.1.1).
5. Write code to your EV3
6. Create a run configuration
7. Enjoy your robot!

## Kotlin Support
1. Copy the following jars to `/home/root/lejos/lib/` using SCP:
- `kotlin-reflect.jar`
- `kotlin-stdlib.jar`
- `kotlin-stdlib-jdk7.jar`
- `kotlin-stdlib-jdk8.jar`
- `kotlin-test.jar`

Rename files if needed! Example: (`kotlin-reflect-1.6.10.jar` -> `kotlin-reflect.jar`)

2. Enable Kotlin Support in Run Configuration

## Projects with EV3 Lego Mindstorms

 https://www.youtube.com/channel/UCYvHuR6MoN7LXsLs2T0-siw
