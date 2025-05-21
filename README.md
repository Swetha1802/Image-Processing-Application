Image Processing Application

Table of contents:
Overview
1.Controller Package

* Main class
* ScriptParser class
* ImageLoader class
* GUI Controller class

2.Model Package

* Interfaces
* Classes

3.View Package

* ConsoleImageLoaderView
* ImageLoaderView
* ImageProcessingGUI

**Overview:**

This image processing application is developed using the MVC (Model-View-Controller) architecture.

The major image operations that are supported :

* Brighten and darkening of an image
* Component Visualization of an image
* Color representation ( value , intensity , luma)
* Sepia and Greyscale that comes under "Color transformation".
* Split and combine the channels
* Compression using haar wavelet transform.
* Creating a histogram for an image
* Color correction of an image
* Level Adjustments on an image
* Split view operation for an image.
* Downsizing of an image
* Partial image manipulation

The application allows users to load the images, perform the desired operation
and save the images in their system.

### Design Changes

Assignment 5 changes:

- The image loader class has been moved to the controller package as the image loading and
  saving operations should be done by the controller only and it is not appropriate
  to include it in the model.

- Model package
  Model package is now organised into ImageRepresentation package and Transformation package.

- The image representation classes namely the image class , image operations class and
  the pixel class is organised under a package name ImageRepresentation as all the three classses
  represent the properties of the image and its pixel.

- "Transformation" package is created such that all the classes that perform image
  manipulation and its respective interfaces are organised.
- The "Transformation" package has two inner packages named class and interfaces
- The class package has all the classes namely the :
  BrightenDarken
  ColorCorrection
  ColorRepresentation
  ColorTransformation
  CombineChannels
  ComponentVisualization
  Filtering
  Flipping
  Histogram
  ImageCompression
  LevelAdjust
  Split


- The "Interfaces" package contains all the interfaces that is being implemented by the
  respective classes mentioned above.

Assignment 6 changes:

- Introduced three types of command line arguments to execute the commands in the main class.

New classes that are added:
Model

- SplitView
- ImageDownsizing

Controller

- GUI controller

View

- GUI

-The ImageDownsizing class reduces the size of an image by adjusting its pixels using a
method called bilinear interpolation. It creates a smaller version of the image and saves it.

- The SplitView class is now created to handle the split view operation logic that is common
  to the classes namely Filtering, ColorTransformation, ColorCorrection and levelAdjust.
  instead of having redundant code.

New operations supported by the application:

- Image Downscaling
- Partial image manipulation

Classes that support Partial image manipulation:

- ComponentVisualization
- ColorTransformation
- Filtering

Component Visualization:

- The class accepts an optional maskImageName to apply transformations only to specific areas of the
  image.
- In applyComponentVisualization, the mask is checked, and transformations are applied only to
  pixels where the mask is black (RGB = 0).
- Pixels in the masked region retain their original values without any transformation.

Color Transformation:

- The class has a maskImageName parameter that allows specifying a mask image to apply partial
  transformations.
- For each pixel, if the corresponding mask pixel is black (0,0,0), the color transformation is
  applied;
  otherwise, the original pixel is retained.

Filtering

- The applyFilter method accepts a maskImageName parameter, allowing the use of a mask image.
- If a mask is provided, the filter is only applied to pixels where the mask has black (0, 0, 0)
  values.
  Otherwise, the original pixel remains unchanged.
- When the mask condition is not met, the pixel in the result image remains the same as in the
  original image, effectively not applying the filter to that area.

Script Parser

- In Script parser class , the switch case is modified in order to support mask operation.

View Package

- A class named "GUI" is added in the view package.
- This class creates an interactive GUI that allows the user to load, have options to
  choose various image operations, preview it then apply the operation to the whole image if the
  user wants and also to save the image at the desired destination of the user.

**Description**

1. Controller Package

1.1 Main.java

Purpose: Entry point for the application.
Responsibilities: Manages the overall flow of the application.
Executes image processing based on user input or script commands given.
Executes commands if chosen to perform it in an interactive mode.
Also has an option to open a GUI and allow the user to manipulate images in the UI.

1.2 ScriptParser.java
Purpose: Parses script files for processing and then performing the respective image operation
It also takes in commands in an interactive mode, validates it and performs the respective operation
mentioned in the command.
Responsibilities:
Reads and validates commands from script files, then executes commands sequentially.
It also takes in commands in the interactive mode and performs the respective valid operation.

1.3 ImageLoader.java
Purpose: To Load and save image.
Responsibilities: Implements the loading and saving operation of an image for
different image types such as ppm,png, jpeg.

1.4 GUIController.java

- Controls user interactions with the image processing application through a graphical interface
- Manages image loading, saving, and preview functionality with split-view comparison
- Handles image transformations that supports all operations.
- Updates the GUI display and histogram after each operation, showing real-time changes to the image

2.Model Package

ImageRepresentation Package

Image.java

- The Image class represents an image with specified width and height, storing each pixel's RGB
  values.
- By default, all pixels are initialized to black (0,0,0) but can be individually accessed and
  modified.
- It provides methods to retrieve and modify pixel colors, as well as to get the image’s dimensions.

ImageOperations.java

