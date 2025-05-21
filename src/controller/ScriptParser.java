package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import model.imagerepresentation.ImageOperations;
import model.transformations.classes.BrightenDarken;
import model.transformations.classes.ColorCorrection;
import model.transformations.classes.ColorRepresentation;
import model.transformations.classes.ColorTransformation;
import model.transformations.classes.CombineChannels;
import model.transformations.classes.ComponentVisualization;
import model.transformations.classes.Filtering;
import model.transformations.classes.Flipping;
import model.transformations.classes.Histogram;
import model.transformations.classes.ImageCompression;
import model.transformations.classes.LevelAdjust;
import model.transformations.classes.Split;
import model.transformations.interfaces.BrightenDarkenInterface;
import model.transformations.interfaces.ColorCorrectionInterface;
import model.transformations.interfaces.ColorRepresentationInterface;
import model.transformations.interfaces.ColorTransformationInterface;
import model.transformations.interfaces.CombineChannelsInterface;
import model.transformations.interfaces.ComponentInterface;
import model.transformations.interfaces.FilteringInterface;
import model.transformations.interfaces.FlippingInterface;
import model.transformations.interfaces.HistogramInterface;
import model.transformations.interfaces.ImageCompressionInterface;
import model.transformations.interfaces.LevelAdjustInterface;
import model.transformations.interfaces.SplitInterface;
import view.ImageLoaderView;

/**
 * The ScriptParser class reads and executes commands from a script file. It uses ImageLoader to
 * load and save images and ImageOperations to perform various image manipulations based on commands
 * in the script.
 */
public class ScriptParser {

  private ImageLoader imageLoader;
  private ImageOperations imageOperations = new ImageOperations();
  private ImageLoaderView view; // Add view reference


  /**
   * Constructs a ScriptParser with the given ImageLoader and ImageOperations.
   *
   * @param imageLoader     The ImageLoader to load and save images.
   * @param imageOperations The ImageOperations to perform image manipulations.
   */
  public ScriptParser(ImageLoader imageLoader, ImageOperations imageOperations,
      ImageLoaderView view) {
    this.imageLoader = imageLoader;
    this.imageOperations = imageOperations;
    this.view = view;

  }

