package model.transformations.interfaces;

/**
 * Helps adjust image brightness and contrast by changing pixel levels.
 */
public interface LevelAdjustInterface {

  /**
   * Changes image levels using black, mid, and white point values.
   *
   * @param b               Black point (0-255)
   * @param m               Midtone value (0-255)
   * @param w               White point (0-255)
   * @param imageName       Source image name
   * @param destImageName   New image name after adjustment
   * @param splitPercentage Where to split image (0-100). Null means whole image
   */
  void levelsAdjust(int b, int m, int w, String imageName, String destImageName,
      Integer splitPercentage);
}