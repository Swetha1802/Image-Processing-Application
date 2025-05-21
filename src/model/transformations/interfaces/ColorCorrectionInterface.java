package model.transformations.interfaces;

/**
 * Interface for color correction operations on images. This interface defines the method for
 * applying color correction to an image.
 */
public interface ColorCorrectionInterface {

  /**
   * Applies color correction to the specified image and saves the result.
   *
   * @param imageName     the name of the original image.
   * @param destImageName the name to save the color-corrected image.
   * @param splitPosition the percentage (0-100) where the split should occur. If 100, no split is
   *                      applied.
   * @throws IllegalArgumentException if the splitPosition is outside the valid range of 0 to 100.
   */
  void colorCorrect(String imageName, String destImageName, Integer splitPosition);
}