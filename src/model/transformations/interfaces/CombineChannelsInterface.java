package model.transformations.interfaces;

/**
 * Interface for combining color channels into a single image.
 */
public interface CombineChannelsInterface {

  /**
   * Combines red, green, and blue channel images into one RGB image.
   *
   * @param destImageName  the name to save the combined RGB image.
   * @param redImageName   the name of the red channel image.
   * @param greenImageName the name of the green channel image.
   * @param blueImageName  the name of the blue channel image.
   */
  public void rgbCombine(String destImageName, String redImageName, String greenImageName,
      String blueImageName);
}
