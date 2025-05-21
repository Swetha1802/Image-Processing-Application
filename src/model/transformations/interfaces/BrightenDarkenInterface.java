package model.transformations.interfaces;

/**
 * This interface defines the methods for brightening and darkening an image. The implementations of
 * these methods will adjust the brightness of the image.
 */
public interface BrightenDarkenInterface {

  /**
   * Brightens an image by increasing the brightness of each pixel.
   *
   * @param increment     the amount to increase the brightness of each pixel.
   * @param imageName     the name of the image to brighten.
   * @param destImageName the name to save the brightened image as.
   */
  public void brighten(int increment, String imageName, String destImageName);

  /**
   * Darkens an image by decreasing the brightness of each pixel.
   *
   * @param decrement     the amount to decrease the brightness of each pixel.
   * @param imageName     the name of the image to darken.
   * @param destImageName the name to save the darkened image as.
   */
  public void darken(int decrement, String imageName, String destImageName);
  // Method implementation


}
