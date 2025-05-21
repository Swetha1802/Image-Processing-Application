package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.BrightenDarkenInterface;

/**
 * This class handles operations for brightening and darkening an image. It adjusts the brightness
 * by increasing or decreasing the pixel values.
 */
public class BrightenDarken implements BrightenDarkenInterface {

  /**
   * Adjusts the brightness of an image.
   *
   * @param amount        the amount to adjust the brightness (positive for brighten, negative for
   *                      darken).
   * @param imageName     the name of the image to adjust.
   * @param destImageName the name to save the adjusted image as.
   * @param isBrighten    true to brighten the image, false to darken it.
   */
  private static void adjustBrightness(int amount, String imageName, String destImageName,
      boolean isBrighten) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        Pixel pixel = original.getPixel(x, y);
        int adjustment = isBrighten ? amount : -amount;

        Pixel newPixel = new Pixel(pixel.getRed() + adjustment, pixel.getGreen() + adjustment,
            pixel.getBlue() + adjustment);

        result.setPixel(x, y, newPixel);
      }
    }

    opn.saveImage(destImageName, result);
  }


  /**
   * Brightens an image by a specified amount.
   *
   * @param increment     the amount to increase the brightness of each pixel.
   * @param imageName     the name of the image to brighten.
   * @param destImageName the name to save the brightened image as.
   */
  public void brighten(int increment, String imageName, String destImageName) {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("Image names cannot be null.");
    }
    adjustBrightness(increment, imageName, destImageName, true);
  }

  /**
   * Darkens an image by a specified amount.
   *
   * @param decrement     the amount to decrease the brightness of each pixel.
   * @param imageName     the name of the image to darken.
   * @param destImageName the name to save the darkened image as.
   */
  public void darken(int decrement, String imageName, String destImageName) {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("Image names cannot be null.");
    }
    adjustBrightness(decrement, imageName, destImageName, false);
  }

}

