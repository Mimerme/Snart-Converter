# Snart-Converter
Image converter for Snart. Translates an image into touches that the Snart-Interpreter can read

#.SNART format
Below is the .SNART file format in plain text for each version

##GScale
.SNART format for greyscale only. Uses black clored ink only. Requires a binary image for the Snart Converter

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

##RGB
.SNART format for colored pictures. File size is significantly larger than GScale. 

```
VERSION=RGB
{
  1 :{[255,255,255], [123,452,131], [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] }
  2 :{[255,255,255], [123,452,131], [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] }
  3 :{[255,255,255], [123,452,131], [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] }
  4 :{[255,255,255], [123,452,131], [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] }
  5 :{[255,255,255], [123,452,131], [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] }
  6 :{[255,255,255], [123,452,131], [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] , [132, 341, 123] }
  etc...
}
```
