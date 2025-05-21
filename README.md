# Image Processing Application

This project implements a wide array of image processing functionalities, from basic manipulations like flipping and brightening to advanced features such as Haar Wavelet-based image compression, histogram analysis, color correction, and levels adjustment. The application is designed with an emphasis on maintainability and extensibility, allowing for easy integration of new image operations. It offers multiple interaction modes: a command-line interface for executing scripts, an interactive text-based console, and a user-friendly graphical interface.

## Video Demonstration
[![Demo Video](https://github.com/Swetha1802/Image-Processing-Application/blob/main/res/thumbnai-pdp.png)](https://youtu.be/67bclQcrGHc)

---

## 1. Key Features

The application supports a comprehensive set of image processing operations:

* **Image Loading & Saving**: Load and save images in **PPM**, **JPG**, and **PNG** formats.
* **Component Visualization**: Visualize individual **Red**, **Green**, **Blue** components, as well as **Value**, **Intensity**, and **Luma** representations as greyscale images.
* **Geometric Transformations**: Perform **horizontal** and **vertical** image flips.
* **Color Adjustments**:
    * **Brighten** or **darken** images by a specified increment.
    * Convert images to **greyscale** (using Luma) and apply a **sepia** tone effect.
    * **Color correction** by aligning histogram peaks.
    * **Levels adjustment** using black, mid, and white points for tonal range control.
* **Channel Manipulation**: **Split** a color image into its R, G, B channels, and **combine** three greyscale channel images back into a single color image.
* **Filtering**: Apply **blur** (Gaussian-like) and **sharpen** filters.
* **Image Compression**: Implement lossy compression using the **Haar Wavelet Transform**, controlled by a percentage.
* **Histogram Generation**: Create visual **histograms** showing the distribution of Red, Green, and Blue channels.
* **Split View**: Preview certain operations (blur, sharpen, sepia, greyscale, color correction, levels adjustment) on a portion of the image with a customizable split line.
* **Image Downsizing (Bilinear Interpolation)**: Reduce image dimensions while maintaining quality through bilinear interpolation.
* **Partial Image Manipulation**: Apply specific operations (**Component Visualization**, **Color Transformation**, **Filtering**) only to masked regions of an image.

---

## 2. Technical Details & Software Architecture

This project is built in Java and adheres strictly to the **Model-View-Controller (MVC)** design pattern, which is crucial for its extensibility and maintainability.

### 2.1. Controller Package

The `controller` package handles user input and mediates between the Model and View.

* `Main.java`: The primary entry point, managing execution flow based on command-line arguments (script file, interactive text mode, or GUI).
* `ScriptParser.java`: Parses and validates text-based commands from script files or interactive input, then delegates these commands to the appropriate Model operations.
* `ImageLoader.java`: Handles the low-level details of loading and saving images in various formats (**PPM**, **JPG**, **PNG**) using `ImageIO`.
* `GUIController.java`: Manages interactions from the graphical user interface, translating GUI events into Model calls and updating the GUI View accordingly.

### 2.2. Model Package

The `model` package encapsulates all image data and core image processing logic, independent of any user interface. It's organized into `ImageRepresentation` and `Transformation` sub-packages.

#### 2.2.1. `ImageRepresentation` Package

* `Image.java`: Represents an image with its dimensions and a 2D array of `Pixel` objects.
* `ImageOperations.java`: Manages the storage and retrieval of images by name using a `HashMap`.
* `Pixel.java`: Represents an individual pixel with **red**, **green**, and **blue** color components, ensuring values are clamped within the valid range (0-255).

#### 2.2.2. `Transformation` Package

This package contains classes and interfaces for all image manipulation operations.

* **Classes**:
    * `BrightenDarken`: Adjusts image brightness.
    * `ColorCorrection`: Aligns histogram peaks for color balance.
    * `ColorRepresentation`: Calculates **value**, **intensity**, and **luma** components.
    * `ColorTransformation`: Applies **sepia** and **greyscale** effects, supporting **partial masking** and **split view**.
    * `CombineChannels`: Reconstructs a color image from R, G, B channel images.
    * `ComponentVisualization`: Visualizes individual color components, supporting **partial masking**.
    * `Filtering`: Applies **blur** and **sharpen** filters, supporting **partial masking** and **split view**.
    * `Flipping`: Implements horizontal and vertical flips.
    * `Histogram`: Generates an image representing the color distribution histogram.
    * `ImageCompression`: Applies **Haar Wavelet Transform** for image compression.
    * `LevelAdjust`: Performs levels adjustment using a quadratic curve, supporting **split view**.
    * `Split`: Extracts individual R, G, B channels.
    * `ImageDownsizing`: Reduces image size using **bilinear interpolation**.
    * `SplitView`: A helper class encapsulating common split-view logic, reducing code redundancy.
* **Interfaces**: A corresponding interface for each transformation class defines the contracts for their respective operations.

### 2.3. View Package

The `view` package is responsible for displaying the user interface and handling user interactions.

* `ConsoleImageLoaderView`: A simple view for displaying processing messages in the console.
* `ImageProcessingGUI`: The main GUI class built with **Java Swing**. It initializes and manages UI components, displays the current image and its live histogram, handles user actions, and delegates tasks to the `GUIController`.

---

## 3. Getting Started

### Prerequisites

* Java Development Kit (JDK) 11 or higher.

### Building the Project

If you're using IntelliJ IDEA, simply open the project; it should handle compilation automatically.
To create a JAR file for executable distribution (recommended):

1.  In IntelliJ, go to `File -> Project Structure -> Project Settings -> Artifacts`.
2.  Click the `+` sign and select `JAR -> From Modules with dependencies...`.
3.  Choose your main module and select `Main` as the main class.
4.  Ensure "Build on make" is checked (if available). Click `OK`.
5.  Go to `Build -> Build Artifacts... -> [Your_Artifact_Name] -> Build`.
    The JAR file will be in `out/artifacts/<Your_Artifact_Name>/<Your_Artifact_Name>.jar`.

### Running the Application

You can run the application in three modes via command-line arguments:

1.  **Script File Mode:** Execute commands from a predefined script file.

    ```bash
    java -jar <Your_Artifact_Name>.jar -file path/to/your/script.txt
    ```

    **Example:** `java -jar program.jar -file res/sample_script.txt`
2.  **Interactive Text Mode:** Enter commands one by one in the console.

    ```bash
    java -jar <Your_Artifact_Name>.jar -text
    ```
3.  **Graphical User Interface (GUI) Mode:** Launch the interactive GUI.

    ```bash
    java -jar <Your_Artifact_Name>.jar
    ```

    (You can also double-click the `.jar` file on most systems.)

**Error Handling:** Invalid command-line arguments will result in an error message and program termination.

---

## 4. Usage (Script Command Examples)

The application supports various text-based commands. Here are some examples:

* **Loading & Saving:**

    ```
    load res/source.png source
    save res/processed_image.png processed_image
    ```
* **Component Visualization (with/without mask):**

    ```
    red-component source rc-source
    red-component source mask rc-mask
    ```
* **Color Transformation (with/without mask, with/without split view):**

    ```
    sepia source sepia-source
    greyscale source mask greyscale-mask
    blur source blur-source-split split 60
    ```
* **Channel Operations:**

    ```
    rgb-split tiger tiger-red tiger-green tiger-blue
    rgb-combine tiger-combine tiger-red tiger-green tiger-blue
    ```
* **Filtering:**

    ```
    sharpen tiger tiger-sharpen
    blur tiger tiger_blur_sp split 60
    ```
* **Compression:**

    ```
    compress 50 tiger tiger_compression_50
    ```
* **Histogram:**

    ```
    histogram tiger tiger_histogram
    ```
* **Color Correction & Levels Adjustment:**

    ```
    color-correct tiger tiger_color_correct
    level-adjust 0 100 200 tiger tiger_level
    ```
* **Brightness:**

    ```
    brighten 70 tiger tiger-brighten
    darken 90 tiger tiger-darken
    ```

For detailed instructions on using the GUI and a comprehensive list of all supported commands, please refer to the `USEME.md` file.

---

## 5. Testing

The project includes a robust test suite within the `test/` folder, covering:

* **Unit Tests**: Comprehensive tests for all Model components, ensuring the correctness of image manipulation algorithms.
* **Controller Tests**: Verification of controller logic and its interaction with the Model and View (using mocks for the View).

## 6. Contributors

Swetha Shankara Raman

Afrah Fathima

