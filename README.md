# Hangar Emulator
This is an experimental J2ME-emulator that let you play some old game fade for feature phones. It's still on a early stage of the development so it has compatibility problems. It's a perosonal project and was made only as experiment and for self-education, so it doesn't pretend to be better than other emulators and to have high level of compatibility.

![image](https://github.com/Wafer-EX/HangarEmulator/assets/76843479/551cb966-a0d7-4294-b67e-dfa95a0dbe87)


## Features
- Anti-Aliasing (makes vector graphics smooth)
- Custom soundbanks
- Custom resolution (you can even strength the game to window size!)
- Unlimited frame rate
- Experimental OpenGL support through LWJGL 3 (extremely raw)
- And more...

## Requirements
- Java 17
- ~512 MB free RAM
- OpenGL 3.3 (if you prefer using OpenGL backend instead of Swing)
## Build
As a result of the assembly, the `HangarEmulator-VERSION-all.jar` file will appear in the `build\libs` directory, which includes all the necessary libraries.

Windows:
```
gradlew.bat shadowJar build
```
Linux:
```
./gradlew shadowJar build
```
## Third-party libraries
| Name    |                                          |
|---------|------------------------------------------|
| FlatLaf | [Link (external resource)](https://www.formdev.com/flatlaf/) |
| ASM     | [Link (external resource)](https://asm.ow2.io/)              |
| LWJGL   | [Link (external resource)](https://www.lwjgl.org/)           |
| JOML    | [Link](https://github.com/JOML-CI/JOML)  |
## License
Copyright 2022-2024 Wafer EX. Hangar Emulator is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for the full license text.
