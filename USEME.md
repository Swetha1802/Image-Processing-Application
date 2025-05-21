# Overview

The image processing application supports a variety of image processing operations through script
commands.

The operations that are supported by the application:

* Brighten an image
* Darken an image
* Component Visualization of an image (red component , green component , blue component)
* Color representation - value , intensity and luma
* Flipping - Horizontal flipping , Vertical Flipping
* Filtering - Blur, Sharpen
* Color Transformation - Sepia and Greyscale.
* Split Channels
* Combine Channels
* Compression using haar wavelet transform.
* Histogram Image
* Color correction
* Level Adjustments
* Split view operation

### Commands and its Syntax

Load Command

* Description: Load an image from the specified path and refer it to henceforth in the program by
  the given image name.
* Syntax: load image-path image-name
* Example: load hello.jpeg hello
* Conditions: This command must be run before any other commands to ensure the image is available
  for processing.

Brighten Command

* Description : Brightens the image by a given constant.
* Syntax : brighten increment image_name dest_name.
* Example: brighten 50 hello hello_bright
* Conditions : The increment value should be positive for the image to brighten.

Darken Command

* Description : Darkens the image by a given constant.
* Syntax : darken decrement image_name dest_name.
* Example: darken 50 hello hello_dark
* Conditions : The decrement value should be negative for the image to darken.

Note : If the constant is positive it would perform the brighten operation
and if the constant is negative it would automatically perform the darken operation.

4. Component Visualization

* Description: Visualizes individual color components of an image.
* Syntax: <component>-component <image_name> <dest_image_name>
* Commands: red-component, green-component, blue-component
* Example: red-component hello hello-red , green-component hello hello-green blue-component hello
  hello-blue.
* Conditions: load command should precede component visualization commands.

5. Color Representation

Value component

* Description: calculates the value component of the image using the value formula.
* Syntax: value-component image-name dest-name
* Example: value-component hello hello-value
* Conditions: Must load the image before applying the command,make sure the formula is right

Intensity component

* Description: calculates the intensity component of the image using the intensity formula.
* Syntax: intensity-component image-name dest-name
* Example: intensity-component hello hello-intensity
* Conditions: Must load the image before applying the command, make sure the formula is right.

Luma component

* Description: calculates the luma component of the image using the luma formula.
* Syntax: luma-component image-name dest-name
* Example: luma-component hello hello-luma
* Conditions: Must load the image before applying the command,make sure the formula is right.


6. Flipping

Horizontal flipping

* Description : Flip an image horizontally to create a new image.
* Syntax : horizontal-flip image-name dest-image-name
* Example horizontal-flip hello hello-horizontal
* Conditions : Requires an image loaded first.

Vertical flipping

* Description : Flip an image vertically to create a new image.
* Syntax : vertical-flip image-name dest-image-name
* Example vertical-flip hello hello-vertical
* Conditions : Requires an image loaded first.

7. Filtering

Blur without split

* Description : Blurs the given image
* Syntax : blur image-name dest-image-name
* Example : blur hello hello-blur
* Conditions : Requires an image loaded first.

Blur with split

* Description : Blurs the given image and provides a split view
* Syntax : blur image-name dest-image-name p
* Example : blur hello hello-blur split 50
* Conditions : Requires an image loaded first , and a split percentage.
* Split percentage- it should not be below 0 or above 100.

Sharpen without split

* Description : sharpens the loaded image
* Syntax : sharpen image-name dest-image-name
* Example sharpen hello hello-sharpen
* Conditions : Requires an image loaded first.

Sharpen with split

* Description : sharpens the loaded image and provides a split view.
* Syntax : sharpen image-name dest-image-name p
* Example sharpen hello hello-sharpen split 60
* Conditions : Requires an image loaded first and a split percentage.
* Split percentage- it should not be below 0 or above 100.

8. Color Transformation

Sepia

* Description : Applies sepia to the image loaded
* Syntax : sepia image-name dest-image-name
* Example : sepia hello hello-sepia
* Conditions : Requires an image loaded first.

Sepia with split

* Description : Applies sepia the loaded image and provides a split view.
* Syntax : sepia image-name dest-image-name p
* Example sepia hello hello-sepia-split split 60
* Conditions : Requires an image loaded first and a split percentage.
* Split percentage- it should not be below 0 or above 100.

Greyscale

* Description : Applies greyscale to the image loaded
* Syntax : greyscale image-name dest-image-name
* Example : greyscale hello hello-greyscale
* Conditions : Requires an image loaded first.

Greyscale with split

* Description : Applies greyscale the loaded image and provides a split vie
* Syntax : greyscale image-name dest-image-name p
* Example greyscale hello hello-greyscale-split split 60
* Conditions : Requires an image loaded first and a split percentage to be given.
  Split percentage- it should not be below 0 or above 100.

9. Split RGB

