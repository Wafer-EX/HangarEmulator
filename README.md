# Hangar Emulator
This is an experimental J2ME-emulator that let you play some old game fade for feature phones. It's still on a early stage of the development so it has compatibility problems. It's a perosonal project and was made only as experiment and for self-education, so it doesn't pretend to be better than other emulators and to have high level of compatibility.

![image](https://github.com/Wafer-EX/HangarEmulator/assets/76843479/551cb966-a0d7-4294-b67e-dfa95a0dbe87)


## Features
- Anti-Aliasing (makes vector graphics smooth)
- Custom soundbanks
- Custom resolution (you can even strength the game to window size!)
- Unlimited frame rate
- Setting with command line arguments
- And more...

#### Command line arguments
| Argument                 | Value format                         | Allowed values                          |
|--------------------------|--------------------------------------|-----------------------------------------|
| `--screen-clearing`      |                                      |                                         |
| `--vector-anti-aliasing` |                                      |                                         |
| `--fps`                  | `Integer`                            | Any positive value, -1 to set unlimited |
| `--scaling-mode`         | `String`                             | `none`, `fit`, `change-resolution`      |
| `--resolution`           | `Integer (width)` `Integer (height)` | Any values is greater than 0            |
| `--interpolation`        |                                      |                                         |
| `--touchscreen-support`  |                                      |                                         |
| `--midlet`               | `String`                             | Legal path to the MIDlet                |

## Requirements
- Java 17
- ~512 MB free RAM
- OpenGL 3.3
## How to build
You need Java Development Kit 17 installed on your PC. Go to the root folder of the project (where is gradle script located) using terminal and run building by one of these command below. As a result of the assembly, the `HangarEmulator-VERSION-all.jar` file will appear in the `build\libs` directory, which includes all the necessary libraries, it's the builded Hangar Emulator.

Windows:
```
gradlew.bat shadowJar build
```
Linux:
```
./gradlew shadowJar build
```
## Third-party libraries
| Name    | Source                                                       |
|---------|--------------------------------------------------------------|
| FlatLaf | [Link (external resource)](https://www.formdev.com/flatlaf/) |
| ASM     | [Link (external resource)](https://asm.ow2.io/)              |
| LWJGL   | [Link (external resource)](https://www.lwjgl.org/)           |
| JOML    | [Link](https://github.com/JOML-CI/JOML)                      |
## License
Copyright 2022-2024 Wafer EX. Hangar Emulator is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for the full license text.