  /**
   * Parses the script file at the given path and executes its commands.
   *
   * @param scriptPath The path to the script file.
   * @throws IOException If there is an error reading the script file.
   */
  public void parseScript(String scriptPath) throws IOException {
    view.displayProcessingMessage(); // Display processing message

    try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && !line.startsWith("#")) {
          executeLine(line);
        }
      }
    }
    view.displayCompletionMessage();

  }

  /**
   * Executes a single line command from the script.
   *
   * @param line The command line to execute.
   * @throws IOException If there is an error during execution.
   */
  void executeLine(String line) throws IOException {
    String[] parts = line.split("\\s+");
    String command = parts[0].toLowerCase();
    FlippingInterface flip = new Flipping();
    BrightenDarkenInterface bd = new BrightenDarken();
    FilteringInterface filter = new Filtering();
    ComponentInterface comp = new ComponentVisualization();
    ColorRepresentationInterface cr = new ColorRepresentation();
    CombineChannelsInterface cci = new CombineChannels();
    ColorTransformationInterface clr = new ColorTransformation();
    SplitInterface spl = new Split();
    HistogramInterface hi = new Histogram();
    ColorCorrectionInterface clrc = new ColorCorrection();
    LevelAdjustInterface lai = new LevelAdjust();
    ImageCompressionInterface ic = new ImageCompression();

    switch (command) {
      case "load":
        validateArgCount(parts, 3, "load");
        imageOperations.saveImage(parts[2], imageLoader.loadImage(parts[1]));
        break;
      case "save":
        validateArgCount(parts, 3, "save");
        imageLoader.saveImage(parts[1], imageOperations.getImage(parts[2]));
        break;
      // Component visualization commands
      case "red-component":
        if (parts.length == 3) {
          validateArgCount(parts, 3, "red-component");
          comp.redComponent(parts[1], parts[2]);
        } else if (parts.length == 4) {
          validateArgCount(parts, 4, "red-component");
          comp.redComponent(parts[1], parts[3], parts[2]);
        } else {
          throw new IllegalArgumentException(
              "Invalid number of arguments for red-component. "
                  + "Expected: red-component source dest [mask]");
        }
        break;

      case "green-component":
        if (parts.length == 3) {
          validateArgCount(parts, 3, "green-component");
          comp.greenComponent(parts[1], parts[2]);
        } else if (parts.length == 4) {
          validateArgCount(parts, 4, "green-component");
          comp.greenComponent(parts[1], parts[3], parts[2]);
        } else {
          throw new IllegalArgumentException(
              "Invalid number of arguments for green-component. "
                  + "Expected: green-component source dest [mask]");
        }
        break;

      case "blue-component":
        if (parts.length == 3) {
          validateArgCount(parts, 3, "blue-component");
          comp.blueComponent(parts[1], parts[2]);
        } else if (parts.length == 4) {
          validateArgCount(parts, 4, "blue-component");
          comp.blueComponent(parts[1], parts[3], parts[2]);
        } else {
          throw new IllegalArgumentException(
              "Invalid number of arguments for blue-component. "
                  + "Expected: blue-component source dest [mask]");
        }
        break;
      case "value-component":
        validateArgCount(parts, 3, "value-component");
        cr.valueComponent(parts[1], parts[2]);
        break;
      case "luma-component":
        validateArgCount(parts, 3, "luma-component");
        cr.lumaComponent(parts[1], parts[2]);
        break;
      case "intensity-component":
        validateArgCount(parts, 3, "intensity-component");
        cr.intensityComponent(parts[1], parts[2]);
        break;
      case "horizontal-flip":
        validateArgCount(parts, 3, "horizontal-flip");
        flip.horizontalFlip(parts[1], parts[2]);
        break;
      case "vertical-flip":
        validateArgCount(parts, 3, "vertical-flip");
        flip.verticalFlip(parts[1], parts[2]);
        break;
      case "brighten":
        validateArgCount(parts, 4, "brighten");
        bd.brighten(Integer.parseInt(parts[1]), parts[2], parts[3]);
        break;
      case "darken":
        validateArgCount(parts, 4, "darken");
        bd.darken(Integer.parseInt(parts[1]), parts[2], parts[3]);
        break;
      case "rgb-split":
        validateArgCount(parts, 5, "rgb-split");
        spl.rgbSplit(parts[1], parts[2], parts[3], parts[4]);
        break;
      case "rgb-combine":
        validateArgCount(parts, 5, "rgb-combine");
        cci.rgbCombine(parts[1], parts[2], parts[3], parts[4]);
        break;
      case "blur":
        if (parts.length == 3) {
          // Command: blur source-image dest-image
          filter.blur(parts[1], parts[2], 100, null);
        } else if (parts.length == 5 && parts[3].equals("split")) {
          // Command: blur source-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[4]);
            filter.blur(parts[1], parts[2], splitPosition, null);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for blur command");
          }
        } else if (parts.length == 4) {
          // Command: blur source-image mask-image dest-image
          filter.blur(parts[1], parts[3], 100, parts[2]);
        } else if (parts.length == 6 && parts[4].equals("split")) {
          // Command: blur source-image mask-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[5]);
            filter.blur(parts[1], parts[3], splitPosition, parts[2]);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for blur command");
          }
        } else {
          throw new IllegalArgumentException("Invalid blur command");
        }
        break;
      case "sharpen":
        if (parts.length == 3) {
          // Command: sharpen source-image dest-image
          filter.sharpen(parts[1], parts[2], 100, null);
        } else if (parts.length == 5 && parts[3].equals("split")) {
          // Command: sharpen source-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[4]);
            filter.sharpen(parts[1], parts[2], splitPosition, null);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for sharpen command");
          }
        } else if (parts.length == 4) {
          // Command: sharpen source-image mask-image dest-image
          filter.sharpen(parts[1], parts[3], 100, parts[2]);
        } else if (parts.length == 6 && parts[4].equals("split")) {
          // Command: sharpen source-image mask-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[5]);
            filter.sharpen(parts[1], parts[3], splitPosition, parts[2]);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for sharpen command");
          }
        } else {
          throw new IllegalArgumentException("Invalid sharpen command");
        }
        break;
      case "sepia":
        if (parts.length == 3) {
          // Command: blur source-image dest-image
          clr.sepia(parts[1], parts[2], 100, null);
        } else if (parts.length == 5 && parts[3].equals("split")) {
          // Command: blur source-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[4]);
            clr.sepia(parts[1], parts[2], splitPosition, null);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for blur command");
          }
        } else if (parts.length == 4) {
          // Command: blur source-image mask-image dest-image
          clr.sepia(parts[1], parts[3], 100, parts[2]);
        } else if (parts.length == 6 && parts[4].equals("split")) {
          // Command: blur source-image mask-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[5]);
            clr.sepia(parts[1], parts[3], splitPosition, parts[2]);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for blur command");
          }
        } else {
          throw new IllegalArgumentException("Invalid blur command");
        }
        break;

      case "greyscale":
        if (parts.length == 3) {
          // Command: blur source-image dest-image
          clr.greyscale(parts[1], parts[2], 100, null);
        } else if (parts.length == 5 && parts[3].equals("split")) {
          // Command: blur source-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[4]);
            clr.greyscale(parts[1], parts[2], splitPosition, null);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for blur command");
          }
        } else if (parts.length == 4) {
          // Command: blur source-image mask-image dest-image
          clr.greyscale(parts[1], parts[3], 100, parts[2]);
        } else if (parts.length == 6 && parts[4].equals("split")) {
          // Command: blur source-image mask-image dest-image split X
          try {
            int splitPosition = Integer.parseInt(parts[5]);
            clr.greyscale(parts[1], parts[3], splitPosition, parts[2]);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for blur command");
          }
        } else {
          throw new IllegalArgumentException("Invalid blur command");
        }
        break;
      case "histogram":
        validateArgCount(parts, 3, "histogram");
        hi.generateHistogram(parts[1], parts[2]);
        break;
      case "color-correct":
        if (parts.length == 3) {
          // No split position provided
          clrc.colorCorrect(parts[1], parts[2], null);
        } else if (parts.length == 5 && parts[3].equals("split")) {
          // Split position provided
          try {
            int splitPosition = Integer.parseInt(parts[4]);
            clrc.colorCorrect(parts[1], parts[2], splitPosition);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for color-correct command");
          }
        } else {
          System.out.println("Parts array: " + Arrays.toString(parts));
          System.out.println("Checking parts[3]: " + parts[3]);
          throw new IllegalArgumentException("Invalid color-correct command !");
        }
        break;
      case "compress":
        validateArgCount(parts, 4, "compress");
        int threshold = Integer.parseInt(parts[1]);
        ic.compress(threshold, parts[2], parts[3]);
        break;
      case "level-adjust":
        if (parts.length == 6) {
          // Command without split: "level-adjust b m w imageName destImageName"
          lai.levelsAdjust(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
              Integer.parseInt(parts[3]), parts[4], parts[5], null);
        } else if (parts.length == 8 && parts[6].equals("split")) {
          // Command with split: "level-adjust b m w imageName destImageName split 50"
          try {
            int splitPosition = Integer.parseInt(parts[7]);
            lai.levelsAdjust(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]), parts[4], parts[5], splitPosition);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid split position for level-adjust command");
          }
        } else {
          throw new IllegalArgumentException("Invalid level-adjust command");
        }
        break;
      case "run":
        validateArgCount(parts, 2, "run");
        parseScript(parts[1]);
        break;
      default:
        throw new IllegalArgumentException("Unknown command: " + command);
    }
  }

  /**
   * Validates the number of arguments for a command.
   *
   * @param parts         The command and its arguments.
   * @param expectedCount The expected number of arguments.
   * @param commandName   The name of the command being validated.
   * @throws IllegalArgumentException If the number of arguments is incorrect.
   */
  private void validateArgCount(String[] parts, int expectedCount, String commandName) {
    if (parts.length != expectedCount) {
      throw new IllegalArgumentException("Invalid " + commandName + " command");
    }
  }
}