* Description: Splits an image into red, green, and blue components.
* Syntax: rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue
* Example : rgb-split hello hello-red hello-green hello-blue
* Conditions: Requires loaded image before splitting.

10. Combine RGB

* Description: combines an image from the split of red, green, and blue components
* Syntax: rgb-combine image-name red-image green-image blue-image
* Example : rgb-combine hello-combine hello-red hello-green hello-blue
* conditions: Ensure split images are available before combining.

11. Histogram

* Description: Generates a histogram for the image.
* Syntax: histogram image-name dest-image-name
* Example : histogram hello hello_histogram
* Conditions: Load image before generating histogram.
  The size of this image should be 256x256.

12. Color Correction

* Description: Applies color correction to an image.
* Syntax: color-correct image-name dest-image-name
* Example : color-correct hello hello_cc
* Conditions:  Use load before applying color correction. Split percentage is optional.

Color correction with split

* Description: Applies color correction to an image with a split view.
* Syntax: color-correct image-name dest-image-name split p
* Example : color-correct hello hello_cc_split split 40
* Conditions:  Use load before applying color correction and Split percentage is required.
* Split percentage- it should not be below 0 or above 100.


13. Level Adjustment

* Description: Adjusts the levels of an image based on black, mid, and white level values.
* Syntax: levels-adjust b m w image-name dest-image-name
* Example: level-adjust 0 128 255 hello hello_la
* Conditions: upload the image and give the bmw values and
  make sure the bmw values are in ascending order,
  b , m and w should be less than or equal to 255 , it should not exceed ,
  else it will be invalid .

Level Adjustment with split

* Description: Adjusts the levels of an image based on black, mid, and white level values and
  provide a split view for the result.
* Syntax: levels-adjust b m w image-name dest-image-name split p
* Example: level-adjust 0 128 255 hello hello_la_split 70
* Conditions: upload the image and give the bmw values and
  make sure the bmw values are in ascending order,
  b , m and w should be less than or equal to 255 , it should not exceed ,
  else it will be invalid.
  Split percentage- it should not be below 0 or above 100.

14. Compression

* Description: create a compression version of an image.
* Syntax: compress percentage image-name dest-image-name
* Example: compress 80 hello hello-compress
* Conditions: Percentages between 0 and 100 are considered valid, and the image has to be loaded
  first.


15. Save

* Description: Save the image with the given name to the specified path which should include the
  name of the file.
* Syntax: save image-path image-name
* Example : save C:/Users/Afrah/Downloads/hello_histogram.png hello_histogram
* Conditions: The image-name specified in the command must correspond to an image that has
  already been loaded or created within the application.
  Attempting to save an image that does not exist in memory will result in an error.

How to run:

To run in IntelliJ:

- In IntelliJ, go to the drop down menu near Run.
- You will see, "Edit Configurations" option.
- Enter the following
- "-file the-path-of-the-script-file", that contains the commands you want to execute.
- Click run.

All the commands in the script file will now be executed.

Interactive Mode:

- In "Edit Configurations" enter the following
- "-text".
- Click run.

Now the user will be able to type and execute all the commands in an interactive way in the command
prompt.

Note: When executing the program through IntelliJ, make sure to give the command as
load res/imagename.extension dest-name

GUI:

- Leave blank on the program arguments field.
- Click on run.

To run using Command Prompt/ Terminal using jar file:

- In the command prompt, navigate to the folder containing jar file.
- To run script file, enter the below command
- java -jar program.jar -file path-of-the-script-file
- To activate interactive mode, enter the below command
- java -jar program.jar -text
- To run the jar file, enter the below command
- java -jar program.jar
- The GUI is will now pop up and the user can use the available options of operations and
  manipulate the images.

Note: When executing the program with jar file through the command prompt, make sure to give the
command as
load imagename.extension dest-name

Sample script commands to run and
verify the image operations is provided below:

#Loading

load res/source.png source
load res/mask_img.png mask

#Component Visualisation

red-component source rc-source
red-component source mask rc-mask

green-component source gc-source
green-component source mask gc-mask

blue-component source bc-source
blue-component source mask bc-mask

#Color Transformation

sepia source sepia-source
sepia source mask sepia-mask
sepia source sepia-source-split split 60
sepia source mask sepia-mask-split split 60

greyscale source greyscale-source
greyscale source mask greyscale-mask
greyscale source greyscale-source-split split 60
greyscale source mask greyscale-mask-split split 60

#Filtering

blur source blur-source
blur source mask blur-mask
blur source blur-source-split split 60
blur source mask blur-mask-split split 60

sharpen source sharpen-source
sharpen source mask sharpen-mask
sharpen source sharpen-source-split split 60
sharpen source mask sharpen-mask-split split 60

#Saving

save res/red-component-source.png rc-source
save res/red-component-mask.png rc-mask

