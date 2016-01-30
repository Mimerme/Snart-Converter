# Snart-Converter
Image converter for Snart. Translates an image a JSON style file touches that the Snart-Interpreter can read.

#About
Originally designed for Snapchat, the Snart-Converter draws a picture from an inputed image to create a Snapsterpiece. I plan to utilize the Snart Converter in other proejcts as well. Perhaps repackage the algoirthms and develop a consumer/user friendly version of the Snart-Converter for anyone to draw images onto Snapchat. Later on probably implement another style of "automated drawing" in games such as Dota 2, just for fun.

#Running
- Download/ clone the repository
- Open the eclipse project / Run the release.bat if there is one
- make sure adb runs on your computer. There is an adb.exe with the .dlls for Windows users. Mac users can install adb directly from the Android SDK Tools
- Answer the questions the program prompts for

#.SNART format
Below is the .SNART file format in plain text for each version

##GScale
.SNART format for greyscale only. Uses black clored ink only. Can convert colored images to greyscale to binary, but results may vary. Done on a pixel by pixel bases. File compression planned to be implemented.

```
VERSION=GSCALE
{
  1: [0,0,1,1,0,0,0,1,0,1,0,1,0,0,0]
  2: [0,0,1,1,0,0,0,1,0,1,0,1,0,0,0]
  3: [0,0,1,1,0,0,0,1,0,1,0,1,0,0,0]
  4: [0,0,1,1,0,0,0,1,0,1,0,1,0,0,0]
  etc...
}
```

##GScale Android
File extension .SNAP. List of commands to run in the ADB Shell. This runs a series of swipes on your android device. Perfect for drawing images in Snapchat. Runs in the Android Debug Bridge with the following command.
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

##GScale Edge
File extension .SNAP. List of commands to run in the ADB Shell. This runs a series of swipes on your android device. Perfect for drawing images in Snapchat. Similar to GScale Android, however uses an edge detection algorithm instead. Runs in the Android Debug Bridge with the following command.
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

##RGB
_Decapricated. No need for file type_
