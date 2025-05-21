package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.ImageDownsizing;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit test for ImageDownSizing class.
 */
public class ImageDownSizingTest {

  private ImageOperations operations;

  @Before
  public void setUp() {
    operations = new ImageOperations();
  }

  @Test
  public void testDownsizeValidImage() {
    Image original = createSampleImage(4, 4);
    operations.saveImage("validImage", original);

    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("validImage", "downsizedValidImage", 2, 2);

    Image downsized = operations.getImage("downsizedValidImage");

    assertPixelEquals(new Pixel(0, 0, 0), downsized.getPixel(0, 0));
    assertPixelEquals(new Pixel(126, 126, 126), downsized.getPixel(1, 0));
    assertPixelEquals(new Pixel(126, 126, 126), downsized.getPixel(0, 1));
    assertPixelEquals(new Pixel(252, 252, 252), downsized.getPixel(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeInvalidImage() {
    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("invalidImage", "downsizedInvalidImage", 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeNullImage() {
    operations.saveImage("corruptedImage", null);
    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("corruptedImage", "downsizedCorruptedImage", 2, 2);
  }

  @Test
  public void testDownsizeToSmallerImage() {
    Image originalImage = createSampleImage(4, 4);
    operations.saveImage("originalImage", originalImage);

    ImageDownsizing imageDownsizing = new ImageDownsizing();
    imageDownsizing.downsize("originalImage", "downsizedImage", 2, 2);

    Image downsizedImage = operations.getImage("downsizedImage");

    assertPixelEquals(new Pixel(0, 0, 0), downsizedImage.getPixel(0, 0));
    assertPixelEquals(new Pixel(126, 126, 126), downsizedImage.getPixel(1, 0));
    assertPixelEquals(new Pixel(126, 126, 126), downsizedImage.getPixel(0, 1));
    assertPixelEquals(new Pixel(252, 252, 252), downsizedImage.getPixel(1, 1));
  }

  @Test
  public void testDownsizeToSameSize() {
    Image originalImage = createSampleImage(4, 4);
    operations.saveImage("originalImage", originalImage);

    ImageDownsizing imageDownsizing = new ImageDownsizing();
    imageDownsizing.downsize("originalImage", "downsizedImage", 4, 4);

    Image downsizedImage = operations.getImage("downsizedImage");

    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        assertPixelEquals(originalImage.getPixel(x, y), downsizedImage.getPixel(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpscalingThrowsException() {
    ImageOperations opn = new ImageOperations();
    Image original = new Image(4, 4);
    opn.saveImage("original_image.png", original);

    ImageDownsizing downsizing = new ImageDownsizing();

    downsizing.downsize("original_image.png", "upscaled_image.png", 6, 6);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeWithInvalidDimensions() {
    Image originalImage = createSampleImage(4, 4);
    operations.saveImage("originalImage", originalImage);

    ImageDownsizing imageDownsizing = new ImageDownsizing();
    imageDownsizing.downsize("originalImage", "invalidImage", -1, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeWithNonExistentImage() {
    ImageDownsizing imageDownsizing = new ImageDownsizing();
    imageDownsizing.downsize("nonExistentImage", "outputImage", 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeInvalidDimensionsNegativeWidth() {
    Image original = createSampleImage(4, 4);
    operations.saveImage("original", original);

    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("original", "invalid", -1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeInvalidDimensionsNegativeHeight() {
    Image original = createSampleImage(4, 4);
    operations.saveImage("original", original);

    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("original", "invalid", 4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeZeroWidth() {
    Image original = createSampleImage(4, 4);
    operations.saveImage("original", original);

    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("original", "invalid", 0, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownsizeZeroHeight() {
    Image original = createSampleImage(4, 4);
    operations.saveImage("original", original);

    ImageDownsizing downsizing = new ImageDownsizing();
    downsizing.downsize("original", "invalid", 4, 0);
  }


  private Image createSampleImage(int width, int height) {
    Image image = new Image(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int value = (x + y) * 63;
        image.setPixel(x, y, new Pixel(value, value, value));
      }
    }
    return image;
  }

  private void assertPixelEquals(Pixel expected, Pixel actual) {
    assertEquals(expected.getRed(), actual.getRed());
    assertEquals(expected.getGreen(), actual.getGreen());
    assertEquals(expected.getBlue(), actual.getBlue());
  }

}