save res/green-component-source.png rc-source
save res/green-component-mask.png rc-mask

save res/blue-component-source.png rc-source
save /res/blue-component-mask.png rc-mask

save res/sepia-source.png sepia-source
save res/sepia-mask.png sepia-mask
save res/sepia-source-split.png sepia-source-split
save res/sepia-mask-split.png sepia-mask-split

save res/greyscale-source.png greyscale-source
save res/greyscale-mask.png greyscale-mask
save res/greyscale-source-split.png greyscale-source-split
save res/greyscale-mask-split.png greyscale-mask-split

save res/blur-source.png blur-source
save res/blur-mask.png blur-mask
save res/blur-source-split.png blur-source-split
save res/blur-mask-split.png blur-mask-split

save res/sharpen-source.png sharpen-source
save res/sharpen-mask.png sharpen-mask
save res/sharpen-source-split.png sharpen-source-split
save res/sharpen-mask-split.png sharpen-mask-split

load tiger.jpeg tiger

#Component Visualisation
red-component tiger tiger-red
save res/tiger-red.jpg tiger-red

green-component tiger tiger-green
save res/tiger-green.jpg tiger-green

blue-component tiger tiger-blue
save res/tiger-blue.jpg tiger-blue

#rgb split
rgb-split tiger tiger-red tiger-green tiger-blue
save res/tiger-split-red.jpg tiger-red
save res/tiger-split-green.jpg tiger-green
save res/tiger-split-blue.jpg tiger-blue

#rgb-combine
rgb-combine tiger-combine tiger-red tiger-green tiger-blue
save res/tiger-combine.jpg tiger-combine

#Color Representation
value-component tiger tiger-value
save res/tiger-value.jpg tiger-value

intensity-component tiger tiger-intensity
save res/tiger-intensity.jpg tiger-intensity

luma-component tiger tiger-luma
save res/tiger-luma.jpg tiger-luma

#Flip
vertical-flip tiger tiger-vertical
save res/tiger-vertical.jpg tiger-vertical

horizontal-flip tiger tiger-horizontal
save res/tiger-horizontal.jpeg tiger-horizontal

horizontal-flip tiger-vertical tiger-vertical-horizontal
save res/tiger-vertical-horizontal.jpg tiger-vertical-horizontal

#Filtering
blur tiger tiger-blur
save res/tiger-blur.jpeg tiger-blur

blur tiger tiger_blur_sp split 60
save res/tiger_blur_sp.png tiger_blur_sp

sharpen tiger tiger-sharpen
save res/tiger-sharpen.jpeg tiger-sharpen

sharpen tiger tiger_sharp_sp split 80
save res/tiger_sharp_sp.png tiger_sharp_sp

#Color Transformation
sepia tiger tiger-sepia
save res/tiger-sepia.jpeg tiger-sepia

sepia tiger tiger_sepia_sp split 40
save res/tiger_sepia_sp.jpeg tiger_sepia_sp

greyscale tiger tiger-greyscale
save res/tiger-greyscale.jpeg tiger-greyscale

greyscale tiger tiger_grey_sp split 30
save res/tiger_grey_sp.jpeg tiger_grey_sp

#Histogram
histogram tiger tiger_histogram
save res/tiger_histogram.jpg tiger_histogram

color-correct tiger tiger_color_correct
save res/tiger_color_correct.png tiger_color_correct

histogram tiger_color_correct tiger_color_correct_histogram
save res/tiger_color_correct_histogram.jpg tiger_color_correct_histogram

color-correct tiger tiger_color_correct_sp split 75
save res/tiger_color_correct_sp.png tiger_color_correct_sp

level-adjust 0 100 200 tiger tiger_level
save res/tiger_level.jpeg tiger_level

histogram tiger_level tiger_level_histogram
save res/tiger_level_histogram.jpg tiger_level_histogram

level-adjust 0 150 228 tiger tiger_level2
save res/tiger_level2.jpeg tiger_level2

histogram tiger_level2 tiger_level2_histogram
save res/tiger_level2_histogram.jpg tiger_level2_histogram

level-adjust 0 100 200 tiger tiger_level_sp split 40
save res/tiger_level_sp.jpeg tiger_level_sp

compress 50 tiger tiger_compression_50
save res/tiger_compression_50.png tiger_compression_50

histogram tiger_compression_50 tiger_compression_50_histogram
save res/tiger_compression_50_histogram.jpg tiger_compression_50_histogram

compress 65 tiger tiger_compression_65
save res/tiger_compression_65.png tiger_compression_65

histogram tiger_compression_65 tiger_compression_65_histogram
save res/tiger_compression_65_histogram.jpg tiger_compression_65_histogram

#brighten and darken
brighten 70 tiger tiger-brighten
save res/tiger-brighten.jpeg tiger-brighten

darken 90 tiger tiger-darken
save res/tiger-darken.jpeg tiger-darken




















