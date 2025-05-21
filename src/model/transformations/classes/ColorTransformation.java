package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.ColorTransformationInterface;

/**
 * This class provides functions to apply color transformations to images like sepia and greyscale
 * effects with support for a split view and partial masking.
 */
public class ColorTransformation implements ColorTransformationInterface {

  /**
   * Changes image colors by applying transformation matrix to pixels.
   *
   * @param imageName     Name of the source image
   * @param destImageName Name for the new transformed image
   * @param redFactorR    Red channel's red factor
   * @param redFactorG    Red channel's green factor
   * @param redFactorB    Red channel's blue factor
   * @param greenFactorR  Green channel's red factor
   * @param greenFactorG  Green channel's green factor
   * @param greenFactorB  Green channel's blue factor
   * @param blueFactorR   Blue channel's red factor
   * @param blueFactorG   Blue channel's green factor
   * @param blueFactorB   Blue channel's blue factor
   * @param splitPosition Where to split image for partial transformation
   * @param maskImageName Optional mask image to control transformation
   */
  public void applyColorTransformation(String imageName, String destImageName, double redFactorR,
      double redFactorG, double redFactorB, double greenFactorR, double greenFactorG,
      double greenFactorB, double blueFactorR, double blueFactorG, double blueFactorB,
      Integer splitPosition, String maskImageName) {

    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());
    Image mask = maskImageName != null ? opn.getImage(maskImageName) : null;

    // Apply color transformation to all pixels or based on the mask
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        if (mask == null || (mask.getPixel(x, y).getRed() == 0
            && mask.getPixel(x, y).getGreen() == 0 && mask.getPixel(x, y).getBlue() == 0)) {
          Pixel pixel = original.getPixel(x, y);
          int r = pixel.getRed();
          int g = pixel.getGreen();
          int b = pixel.getBlue();

          int newRed = (int) (redFactorR * r + redFactorG * g + redFactorB * b);
          int newGreen = (int) (greenFactorR * r + greenFactorG * g + greenFactorB * b);
          int newBlue = (int) (blueFactorR * r + blueFactorG * g + blueFactorB * b);

          // Clamping to ensure values are between 0-255
          newRed = Math.min(255, Math.max(0, newRed));
          newGreen = Math.min(255, Math.max(0, newGreen));
          newBlue = Math.min(255, Math.max(0, newBlue));

          result.setPixel(x, y, new Pixel(newRed, newGreen, newBlue));
        } else {
          result.setPixel(x, y, original.getPixel(x, y)); // Retain original pixel if not masked
        }
      }
    }

    // Apply split view effect
    SplitView.applySplit(original, result, splitPosition);

    opn.saveImage(destImageName, result);
  }

  /**
   * Applies a sepia tone effect to an image with an optional mask.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the sepia image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   * @param maskImageName the name of the mask image to apply the transformation partially, can be
   *                      null.
   */
  public void sepia(String imageName, String destImageName, Integer splitPosition,
      String maskImageName) {
    applyColorTransformation(imageName, destImageName, 0.393, 0.769, 0.189, 0.349, 0.686, 0.168,
        0.272, 0.534, 0.131, splitPosition, maskImageName);
  }

  /**
   * Applies a sepia tone effect to an image without a mask for compatibility with existing code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the sepia image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   */
  public void sepia(String imageName, String destImageName, Integer splitPosition) {
    sepia(imageName, destImageName, splitPosition, null);
  }

  /**
   * Applies a greyscale effect to an image with an optional mask.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the greyscale image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   * @param maskImageName the name of the mask image to apply the transformation partially, can be
   *                      null.
   */
  public void greyscale(String imageName, String destImageName, Integer splitPosition,
      String maskImageName) {
    applyColorTransformation(imageName, destImageName, 0.2126, 0.7152, 0.0722, 0.2126, 0.7152,
        0.0722, 0.2126, 0.7152, 0.0722, splitPosition, maskImageName);
  }

  /**
   * Applies a greyscale effect to an image without a mask for compatibility with existing code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the greyscale image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   */
  public void greyscale(String imageName, String destImageName, Integer splitPosition) {
    greyscale(imageName, destImageName, splitPosition, null);
  }
}