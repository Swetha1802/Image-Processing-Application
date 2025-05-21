package model.transformations.interfaces;

/**
 * This interface defines methods for flipping images.
 */
public interface FlippingInterface {

  /**
   * Flips the image horizontally.
   *
   * @param imageName     The name of the image to flip.
   * @param destImageName The name for the new flipped image.
   */
  public void horizontalFlip(String imageName, String destImageName);

  /**
   * Flips the image vertically.
   *
   * @param imageName     The name of the image to flip.
   * @param destImageName The name for the new flipped image.
   */
  public void verticalFlip(String imageName, String destImageName);
}
