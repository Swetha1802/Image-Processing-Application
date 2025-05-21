package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.LevelAdjustInterface;

/**
 * The levelAdjustment class provides the functionality to perform levelAdjustment on an image.
 */

public class LevelAdjust implements LevelAdjustInterface {

  @Override
  public void levelsAdjust(int b, int m, int w, String imageName, String destImageName,
      Integer splitPercentage) {
    validateLevels(b, m, w);
    validateSplitPercentage(splitPercentage);

    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);

    // Compute quadratic coefficients for level adjustment
    double[] coefficients = computeQuadraticCoefficients(b, m, w);

    // Adjust levels and apply split view logic
    Image adjustedImage = adjustLevels(original, coefficients, splitPercentage);

    // Save the adjusted image
    opn.saveImage(destImageName, adjustedImage);

    // Generate histogram for the adjusted image
    Histogram histogramGenerator = new Histogram();
    histogramGenerator.generateHistogram(destImageName, "histogram_" + destImageName);
  }

  private void validateLevels(int b, int m, int w) {
    if (b < 0 || b > 255 || m < 0 || m > 255 || w < 0 || w > 255) {
      throw new IllegalArgumentException("Black, Mid, and White values must be between 0 and 255.");
    }
    if (b >= m || m >= w) {
      throw new IllegalArgumentException("Black (b) < Mid (m) < White (w) must hold.");
    }
  }

  private void validateSplitPercentage(Integer splitPercentage) {
    if (splitPercentage != null && (splitPercentage < 0 || splitPercentage > 100)) {
      throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
    }
  }

  private Image adjustLevels(Image original, double[] coefficients, Integer splitPercentage) {
    // Create a new image for storing the adjusted pixels
    Image adjustedImage = new Image(original.getWidth(), original.getHeight());

    // Calculate the split position using SplitViewHandler
    int splitPosition = SplitView.calculateSplitPosition(original.getWidth(), splitPercentage);

    // Adjust pixels and apply the split view logic
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        Pixel pixel = original.getPixel(x, y);

        if (x < splitPosition) {
          // Apply level adjustment for pixels before the split position
          int red = adjustValue(pixel.getRed(), coefficients);
          int green = adjustValue(pixel.getGreen(), coefficients);
          int blue = adjustValue(pixel.getBlue(), coefficients);
          adjustedImage.setPixel(x, y, new Pixel(red, green, blue));
        } else {
          // Copy original pixels after the split position (no adjustment)
          adjustedImage.setPixel(x, y,
              new Pixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
        }
      }
    }

    return adjustedImage;
  }

  private int adjustValue(int value, double[] coefficients) {
    double normValue = value / 255.0;
    double adjustedValue = applyQuadraticFunction(normValue, coefficients[0], coefficients[1],
        coefficients[2]);
    return (int) Math.min(255, Math.max(0, adjustedValue * 255));
  }

  private double[] computeQuadraticCoefficients(int b, int m, int w) {
    double aa = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    double a_a = b * (128 - 255) + 128 * w - 255 * m;
    double a_b = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    double a_c = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);

    double a = a_a / aa;
    double b_ = a_b / aa;
    double c = a_c / aa;

    return new double[]{a, b_, c};
  }

  private double applyQuadraticFunction(double value, double a, double b, double c) {
    // Apply the quadratic formula: y = ax^2 + bx + c
    return a * value * value + b * value + c;
  }
}