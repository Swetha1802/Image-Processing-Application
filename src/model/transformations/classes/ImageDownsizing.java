package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.ImageDownsizingInterface;

/**
 * The ImageDownsizing class provides the functionality to downscale an image using interpolation.
 */
public class ImageDownsizing implements ImageDownsizingInterface {

  /**
   * Downscale an image to the target dimensions.
   *
   * @param imageName     The name of the original image.
   * @param destImageName The name of the downsized image to save.
   * @param targetWidth   The target width of the downsized image.
   * @param targetHeight  The target height of the downsized image.
   */

  @Override
  public void downsize(String imageName, String destImageName, int targetWidth, int targetHeight) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);

    int originalWidth = original.getWidth();
    int originalHeight = original.getHeight();

    if (targetWidth > originalWidth || targetHeight > originalHeight) {
      throw new IllegalArgumentException(
          "Target dimensions must be smaller than or equal to the original dimensions.");
    }
    Image downsized = new Image(targetWidth, targetHeight);

    // Iterate over each pixel in the target downsized image
    for (int y = 0; y < targetHeight; y++) {
      for (int x = 0; x < targetWidth; x++) {

        // Calculate the corresponding location in the original image
        double srcX = (double) x * originalWidth / targetWidth;
        double srcY = (double) y * originalHeight / targetHeight;

        // Get the integer parts and the fractional parts of the coordinates
        int x1 = (int) Math.floor(srcX);
        int y1 = (int) Math.floor(srcY);
        double dx = srcX - x1;
        double dy = srcY - y1;

        // Calculate the bounds for the neighboring pixels to be used in interpolation
        int x2 = Math.min(x1 + 1, originalWidth - 1);
        int y2 = Math.min(y1 + 1, originalHeight - 1);

        // Get the four neighboring pixels for bilinear interpolation
        Pixel pA = original.getPixel(x1, y1);
        Pixel pB = original.getPixel(x2, y1);
        Pixel pC = original.getPixel(x1, y2);
        Pixel pD = original.getPixel(x2, y2);

        // Interpolate the red, green and blue components separately
        int red = bilinearInterpolate(pA.getRed(), pB.getRed(), pC.getRed(), pD.getRed(), dx, dy);
        int green = bilinearInterpolate(pA.getGreen(), pB.getGreen(), pC.getGreen(), pD.getGreen(),
            dx, dy);
        int blue = bilinearInterpolate(pA.getBlue(), pB.getBlue(), pC.getBlue(), pD.getBlue(), dx,
            dy);

        // Set the pixel in the downsized image
        downsized.setPixel(x, y, new Pixel(red, green, blue));
      }
    }

    // Save the downsized image
    opn.saveImage(destImageName, downsized);
  }

  /**
   * Performs bilinear interpolation for a given color component.
   *
   * @param a  Value at (x1, y1)
   * @param b  Value at (x2, y1)
   * @param c  Value at (x1, y2)
   * @param d  Value at (x2, y2)
   * @param dx Distance in x direction between original and mapped point
   * @param dy Distance in y direction between original and mapped point
   * @return The interpolated value, clamped between 0 and 255
   */
  private int bilinearInterpolate(int a, int b, int c, int d, double dx, double dy) {
    // Calculate the weighted average of the values
    double m = (1 - dx) * a + dx * b;
    double n = (1 - dx) * c + dx * d;
    double value = (1 - dy) * m + dy * n;

    return clamp((int) Math.round(value));
  }

  /**
   * Clamps a value to the valid pixel range [0, 255].
   *
   * @param value The value to clamp
   * @return The clamped value
   */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }
}
