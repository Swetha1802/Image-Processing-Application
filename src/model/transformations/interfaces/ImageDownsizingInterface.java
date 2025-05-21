package model.transformations.interfaces;

/**
 * The ImageDownsizingInterface defines the operations for downscaling an image.
 */
public interface ImageDownsizingInterface {

  /**
   * Downscale an image to the target dimensions.
   *
   * @param imageName     The name of the original image.
   * @param destImageName The name of the downsized image to save.
   * @param targetWidth   The target width of the downsized image.
   * @param targetHeight  The target height of the downsized image.
   */
  void downsize(String imageName, String destImageName, int targetWidth, int targetHeight);
}
