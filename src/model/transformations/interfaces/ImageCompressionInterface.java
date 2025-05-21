package model.transformations.interfaces;

/**
 * Interface for compressing images.
 */
public interface ImageCompressionInterface {

  /**
   * Compresses an image by a given percentage.
   *
   * @param imageName        The name of the input image file.
   * @param destImageName    The name of the output (compressed) image file.
   * @param compressionRatio The percentage of compression (0 to 100).
   * @throws IllegalArgumentException if the compression ratio is not between 0 and 100.
   */
  public void compress(double compressionRatio, String imageName, String destImageName);
}
