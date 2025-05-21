package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.CombineChannelsInterface;

/**
 * Class for combining RGB channels into a single image.
 */
public class CombineChannels implements CombineChannelsInterface {

  /**
   * Combines red, green, and blue images into one RGB image.
   *
   * @param destImageName  the name to save the combined RGB image.
   * @param redImageName   the name of the red channel image.
   * @param greenImageName the name of the green channel image.
   * @param blueImageName  the name of the blue channel image.
   */
  public void rgbCombine(String destImageName, String redImageName, String greenImageName,
      String blueImageName) {
    ImageOperations opn = new ImageOperations();
    Image redImage = opn.getImage(redImageName);
    Image greenImage = opn.getImage(greenImageName);
    Image blueImage = opn.getImage(blueImageName);

    // Check for size equality
    if (redImage.getWidth() != greenImage.getWidth()
        || redImage.getHeight() != greenImage.getHeight()
        || redImage.getWidth() != blueImage.getWidth()
        || redImage.getHeight() != blueImage.getHeight()) {
      throw new IllegalArgumentException("All images must have the same dimensions.");
    }

    Image result = new Image(redImage.getWidth(), redImage.getHeight());

    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        int r = redImage.getPixel(x, y).getRed();
        int g = greenImage.getPixel(x, y).getGreen();
        int b = blueImage.getPixel(x, y).getBlue();
        result.setPixel(x, y, new Pixel(r, g, b));
      }
    }

    opn.saveImage(destImageName, result);
  }
}
