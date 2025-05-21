package model.transformations.interfaces;

/**
 * This interface defines methods for applying different color components to an image such as value,
 * intensity, and luma. The implementing class will provide the actual functionality for these
 * methods.
 */
public interface ColorRepresentationInterface {

  /**
   * Applies the value component to the image and creates a new image based on it.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   */
  public void valueComponent(String imageName, String destImageName);

  /**
   * Applies the intensity component to the image and creates a new image based on it.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   */
  public void intensityComponent(String imageName, String destImageName);

  /**
   * Applies the luma component to the image and creates a new image based on it.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the resulting image as.
   */
  public void lumaComponent(String imageName, String destImageName);

}
