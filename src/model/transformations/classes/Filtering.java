package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.FilteringInterface;

/**
 * Class for applying various filters to images.
 */
public class Filtering implements FilteringInterface {

  /**
   * Applies a filter to the image using the specified matrix and saves the result.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the filtered image.
   * @param matrix        the filter matrix to apply.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   * @param maskImageName the name of the mask image to apply the filter partially, can be null.
   */
  public void applyFilter(String imageName, String destImageName, float[] matrix,
      Integer splitPosition, String maskImageName) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());
    Image mask = maskImageName != null ? opn.getImage(maskImageName) : null;

    // Apply filter to the entire image
    int matrixSize = (int) Math.sqrt(matrix.length);
    int matrixRadius = matrixSize / 2;

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        if (mask == null || (mask.getPixel(x, y).getRed() == 0
            && mask.getPixel(x, y).getGreen() == 0 && mask.getPixel(x, y).getBlue() == 0)) {
          float red = 0;
          float green = 0;
          float blue = 0;

          // Apply filter only to the relevant portion based on the matrix
          for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
              int pixelX = Math.min(Math.max(x + j - matrixRadius, 0), original.getWidth() - 1);
              int pixelY = Math.min(Math.max(y + i - matrixRadius, 0), original.getHeight() - 1);

              Pixel pixel = original.getPixel(pixelX, pixelY);
              float factor = matrix[i * matrixSize + j];

              red += pixel.getRed() * factor;
              green += pixel.getGreen() * factor;
              blue += pixel.getBlue() * factor;
            }
          }

          // Clamping the values to valid color ranges (0-255)
          int r = Math.min(255, Math.max(0, (int) red));
          int g = Math.min(255, Math.max(0, (int) green));
          int b = Math.min(255, Math.max(0, (int) blue));

          result.setPixel(x, y, new Pixel(r, g, b));
        } else {
          result.setPixel(x, y, original.getPixel(x, y)); // Retain original pixel if not masked
        }
      }
    }

    // After filtering, apply the split view effect
    SplitView.applySplit(original, result, splitPosition);

    // Save the resulting image
    opn.saveImage(destImageName, result);
  }

  /**
   * Applies a blur filter to the specified image and saves the result.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the blurred image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   * @param maskImageName the name of the mask image to apply the filter partially, can be null.
   */
  public void blur(String imageName, String destImageName, Integer splitPosition,
      String maskImageName) {
    if (splitPosition == null) {
      splitPosition = 100; // Default to no split
    }

    float[] matrix = {1 / 16f, 1 / 8f, 1 / 16f, 1 / 8f, 1 / 4f, 1 / 8f, 1 / 16f, 1 / 8f, 1 / 16f};
    applyFilter(imageName, destImageName, matrix, splitPosition, maskImageName);
  }

  /**
   * Applies a blur filter to the specified image without a mask, for compatibility with existing
   * code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the blurred image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   */
  public void blur(String imageName, String destImageName, Integer splitPosition) {
    blur(imageName, destImageName, splitPosition, null);
  }

  /**
   * Applies a sharpen filter to the specified image and saves the result.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the sharpened image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   * @param maskImageName the name of the mask image to apply the filter partially, can be null.
   */
  public void sharpen(String imageName, String destImageName, Integer splitPosition,
      String maskImageName) {
    if (splitPosition == null) {
      splitPosition = 100; // Default to no split
    }

    float[] matrix = {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f,
        -1 / 8f, -1 / 8f, 1 / 4f, 1f, 1 / 4f, -1 / 8f, -1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f,
        -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f};
    applyFilter(imageName, destImageName, matrix, splitPosition, maskImageName);
  }

  /**
   * Applies a sharpen filter to the specified image without a mask, for compatibility with existing
   * code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the sharpened image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   */
  public void sharpen(String imageName, String destImageName, Integer splitPosition) {
    sharpen(imageName, destImageName, splitPosition, null);
  }
}