package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.ImageCompression;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit4 test for ImageCompression Class.
 */
public class ImageCompressionTest {

  private ImageCompression imageCompression;

  @Before
  public void setUp() {
    imageCompression = new ImageCompression();
  }

  @Test
  public void testCompressWith2x2Image() {
    Image original = new Image(2, 2);
    original.setPixel(0, 0, new Pixel(100, 150, 200));
    original.setPixel(1, 0, new Pixel(120, 170, 210));
    original.setPixel(0, 1, new Pixel(140, 190, 240));
    original.setPixel(1, 1, new Pixel(160, 210, 255));

    String inputImageName = "input_image.jpg";
    String outputImageName = "compressed_image.jpg";

    ImageOperations imageOperations = new ImageOperations();
    imageOperations.saveImage(inputImageName, original);

    imageCompression.compress(50.0, inputImageName, outputImageName);

    Image compressedImage = imageOperations.getImage(outputImageName);

    assertPixelEquals(new Pixel(110, 160, 205), compressedImage.getPixel(0, 0));
    assertPixelEquals(new Pixel(110, 160, 205), compressedImage.getPixel(1, 0));
    assertPixelEquals(new Pixel(150, 200, 248), compressedImage.getPixel(0, 1));
    assertPixelEquals(new Pixel(150, 200, 248), compressedImage.getPixel(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressWithInvalidCompressionRatio() {
    Image original = new Image(2, 2);
    original.setPixel(0, 0, new Pixel(100, 100, 100));
    original.setPixel(1, 0, new Pixel(120, 120, 120));
    original.setPixel(0, 1, new Pixel(140, 140, 140));
    original.setPixel(1, 1, new Pixel(160, 160, 160));

    String inputImageName = "test_image_invalid_ratio.jpg";
    String outputImageName = "compressed_image_invalid_ratio.jpg";

    ImageOperations imageOperations = new ImageOperations();
    imageOperations.saveImage(inputImageName, original);

    imageCompression.compress(-10.0, inputImageName, outputImageName);
  }

  @Test
  public void testCompressWithZeroPercentCompression() {
    Image original = new Image(2, 2);
    original.setPixel(0, 0, new Pixel(100, 150, 200));
    original.setPixel(1, 0, new Pixel(120, 170, 210));
    original.setPixel(0, 1, new Pixel(140, 190, 240));
    original.setPixel(1, 1, new Pixel(160, 210, 255));

    String inputImageName = "input_image_zero.jpg";
    String outputImageName = "compressed_image_zero.jpg";
    ImageOperations imageOperations = new ImageOperations();
    imageOperations.saveImage(inputImageName, original);

    imageCompression.compress(0.0, inputImageName, outputImageName);

    Image compressedImage = imageOperations.getImage(outputImageName);

    assertPixelEquals(new Pixel(100, 150, 200), compressedImage.getPixel(0, 0));
    assertPixelEquals(new Pixel(120, 170, 210), compressedImage.getPixel(1, 0));
    assertPixelEquals(new Pixel(140, 190, 240), compressedImage.getPixel(0, 1));
    assertPixelEquals(new Pixel(160, 210, 255), compressedImage.getPixel(1, 1));
  }

  @Test
  public void testCompressWithFiftyPercentCompression() {
    Image original = new Image(2, 2);
    original.setPixel(0, 0, new Pixel(100, 150, 200));
    original.setPixel(1, 0, new Pixel(120, 170, 210));
    original.setPixel(0, 1, new Pixel(140, 190, 240));
    original.setPixel(1, 1, new Pixel(160, 210, 255));

    String inputImageName = "input_image_fifty.jpg";
    String outputImageName = "compressed_image_fifty.jpg";
    ImageOperations imageOperations = new ImageOperations();
    imageOperations.saveImage(inputImageName, original);

    imageCompression.compress(50.0, inputImageName, outputImageName);

    Image compressedImage = imageOperations.getImage(outputImageName);

    assertPixelEquals(new Pixel(110, 160, 205), compressedImage.getPixel(0, 0));
    assertPixelEquals(new Pixel(110, 160, 205), compressedImage.getPixel(1, 0));
    assertPixelEquals(new Pixel(150, 200, 248), compressedImage.getPixel(0, 1));
    assertPixelEquals(new Pixel(150, 200, 248), compressedImage.getPixel(1, 1));
  }

  @Test
  public void testCompressWithSeventyPercentCompression() {
    Image original = new Image(2, 2);
    original.setPixel(0, 0, new Pixel(100, 150, 200));
    original.setPixel(1, 0, new Pixel(120, 170, 210));
    original.setPixel(0, 1, new Pixel(140, 190, 240));
    original.setPixel(1, 1, new Pixel(160, 210, 255));

    String inputImageName = "input_image_seventy.jpg";
    String outputImageName = "compressed_image_seventy.jpg";
    ImageOperations imageOperations = new ImageOperations();
    imageOperations.saveImage(inputImageName, original);

    imageCompression.compress(70.0, inputImageName, outputImageName);

    Image compressedImage = imageOperations.getImage(outputImageName);

    assertPixelEquals(new Pixel(130, 180, 205), compressedImage.getPixel(0, 0));
    assertPixelEquals(new Pixel(130, 180, 205), compressedImage.getPixel(1, 0));
    assertPixelEquals(new Pixel(130, 180, 248), compressedImage.getPixel(0, 1));
    assertPixelEquals(new Pixel(130, 180, 248), compressedImage.getPixel(1, 1));
  }

  private void assertPixelEquals(Pixel expected, Pixel actual) {
    assertEquals("Red channel mismatch", expected.getRed(), actual.getRed());
    assertEquals("Green channel mismatch", expected.getGreen(), actual.getGreen());
    assertEquals("Blue channel mismatch", expected.getBlue(), actual.getBlue());
  }
}
