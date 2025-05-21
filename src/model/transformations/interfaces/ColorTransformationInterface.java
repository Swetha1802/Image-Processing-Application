package model.transformations.interfaces;

/**
 * Defines operations for transforming image colors. Allows applying sepia tone and greyscale
 * effects to images. Supports partial image transformations using split positions.
 */
public interface ColorTransformationInterface {

  /**
   * Applies a sepia tone effect to an image. Converts image colors to warm brownish-golden tones.
   *
   * @param imageName     Name of the original image to transform
   * @param destImageName Name for the new sepia-toned image
   * @param splitPosition Percentage of image to apply sepia (0-100)
   */
  public void sepia(String imageName, String destImageName, Integer splitPosition);

  /**
   * Applies sepia tone with an optional mask image. Allows selective sepia application using a
   * mask.
   *
   * @param imageName     Name of the original image
   * @param destImageName Name for the new sepia-toned image
   * @param splitPosition Percentage of image to apply sepia
   * @param maskImageName Name of image used as a transformation mask
   */
  public void sepia(String imageName, String destImageName, Integer splitPosition,
      String maskImageName);

  /**
   * Converts an image to greyscale. Removes color information, keeping only brightness levels.
   *
   * @param imageName     Name of the original image to convert
   * @param destImageName Name for the new greyscale image
   * @param splitPosition Percentage of image to convert (0-100)
   */
  public void greyscale(String imageName, String destImageName, Integer splitPosition);


  /**
   * Converts image to greyscale with an optional mask image. Enables selective greyscale conversion
   * using a mask.
   *
   * @param imageName     Name of the original image
   * @param destImageName Name for the new greyscale image
   * @param splitPosition Percentage of image to convert
   * @param maskImageName Name of image used as a transformation mask
   */
  public void greyscale(String imageName, String destImageName, Integer splitPosition,
      String maskImageName);
}