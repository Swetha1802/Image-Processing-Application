package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.transformations.interfaces.FlippingInterface;

/**
 * This class has methods to flip images.
 */
public class Flipping implements FlippingInterface {

  /**
   * Flips the image either horizontally or vertically.
   *
   * @param imageName      The name of the image to flip.
   * @param destImageName  The name for the new flipped image.
   * @param horizontalFlip True to flip horizontally, false to flip vertically.
   */
  private static void applyFlip(String imageName, String destImageName, boolean horizontalFlip) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        if (horizontalFlip) {
          // Set pixel for horizontal flip
          result.setPixel(original.getWidth() - 1 - x, y, original.getPixel(x, y));
        } else {
          // Set pixel for vertical flip
          result.setPixel(x, original.getHeight() - 1 - y, original.getPixel(x, y));
        }
      }
    }

    // Save the flipped image
    opn.saveImage(destImageName, result);
  }

  /**
   * Flips the image horizontally.
   *
   * @param imageName     The name of the image to flip.
   * @param destImageName The name for the new flipped image.
   */
  public void horizontalFlip(String imageName, String destImageName) {
    if (imageName == null) {
      throw new IllegalArgumentException("Source image name cannot be null");
    }
    if (destImageName == null) {
      throw new IllegalArgumentException("Destination image name cannot be null");
    }
    applyFlip(imageName, destImageName, true);
  }

  /**
   * Flips the image vertically.
   *
   * @param imageName     The name of the image to flip.
   * @param destImageName The name for the new flipped image.
   */
  public void verticalFlip(String imageName, String destImageName) {
    if (imageName == null) {
      throw new IllegalArgumentException("Source image name cannot be null");
    }
    if (destImageName == null) {
      throw new IllegalArgumentException("Destination image name cannot be null");
    }
    applyFlip(imageName, destImageName, false);
  }
}
