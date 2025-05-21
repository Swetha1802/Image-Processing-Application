package model.transformations.interfaces;

/**
 * This interface defines methods for splitting images into color components.
 */
public interface SplitInterface {

  /**
   * Splits an image into its red, green, and blue components.
   *
   * @param imageName The name of the original image to split.
   * @param redName   The name for the new image that will contain only red.
   * @param greenName The name for the new image that will contain only green.
   * @param blueName  The name for the new image that will contain only blue.
   */
  public void rgbSplit(String imageName, String redName, String greenName, String blueName);
}