- The ImageOperations class manages image storage and retrieval using a
  HashMap where each image is identified by a unique name.
- The getImage method retrieves an image by name, throwing an exception if the image does not exist.
- The saveImage method stores an image under a specified name, ensuring the
  image is not null before saving.

Pixel.java

- The Pixel class represents a pixel with red, green, and blue color components,
  each of which is constrained to values between 0 and 255.
- The constructor and individual setter methods (setRed, setGreen, and setBlue)
  use a private clamp method to ensure that color values remain within this range.
- Getters for each color component (getRed, getGreen, and getBlue) allow for retrieving the pixel’s
  color values.

Transformation Package

Class Package

BrightenDarken.java
The adjustbrightness method takes in the image name , destination name and
the constant as the parameters adjust brightness accordingly depending on
whether the constant is positive or negative.

* If the constant = positive then brighten is perfomed
* If constant = negative , darken is performed.

ColorCorrection.java

- The colorcorrect method in the class performs color correction on the image,
- it provides an option to do a split a view, else it does the operation on the whole image.

ColorRepresentation.java

- This class has the color representation method that
  calculates the value , intensity and luma operation on the image using the mathematical formulae.

ColorTransformation.java

- This class has the color transformation method that applies the
  matrix kernel and performs the required color transformation on the image .
  It can either be sepia or grey scale.
- Supports partial masking to apply transformations to specific areas of an image.
- Includes a split-view effect to apply transformations on a portion of the image based on a
  specified position.

CombineChannels.java

- This class has a combine chanel method that combines the individual
  channels of red , green and blue components into one complete image.

ComponentVisualization.java

- This class has the Componentvisualization method that visualises each color channel .
  For red chanel, values are assigned as (r,r,r)
  For green channel values are assigned as (g,g,g).
  For blue channel values are assigned as (b,b,b).
- Supports partial masking to apply component visualizations to specific areas of an image.

Filtering.java

- The apply filter class implements kernel matrices to apply effects like blur and sharpen.
- It also includes a split view effect to preview the filtering operation.
- It supports partial masking to apply blur and sharpen to specific areas of an image.

Flipping.java

- The flipping method implements the flipping operation
  on the image and performs either vertical or horizontal flip.

Histogram.java

- The Histogramclass generates a histogram image from an input image, showing color
  distribution for red, green, and blue channels.
- It calculates color histograms and uses these values to draw curves representing
  each color on a 256x256 image.
- The class includes methods for setting a white background, adding grid lines,
- and drawing histogram outlines in each color.

LevelAdjustment.java

- The levelAdjustment class provides functionality for adjusting an image's
  levels based on black, mid, and white values to enhance its tonal range.
- It calculates quadratic coefficients and applies a transformation based on these
  values, creating an adjusted image with a split line if specified.
- The class validates input values, applies adjustments, saves the adjusted image,
  and generates a histogram for the result.

Compression.java

-The compression class uses the haar wavelet transform to compress the image given,
with the specified percentage of compression.

Split.java

- The Split method in this class splits or extracts the red, green and blue
  component from the image.

ImageDownSizing.java

- The ImageDownsizing class reduces the size of an image by resizing it to a target width and
  height.
- It uses bilinear interpolation to calculate the color of each pixel in the new image based on
  nearby pixels from the original image.
- The downsized image is saved with the specified name after the transformation.

SplitView

- Handles the common splitview logic for an image for all the classes that has a split view option
  namely Filtering , ColorTransformation, ColorCorrection and levelAdjust.

Interfaces Package

BrightenDarken - defines the brigthen method and the darken method
used in the brightendarken class.

ColorRepresentation - defines the value, intensity ,
luma component which is used in the ColorRepresentation class.

ColorTransformation - Defines the methods that perform the sepia and greyscale operation.

Combine Channels - Defines the method that would combine the individual channels of
red , green and blue components

Filtering - Defines the method that performs the filtering operations on an
image namely the blur and sharpen operation

Flipping - Defines the method that performs flipping on an image , either horizontal or vertical
flipping.

Split - Defines the split method that would split the red, green and blue components individually.

CompressionInterface - It defines the compress method that
is being called in the compression class to perform the operation

HistogramInterface - It defines the generatehistogram and calculatehistogram methods that creates
the
histogram for the image provided.

ColorCorrectInterface- It defines the colorcorrect method that is overrided in the colorcorrection
class
to perform colorcorrection.

Level Adjustment interface - It defines the levelsadjust method that is overrided in the
levelAdjustment class
to perform level Adjustment to the provided image.

3. View Package

Control Image LoaderView

- The ImageLoaderView interface defines methods for displaying messages
- related to image processing.
- displayProcessingMessage() is intended to show a message when an image is
- being processed.
- displayCompletionMessage() is intended to show a message once
- image processing is completed.

ImageProcessingGUI

- Initializes and manages the graphical user interface components.
- Handles user actions and delegates tasks to the GUIController.
- Displays images, histograms, and error messages.
- Provides access to GUI elements for interaction with the controller.

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

Image Citation:

Image used: res/tiger.jpeg
Image taken by: Afrah

Image used: res/source.jpg
Image taken by: Swetha

Image used: res/mask_img.jpg
Image taken by: Swetha




