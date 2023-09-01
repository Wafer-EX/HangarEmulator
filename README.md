# Hangar Emulator
This is a very raw and simple J2ME-emulator that let you play some old games made for feature phones. It's still a very early stage of development so it has many compatibility problems. It was made only for experiments and self-education so it doesn't pretend to be a cool emulator with very high level of compatibility.

![image](https://user-images.githubusercontent.com/76843479/175813227-4ab735a1-c493-4cb5-bd08-c7b5df19d6e6.png)



## Features
- Anti-aliasing (makes vector graphics smooth)
- Custom soundbanks
- Custom resolution (include screen filling)
- Canvas scaling when stretching the window
- Unlimited frame rate
- Experimental OpenGL support through LWJGL 3 (extremely raw)

## Requirements
- Java Development Kit 17
- ~512 MB free RAM
- OpenGL 4.6 (if you prefer use OpenGL instead of Swing)
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
## License
Copyright 2022-2023 Kirill Lomakin. Hangar Emulator is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for the full license text.
