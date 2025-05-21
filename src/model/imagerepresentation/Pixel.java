package model.imagerepresentation;

/**
 * This class represents a pixel with red, green, and blue color components. Each color component is
 * clamped to be within the range of 0 to 255.
 */
public class Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * Creates a Pixel object with the given red, green, and blue values. The values are clamped to
   * make sure they are between 0 and 255.
   *
   * @param red   The red color component.
   * @param green The green color component.
   * @param blue  The blue color component.
   */
  public Pixel(int red, int green, int blue) {
    this.red = clamp(red);
    this.green = clamp(green);
    this.blue = clamp(blue);
  }

  /**
   * Clamps the value to make sure it is between 0 and 255.
   *
   * @param value The value to be clamped.
   * @return The clamped value between 0 and 255.
   */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

  /**
   * Gets the red component of the pixel.
   *
   * @return The red component.
   */
  public int getRed() {
    return red;
  }

  /**
   * Sets the red component of the pixel. The value is clamped to make sure it is between 0 and
   * 255.
   *
   * @param red The new red value.
   */
  public void setRed(int red) {
    this.red = clamp(red);
  }

  /**
   * Gets the green component of the pixel.
   *
   * @return The green component.
   */
  public int getGreen() {
    return green;
  }

  /**
   * Sets the green component of the pixel. The value is clamped to make sure it is between 0 and
   * 255.
   *
   * @param green The new green value.
   */
  public void setGreen(int green) {
    this.green = clamp(green);
  }

  /**
   * Gets the blue component of the pixel.
   *
   * @return The blue component.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Sets the blue component of the pixel. The value is clamped to make sure it is between 0 and
   * 255.
   *
   * @param blue The new blue value.
   */
  public void setBlue(int blue) {
    this.blue = clamp(blue);
  }
}
