package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.ComponentInterface;

/**
 * Class for visualizing color components of an image with optional mask support.
 */
public class ComponentVisualization implements ComponentInterface {

  /**
   * Extracts and saves the red component of the specified image with optional mask.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the red component image.
   * @param maskImageName the name of the mask image to apply the transformation partially, can be
   *                      null.
   */
  public void redComponent(String imageName, String destImageName, String maskImageName) {
    applyComponentVisualization(imageName, destImageName, 0, maskImageName);
  }

  /**
   * Extracts and saves the red component without mask for compatibility with existing code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the red component image.
   */
  public void redComponent(String imageName, String destImageName) {
    redComponent(imageName, destImageName, null);
  }

  /**
   * Extracts and saves the green component of the specified image with optional mask.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the green component image.
   * @param maskImageName the name of the mask image to apply the transformation partially, can be
   *                      null.
   */
  public void greenComponent(String imageName, String destImageName, String maskImageName) {
    applyComponentVisualization(imageName, destImageName, 1, maskImageName);
  }

  /**
   * Extracts and saves the green component without mask for compatibility with existing code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the green component image.
   */
  public void greenComponent(String imageName, String destImageName) {
    greenComponent(imageName, destImageName, null);
  }

  /**
   * Extracts and saves the blue component of the specified image with optional mask.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the blue component image.
   * @param maskImageName the name of the mask image to apply the transformation partially, can be
   *                      null.
   */
  public void blueComponent(String imageName, String destImageName, String maskImageName) {
    applyComponentVisualization(imageName, destImageName, 2, maskImageName);
  }

  /**
   * Extracts and saves the blue component without mask for compatibility with existing code.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the blue component image.
   */
  public void blueComponent(String imageName, String destImageName) {
    blueComponent(imageName, destImageName, null);
  }

  /**
   * Applies the visualization of the specified color component to a new image with optional mask.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the new image with the component visualization.
   * @param component     the component to visualize (0 for red, 1 for green, 2 for blue).
   * @param maskImageName the name of the mask image to apply the transformation partially, can be
   *                      null.
   */
  private void applyComponentVisualization(String imageName, String destImageName, int component,
      String maskImageName) {
    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Image result = new Image(original.getWidth(), original.getHeight());
    Image mask = maskImageName != null ? opn.getImage(maskImageName) : null;

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        if (mask == null || (mask.getPixel(x, y).getRed() == 0
            && mask.getPixel(x, y).getGreen() == 0 && mask.getPixel(x, y).getBlue() == 0)) {
          // Apply component visualization if no mask or mask pixel is black
          Pixel pixel = original.getPixel(x, y);
          int grayscaleValue;

          if (component == 0) { // Red component
            grayscaleValue = pixel.getRed();
          } else if (component == 1) { // Green component
            grayscaleValue = pixel.getGreen();
          } else { // Blue component
            grayscaleValue = pixel.getBlue();
          }

          // Create a grayscale pixel based on the selected component
          result.setPixel(x, y, new Pixel(grayscaleValue, grayscaleValue, grayscaleValue));
        } else {
          // Retain original pixel if masked
          result.setPixel(x, y, original.getPixel(x, y));
        }
      }
    }

    opn.saveImage(destImageName, result);
  }
}