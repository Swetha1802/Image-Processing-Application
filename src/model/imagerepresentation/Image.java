package model.imagerepresentation;

/**
 * The Image class represents an image with a grid of pixels. Each pixel can have a red, green, and
 * blue value. The image has a width and height that define its size.
 */
public class Image {

  private Pixel[][] pixels;
  private int width;
  private int height;

  /**
   * Constructs an Image object with the given width and height. Initializes all pixels to black (0,
   * 0, 0).
   *
   * @param width  The width of the image.
   * @param height The height of the image.
   */
  public Image(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive");
    }
    this.width = width;
    this.height = height;
    pixels = new Pixel[height][width];
    initializePixels();
  }

  /**
   * Initializes all pixels in the image to the color black (0, 0, 0).
   */
  private void initializePixels() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[y][x] = new Pixel(0, 0, 0);
      }
    }
  }

  /**
   * Returns the pixel at the given (x, y) position.
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @return The pixel at the given coordinates.
   */
  public Pixel getPixel(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new ArrayIndexOutOfBoundsException("Index out of bounds for pixel access");
    }
    return pixels[y][x];
  }

  /**
   * Sets the pixel at the given (x, y) position to the given pixel.
   *
   * @param x     The x-coordinate of the pixel.
   * @param y     The y-coordinate of the pixel.
   * @param pixel The new pixel to set at the given coordinates.
   */
  public void setPixel(int x, int y, Pixel pixel) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new ArrayIndexOutOfBoundsException("Index out of bounds for pixel setting");
    }
    if (pixel == null) {
      throw new IllegalArgumentException("Pixel cannot be null");
    }
    pixels[y][x] = pixel;
  }

  /**
   * Returns the width of the image.
   *
   * @return The width of the image.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the image.
   *
   * @return The height of the image.
   */
  public int getHeight() {
    return height;
  }

}