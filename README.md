# Hangar Emulator
This is a simple J2ME emulator which will let you play some old games for feature phones. Hangar Emulator supports main mostly used libraries but at this moment it is at a very early stage of development.

![image](https://user-images.githubusercontent.com/76843479/175813227-4ab735a1-c493-4cb5-bd08-c7b5df19d6e6.png)
## Requirements
- Java Development Kit 17
- ~256 MB free RAM
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
Copyright 2022 Kirill Lomakin. Hangar Emulator is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for the full license text.
