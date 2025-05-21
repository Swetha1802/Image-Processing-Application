package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;

/**
 * This class is responsible for loading and saving images. It supports reading and writing both PPM
 * and standard image formats.
 */
public class ImageLoader {

  // Supported image formats for saving images
  private static final String[] SUPPORTED_FORMATS = {"png", "jpeg", "jpg", "ppm"};

  /**
   * Converts a custom Image object to a BufferedImage for saving.
   *
   * @param image The custom Image object.
   * @return A BufferedImage representing the same image.
   */
  public static BufferedImage convertToBufferedImage(Image image) {
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Pixel pixel = image.getPixel(x, y);
        int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
        bufferedImage.setRGB(x, y, rgb);
      }
    }
    return bufferedImage;
  }

  /**
   * Writes a custom Image object to a PPM file.
   *
   * @param filePath The path to write the PPM file to.
   * @param image    The Image object to save.
   * @throws IOException If the file cannot be written.
   */
  private static void writePPM(String filePath, Image image) throws IOException {
    BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

    int width = image.getWidth();
    int height = image.getHeight();

    bw.write("P3\n");
    bw.write(width + " " + height + "\n");
    bw.write("255\n");

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel pixel = image.getPixel(x, y);
        bw.write(pixel.getRed() + "\n");
        bw.write(pixel.getGreen() + "\n");
        bw.write(pixel.getBlue() + "\n");
      }
    }

    bw.close();
  }

  /**
   * Gets the file extension from a file path.
   *
   * @param filePath The file path.
   * @return The file extension.
   */
  private static String getFileExtension(String filePath) {
    int dotIndex = filePath.lastIndexOf('.');
    if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
      return filePath.substring(dotIndex + 1).toLowerCase();
    }
    throw new IllegalArgumentException("No valid file extension found in: " + filePath);
  }

  /**
   * Loads an image from a given path and stores it with the given name. It supports PPM format and
   * other standard formats like PNG or JPEG.
   *
   * @param imagePath The path of the image file to load.
   */
  public Image loadImage(String imagePath) {
    Image customImage;
    try {
      if (imagePath.endsWith(".ppm")) {
        customImage = readPPM(imagePath);
      } else {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        if (bufferedImage != null) {
          customImage = convertToCustomImage(bufferedImage);
        } else {
          throw new IOException("Image could not be loaded.");
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not load image: " + e.getMessage());
    }
    return customImage;
  }

  /**
   * Saves an image to the specified file path. Supports saving in PPM and other standard image
   * formats.
   *
   * @param imagePath   The path to save the image to.
   * @param imageToSave The Image object to save.
   */
  public void saveImage(String imagePath, Image imageToSave) {

    if (imageToSave != null) {
      try {
        String format = getFileExtension(imagePath);

        // Check if the format is supported
        if (!isSupportedFormat(format)) {
          throw new IllegalArgumentException("Unsupported file format: " + format
              + ". Supported formats are: png, jpeg, jpg, ppm");
        }

        if (format.equals("ppm")) {
          writePPM(imagePath, imageToSave);
        } else {
          BufferedImage bufferedImage = convertToBufferedImage(imageToSave);
          ImageIO.write(bufferedImage, format, new File(imagePath));
        }

      } catch (IOException e) {
        throw new IllegalArgumentException("Could not save image: " + e.getMessage());
      }
    } else {
      throw new IllegalArgumentException("Image to save cannot be null.");
    }
  }

  /**
   * Checks if the given file format is supported for saving images.
   *
   * @param format The file format.
   * @return True if the format is supported, false otherwise.
   */
  private boolean isSupportedFormat(String format) {
    for (String supportedFormat : SUPPORTED_FORMATS) {
      if (supportedFormat.equalsIgnoreCase(format)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Reads a PPM image from the given file path.
   *
   * @param filePath The path of the PPM file to read.
   * @return The Image object created from the PPM file.
   * @throws IOException If the file cannot be read.
   */
  private Image readPPM(String filePath) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filePath));
    String startnum = br.readLine().trim();

    // Makes sure the PPM file is valid
    if (!startnum.equals("P3")) {
      throw new IOException("Invalid PPM file format: " + startnum);
    }

    // Skipping the comments
    String line;
    while ((line = br.readLine()) != null) {
      line = line.trim();
      if (line.startsWith("#")) {
        continue;
      }
      break;
    }

    // Read width and height
    String[] dimensions = line.split("\\s+");
    int width = Integer.parseInt(dimensions[0]);
    int height = Integer.parseInt(dimensions[1]);

    // Reading max color value
    line = br.readLine().trim();
    int maxColorValue = Integer.parseInt(line);

    // Creates an Image object
    Image image = new Image(width, height);

    // Reads the pixel data
    int[] rgbValues = new int[width * height * 3];
    int index = 0;

    while ((line = br.readLine()) != null) {
      line = line.trim();
      if (!line.isEmpty() && !line.startsWith("#")) {
        String[] values = line.split("\\s+");
        for (String value : values) {
          rgbValues[index++] = Integer.parseInt(value);
          if (index >= rgbValues.length) {
            break;
          }
        }
      }
      if (index >= rgbValues.length) {
        break;
      }
    }

    // Sets the pixel values
    index = 0;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = clamp((rgbValues[index++] * 255) / maxColorValue);
        int g = clamp((rgbValues[index++] * 255) / maxColorValue);
        int b = clamp((rgbValues[index++] * 255) / maxColorValue);

        image.setPixel(x, y, new Pixel(r, g, b));
      }
    }

    br.close();
    return image;
  }

  /**
   * Clamps a value to make sure it's between 0 and 255.
   *
   * @param value The value to be clamped.
   * @return The clamped value.
   */
  private int clamp(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  /**
   * Converts a BufferedImage to a custom Image object.
   *
   * @param bufferedImage The BufferedImage to convert.
   * @return A custom Image object.
   */
  private Image convertToCustomImage(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    Image image = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = bufferedImage.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        image.setPixel(x, y, new Pixel(r, g, b));
      }
    }
    return image;
  }

}
