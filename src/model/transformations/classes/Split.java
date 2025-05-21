package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.SplitInterface;

/**
 * This class handles splitting an image into its RGB components.
 */
public class Split implements SplitInterface {

  /**
   * Splits an image into red, green, and blue components.
   *
   * @param imageName The name of the original image to split.
   * @param redName   The name for the new red image.
   * @param greenName The name for the new green image.
   * @param blueName  The name for the new blue image.
   */
  public void rgbSplit(String imageName, String redName, String greenName, String blueName) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image redImage = new Image(original.getWidth(), original.getHeight());
    Image greenImage = new Image(original.getWidth(), original.getHeight());
    Image blueImage = new Image(original.getWidth(), original.getHeight());

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        Pixel pixel = original.getPixel(x, y);

        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();

        redImage.setPixel(x, y, new Pixel(r, r, r));
        greenImage.setPixel(x, y, new Pixel(g, g, g));
        blueImage.setPixel(x, y, new Pixel(b, b, b));
      }
    }

    opn.saveImage(redName, redImage);
    opn.saveImage(greenName, greenImage);
    opn.saveImage(blueName, blueImage);
  }
}
