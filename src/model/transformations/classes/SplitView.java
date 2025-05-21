package model.transformations.classes;

import model.imagerepresentation.Image;

/**
 * Utility class to handle split view functionality for image processing operations.
 */
public class SplitView {

  /**
   * Processes an image with split view effect.
   *
   * @param original      Original image
   * @param result        Result image where processed pixels will be stored
   * @param splitPosition Split percentage (0-100)
   */
  public static void applySplit(Image original, Image result, Integer splitPosition) {
    // Default to full image if split position is null
    int splitPixel = (splitPosition != null)
        ? (original.getWidth() * splitPosition) / 100 : original.getWidth();

    // Ensure splitPixel does not exceed the image width
    if (splitPixel >= original.getWidth()) {
      splitPixel = original.getWidth() - 1;
    }

    // Process for all rows
    for (int y = 0; y < original.getHeight(); y++) {
      // Left side of split (processed image)
      for (int x = 0; x < splitPixel; x++) {
        if (x < result.getWidth()) {
          // Keep the processed pixels from result image
          // They should already be there from image processing operation
        }
      }

      // Right side of split (original image)
      for (int x = splitPixel; x < original.getWidth(); x++) {
        if (x < result.getWidth()) {
          // Copy original image pixels
          result.setPixel(x, y, original.getPixel(x, y));
        }
      }
    }
  }

  /**
   * Calculates the split position based on the split percentage.
   *
   * @param width         Width of the original image
   * @param splitPosition Split percentage (0-100)
   * @return The split pixel index
   */
  public static int calculateSplitPosition(int width, Integer splitPosition) {
    return (splitPosition != null && splitPosition > 0 && splitPosition < 100) ?
        (width * splitPosition) / 100 : width;
  }
}