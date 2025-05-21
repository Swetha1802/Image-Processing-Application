package model.transformations.interfaces;

/**
 * Defines operations for extracting individual color components from images. Allows separation of
 * red, green, and blue color channels.
 */
public interface ComponentInterface {

  /**
   * Extracts and creates a new image containing only the red color channel. Keeps red intensity,
   * sets green and blue to zero.
   *
   * @param imageName     Name of the original image to extract red component from
   * @param destImageName Name for the new red component image
   */
  public void redComponent(String imageName, String destImageName);

  /**
   * Extracts red component with an optional mask image. Allows selective red component extraction
   * using a mask.
   *
   * @param imageName     Name of the original image
   * @param destImageName Name for the new red component image
   * @param maskImageName Name of image used as an extraction mask
   */
  public void redComponent(String imageName, String destImageName, String maskImageName);

  /**
   * Extracts and creates a new image containing only the green color channel. Keeps green
   * intensity, sets red and blue to zero.
   *
   * @param imageName     Name of the original image to extract green component from
   * @param destImageName Name for the new green component image
   */
  public void greenComponent(String imageName, String destImageName);

  /**
   * Extracts green component with an optional mask image. Allows selective green component
   * extraction using a mask.
   *
   * @param imageName     Name of the original image
   * @param destImageName Name for the new green component image
   * @param maskImageName Name of image used as an extraction mask
   */
  public void greenComponent(String imageName, String destImageName, String maskImageName);

  /**
   * Extracts and creates a new image containing only the blue color channel. Keeps blue intensity,
   * sets red and green to zero.
   *
   * @param imageName     Name of the original image to extract blue component from
   * @param destImageName Name for the new blue component image
   */
  public void blueComponent(String imageName, String destImageName);


  /**
   * Extracts blue component with an optional mask image. Allows selective blue component extraction
   * using a mask.
   *
   * @param imageName     Name of the original image
   * @param destImageName Name for the new blue component image
   * @param maskImageName Name of image used as an extraction mask
   */
  public void blueComponent(String imageName, String destImageName, String maskImageName);
}