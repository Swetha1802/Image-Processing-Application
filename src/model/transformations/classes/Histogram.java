package model.transformations.classes;

import java.awt.Color;
import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.HistogramInterface;

/**
 * This class provides methods to generate and calculate histograms for images, as well as visual
 * representations of histograms.
 */
public class Histogram implements HistogramInterface {

  /**
   * Generates a histogram image for a given input image and saves it to a destination file.
   *
   * @param imageName     the name of the input image file.
   * @param destImageName the name of the output image file to save the histogram image.
   */
  @Override
  public void generateHistogram(String imageName, String destImageName) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image histogramImage = createHistogram(original);
    opn.saveImage(destImageName, histogramImage);
  }

  /**
   * Calculates the histograms for the red, green, and blue channels of an image.
   *
   * @param image the input image.
   * @return a 2D array containing the histograms for red, green, and blue channels.
   */
  @Override
  public int[][] calculateHistograms(Image image) {
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Pixel pixel = image.getPixel(x, y);
        redHistogram[pixel.getRed()]++;
        greenHistogram[pixel.getGreen()]++;
        blueHistogram[pixel.getBlue()]++;
      }
    }

    return new int[][]{redHistogram, greenHistogram, blueHistogram};
  }

  /**
   * Creates a histogram image from the original image.
   *
   * @param original the input image.
   * @return an image representing the histogram.
   */
  // Overloaded method to handle existing behavior without specifying a component
  public Image createHistogram(Image original) {
    return createHistogram(original, "all"); // Default behavior, draws all components
  }

  /**
   * Creates a visual histogram image from pixel color distribution.
   *
   * @param original  The source image to analyze
   * @param component Specifies which color channel to highlight (e.g., "red", "green", "blue")
   * @return A new image showing the color intensity histogram
   */
  // Main method with two parameters for conditional behavior
  public Image createHistogram(Image original, String component) {
    Image histogramImage = new Image(256, 256);
    fillBackground(histogramImage, new Pixel(255, 255, 255)); // White background

    int[][] histograms = calculateHistograms(original);
    int maxValue = Math.max(Math.max(getMaxValue(histograms[0]), getMaxValue(histograms[1])),
        getMaxValue(histograms[2]));

    drawGrid(histogramImage, new Pixel(230, 230, 230), 16);

    // Conditional drawing based on the specified component
    switch (component.toLowerCase()) {
      case "red":
        drawHistogramOutline(histogramImage, histograms[0], Color.RED, maxValue);
        break;
      case "green":
        drawHistogramOutline(histogramImage, histograms[1], Color.GREEN, maxValue);
        break;
      case "blue":
        drawHistogramOutline(histogramImage, histograms[2], Color.BLUE, maxValue);
        break;
      case "all":
      default:
        // Draw all histograms if "all" is specified or no specific component is provided
        drawHistogramOutline(histogramImage, histograms[0], Color.RED, maxValue);
        drawHistogramOutline(histogramImage, histograms[1], Color.GREEN, maxValue);
        drawHistogramOutline(histogramImage, histograms[2], Color.BLUE, maxValue);
        break;
    }

    return histogramImage;
  }

  /**
   * Finds the maximum value in a histogram.
   *
   * @param histogram the histogram array.
   * @return the maximum value in the histogram.
   */
  private int getMaxValue(int[] histogram) {
    int max = 0;
    for (int value : histogram) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  /**
   * Fills the background of an image with a specified color.
   *
   * @param image the image to fill.
   * @param color the color to fill with.
   */
  private void fillBackground(Image image, Pixel color) {
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        image.setPixel(x, y, color);
      }
    }
  }

  /**
   * Draws the histogram outline for a specified color channel.
   *
   * @param histogramImage the image to draw on.
   * @param histogram      the histogram data.
   * @param color          the color for the outline.
   * @param maxValue       the maximum value used for scaling.
   */
  private void drawHistogramOutline(Image histogramImage, int[] histogram, Color color,
      int maxValue) {
    for (int x = 1; x < histogram.length; x++) {
      int heightPrev = (int) ((double) histogram[x - 1] / maxValue
          * 255); // Scale to fit 255 pixels
      int heightCurr = (int) ((double) histogram[x] / maxValue * 255);

      heightPrev = Math.min(heightPrev, 255);
      heightCurr = Math.min(heightCurr, 255);

      drawLine(histogramImage, x - 1, 255 - heightPrev, x, 255 - heightCurr, color);
    }
  }

  /**
   * Draws a line between two points on an image using a specified color.
   *
   * @param img   the image to draw on.
   * @param x1    the starting x-coordinate.
   * @param y1    the starting y-coordinate.
   * @param x2    the ending x-coordinate.
   * @param y2    the ending y-coordinate.
   * @param color the color to draw the line.
   */
  private void drawLine(Image img, int x1, int y1, int x2, int y2, Color color) {
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int sx = x1 < x2 ? 1 : -1;
    int sy = y1 < y2 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      if (x1 >= 0 && x1 < 256 && y1 >= 0 && y1 < 256) {
        img.setPixel(x1, y1, new Pixel(color.getRed(), color.getGreen(), color.getBlue()));
      }
      if (x1 == x2 && y1 == y2) {
        break;
      }
      int e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x1 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y1 += sy;
      }
    }
  }

  /**
   * Draws a grid on an image with specified color and spacing.
   *
   * @param histogramImage the image to draw the grid on.
   * @param color          the color of the grid lines.
   * @param spacing        the spacing between grid lines.
   */
  private void drawGrid(Image histogramImage, Pixel color, int spacing) {
    for (int i = 0; i < 256; i += spacing) {
      for (int y = 0; y < histogramImage.getHeight(); y++) {
        if (i < histogramImage.getWidth()) {
          histogramImage.setPixel(i, y, color);
        }
      }
      for (int x = 0; x < histogramImage.getWidth(); x++) {
        if (i < histogramImage.getHeight()) {
          histogramImage.setPixel(x, i, color);
        }
      }
    }
  }
}
