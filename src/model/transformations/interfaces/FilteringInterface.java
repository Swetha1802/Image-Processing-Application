package model.transformations.interfaces;

/**
 * Defines operations for applying image filtering techniques. Supports blur and sharpen effects
 * with optional masking and split positioning.
 */
public interface FilteringInterface {

  /**
   * Applies a blur effect to an image with optional masking. Softens image details by averaging
   * neighboring pixel values.
   *
   * @param imageName     Name of the original image to blur
   * @param destImageName Name for the new blurred image
   * @param splitPosition Percentage of image to apply blur (0-100)
   * @param maskImageName Name of image used as a blur mask
   */
  public void blur(String imageName, String destImageName, Integer splitPosition,
      String maskImageName);

  /**
   * Applies blur effect to an entire image. Softens image details without using a mask.
   *
   * @param imageName     Name of the original image to blur
   * @param destImageName Name for the new blurred image
   * @param splitPosition Percentage of image to apply blur
   */
  public void blur(String imageName, String destImageName, Integer splitPosition);

  /**
   * Applies a sharpen effect to an image with optional masking. Enhances image details by
   * increasing pixel edge contrast.
   *
   * @param imageName     Name of the original image to sharpen
   * @param destImageName Name for the new sharpened image
   * @param splitPosition Percentage of image to apply sharpening (0-100)
   * @param maskImageName Name of image used as a sharpening mask
   */
  public void sharpen(String imageName, String destImageName, Integer splitPosition,
      String maskImageName);

  /**
   * Applies sharpen effect to an entire image. Enhances image details without using a mask.
   *
   * @param imageName     Name of the original image to sharpen
   * @param destImageName Name for the new sharpened image
   * @param splitPosition Percentage of image to apply sharpening
   */
  public void sharpen(String imageName, String destImageName, Integer splitPosition);


}