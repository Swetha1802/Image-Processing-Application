package model.transformations.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.ImageCompressionInterface;

/**
 * This class implements image compression using the Haar wavelet transform to achieve a pixelated
 * blur effect.
 */
public class ImageCompression implements ImageCompressionInterface {

  /**
   * Transforms a sequence using averaging and differencing.
   */
  private double[] transform(double[] sequence) {
    int length = sequence.length;
    double[] result = new double[length];
    int halfLength = length / 2;

    for (int i = 0; i < halfLength; i++) {
      double a = sequence[2 * i];
      double b = sequence[2 * i + 1];
      result[i] = (a + b) / 2.0;  // Average
      result[i + halfLength] = (a - b) / 2.0;  // Difference
    }

    if (length % 2 != 0) {
      result[halfLength] = sequence[length - 1];
    }

    return result;
  }

  /**
   * Inverts a transformed sequence.
   */
  private double[] inverse(double[] sequence) {
    int length = sequence.length;
    double[] result = new double[length];
    int halfLength = length / 2;

    for (int i = 0; i < halfLength; i++) {
      double avg = sequence[i];
      double diff = sequence[i + halfLength];
      result[2 * i] = (avg + diff);
      result[2 * i + 1] = avg - diff;
    }

    if (length % 2 != 0) {
      result[length - 1] = sequence[halfLength];
    }

    return result;
  }

  /**
   * Applies a pixelated effect by simplifying wavelet coefficients.
   */
  private void applyPixelation(double[][] data, int width, int height, double compressionRatio) {
    List<Double> allCoefficients = new ArrayList<>();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (data[y][x] != 0) {

          allCoefficients.add(Math.abs(data[y][x]));
        }
      }
    }
    HashSet<Double> clearDup = new HashSet<>(allCoefficients);
    allCoefficients.clear();
    allCoefficients.addAll(clearDup);

    // Sort coefficients to find threshold
    Collections.sort(allCoefficients);
    int thresholdIndex = (int) (allCoefficients.size() * compressionRatio / 100.0);
    double threshold = allCoefficients.get(thresholdIndex);

    // Apply aggressive threshold to create a pixelated effect
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        if (Math.abs(data[y][x]) < threshold) {
          data[y][x] = 0.0;  // Zero out small coefficients
        }

      }
    }
  }

  /**
   * Main compression method for pixelation.
   */
  public void compress(double compressionRatio, String imageName, String destImageName) {
    if (compressionRatio < 0 || compressionRatio > 100) {
      throw new IllegalArgumentException(
          "Compression ratio must be between 0 and 100. Provided: " + compressionRatio);
    }
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());

    int width = original.getWidth();
    int height = original.getHeight();
    int paddedWidth = nextPowerOf2(width);
    int paddedHeight = nextPowerOf2(height);

    // Process each color channel independently
    double[][][] channels = new double[3][paddedHeight][paddedWidth];

    // Extract channels
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel p = original.getPixel(x, y);
        channels[0][y][x] = p.getRed();
        channels[1][y][x] = p.getGreen();
        channels[2][y][x] = p.getBlue();
      }
    }

    // Process each channel independently
    for (int c = 0; c < 3; c++) {
      // Transform rows
      for (int y = 0; y < paddedHeight; y++) {
        int m = paddedWidth;
        while (m > 1) {
          double[] row = Arrays.copyOf(channels[c][y], m);
          double[] transformed = transform(row);
          System.arraycopy(transformed, 0, channels[c][y], 0, m);
          m /= 2;
        }
      }

      // Transform columns
      for (int x = 0; x < paddedWidth; x++) {
        int m = paddedHeight;
        while (m > 1) {
          double[] col = new double[m];
          for (int y = 0; y < m; y++) {
            col[y] = channels[c][y][x];
          }
          double[] transformed = transform(col);
          for (int y = 0; y < m; y++) {
            channels[c][y][x] = transformed[y];
          }
          m /= 2;
        }
      }

      // Apply pixelation
      applyPixelation(channels[c], paddedWidth, paddedHeight, compressionRatio);

      // Inverse transform columns
      for (int x = 0; x < paddedWidth; x++) {
        int m = 2;
        while (m <= paddedHeight) {
          double[] col = new double[m];
          for (int y = 0; y < m; y++) {
            col[y] = channels[c][y][x];
          }
          double[] inverted = inverse(col);
          for (int y = 0; y < m; y++) {
            channels[c][y][x] = inverted[y];
          }
          m *= 2;
        }
      }

      // Inverse transform rows
      for (int y = 0; y < paddedHeight; y++) {
        int m = 2;
        while (m <= paddedWidth) {
          double[] row = Arrays.copyOf(channels[c][y], m);
          double[] inverted = inverse(row);
          System.arraycopy(inverted, 0, channels[c][y], 0, m);
          m *= 2;
        }
      }
    }

    // Combine channels and clamp values
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = clamp((int) Math.round(channels[0][y][x]));
        int g = clamp((int) Math.round(channels[1][y][x]));
        int b = clamp((int) Math.round(channels[2][y][x]));
        result.setPixel(x, y, new Pixel(r, g, b));


      }
    }

    opn.saveImage(destImageName, result);
  }

  /**
   * Clamps a value to the valid pixel range [0, 255].
   */
  private int clamp(int value) {
    return Math.min(255, Math.max(0, value));
  }

  /**
   * Gets the next power of 2 that's greater than or equal to n.
   */
  private int nextPowerOf2(int n) {
    int power = 1;
    while (power < n) {
      power *= 2;
    }
    return power;
  }
}