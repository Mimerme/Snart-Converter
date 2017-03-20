# Snart-Converter

# About
Originally designed for Snapchat, the Snart-Converter draws a picture from an inputed image to create a Snapsterpiece. I plan to utilize the Snart Converter in other proejcts as well. Perhaps repackage the algoirthms and develop a user friendly version of the Snart-Converter for anyone to draw images onto Snapchat. Later on probably implement another style of "automated drawing" in games such as Dota 2, just for fun.

# Modifying and Running
- Download/ clone the repository
- Open the eclipse project / Run the release.bat if there is one
- make sure adb runs on your computer. There is an adb.exe with the .dlls for Windows users. Mac users can install adb directly from the Android SDK Tools
- Answer the questions the program prompts for

# Running
- download the relases from the /release folder (Run the GUI for a UI or any other jar for a command line prompt)

# Output Files

## GScale Android
Short for "Greyscale Android", the program outputs this file when no thinning has been applied. Run the file as shown bellow through ADB. The file itself is just a list of ADB commands simulating the touches.
```
adb shell < [filename].SNAP
```

Raw text format is as follows.

```
input swipe [xStart] [yStart] [xEnd] [yEnd]
input swipe [xStart] [yStart] [xEnd] [yEnd]
input swipe [xStart] [yStart] [xEnd] [yEnd]
input swipe [xStart] [yStart] [xEnd] [yEnd]
input swipe [xStart] [yStart] [xEnd] [yEnd]
input swipe [xStart] [yStart] [xEnd] [yEnd]
etc...
```

## GScale Edge
Similar to GScale Android, however runs an implementation of the Zhuen-Sheng thinning algorithm before processing the image. This is recomended if the image has minor details that need to be present. This implementation is needed as Snapchat has a fixed sized of the minimum size a drawn dot can be.

