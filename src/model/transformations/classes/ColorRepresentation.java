package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.ColorRepresentationInterface;

/**
 * This class provides methods to visualize different color components of an image such as value,
 * intensity, and luma.
 */
public class ColorRepresentation implements ColorRepresentationInterface {

  /**
   * Applies the selected color component to the image and creates a new grayscale image based on
   * that component.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   * @param type          the type of color component to apply.
   */
  private static void applyComponent(String imageName, String destImageName, ComponentType type) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        Pixel pixel = original.getPixel(x, y);
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        int componentValue = 0;

        switch (type) {
          case VALUE:
            componentValue = Math.max(r, Math.max(g, b));
            break;
          case INTENSITY:
            componentValue = (r + g + b) / 3;
            break;
          case LUMA:
            componentValue = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
            break;
          default:
            break;
        }

        result.setPixel(x, y, new Pixel(componentValue, componentValue, componentValue));
      }
    }

    opn.saveImage(destImageName, result);
  }

  /**
   * Extracts the value component from the image and creates a new image based on it.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   */
  public void valueComponent(String imageName, String destImageName) {
    applyComponent(imageName, destImageName, ComponentType.VALUE);
  }

  /**
   * Extracts the intensity component from the image and creates a new image based on it.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   */
  public void intensityComponent(String imageName, String destImageName) {
    applyComponent(imageName, destImageName, ComponentType.INTENSITY);
  }

  /**
   * Extracts the luma component from the image and creates a new image based on it.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   */
  public void lumaComponent(String imageName, String destImageName) {
    applyComponent(imageName, destImageName, ComponentType.LUMA);
  }

  /**
   * Enum to represent the different types of color components.
   */
  public enum ComponentType {
    VALUE, INTENSITY, LUMA
  }

}
