package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.ColorTransformation;
import model.transformations.interfaces.ColorTransformationInterface;
import org.junit.Test;

/**
 * Test class for testing the color transformation functionalities such as Sepia and Greyscale on an
 * image. This class includes unit tests to verify that the transformation methods work as expected
 * on different image types.
 */


public class ColorTransformationTest {


  ImageOperations opn = new ImageOperations();
  ColorTransformationInterface clr = new ColorTransformation();


  @Test
  public void testSepiaTransformation() {
    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 100, 100)); // Add a simple pixel
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150)); // Another pixel
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200)); // Another pixel
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250)); // Another pixel

    opn.saveImage("original_image.png", originalImage);

    clr.sepia("original_image.png", "sepia_output.png", null);
    Image sepiaImage = opn.getImage("sepia_output.png");

    Pixel pixel = sepiaImage.getPixel(0, 0);
    assertEquals(135, pixel.getRed());
    assertEquals(120, pixel.getGreen());
    assertEquals(93, pixel.getBlue());
  }


  @Test
  public void testGreyscaleTransformation() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 100, 100)); // Add a simple pixel
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150)); // Another pixel
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200)); // Another pixel
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250)); // Another pixel

    opn.saveImage("original_image.png", originalImage);

    clr.greyscale("original_image.png", "sepia_output.png", null);
    Image greyscaleImage = opn.getImage("sepia_output.png");

    Pixel pixel = greyscaleImage.getPixel(1, 1);
    assertEquals(250, pixel.getRed());
    assertEquals(250, pixel.getGreen());
    assertEquals(250, pixel.getBlue());
  }

  @Test
  public void testMaxRGB() {

    Image maxImage = new Image(2, 2);
    maxImage.setPixel(0, 0, new Pixel(255, 255, 255));
    opn.saveImage("max_rgb_image.png", maxImage);

    clr.greyscale("max_rgb_image.png", "greyscale_max_output.png", null);
    Image greyscaleMaxImage = opn.getImage("greyscale_max_output.png");

    Pixel pixel = greyscaleMaxImage.getPixel(0, 0);

    int expectedValue = 255;
    int actualRed = pixel.getRed();
    int actualGreen = pixel.getGreen();
    int actualBlue = pixel.getBlue();

    int tolerance = 1;

    assertEquals(expectedValue, actualRed, tolerance);
    assertEquals(expectedValue, actualGreen, tolerance);
    assertEquals(expectedValue, actualBlue, tolerance);
  }

  @Test
  public void testMinRGB() {

    Image minImage = new Image(2, 2);
    minImage.setPixel(0, 0, new Pixel(0, 0, 0));
    opn.saveImage("min_rgb_image.png", minImage);

    clr.sepia("min_rgb_image.png", "sepia_min_output.png", null);
    Image sepiaMinImage = opn.getImage("sepia_min_output.png");

    Pixel pixel = sepiaMinImage.getPixel(0, 0);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }


  @Test
  public void testNegativePixelValuesClamping() {
    Image negativeImage = new Image(2, 2);
    negativeImage.setPixel(0, 0, new Pixel(-50, -100, -200));
    opn.saveImage("negative_image.png", negativeImage);

    clr.sepia("negative_image.png", "sepia_negative_output.png", null);
    Image sepiaNegativeImage = opn.getImage("sepia_negative_output.png");

    Pixel pixel = sepiaNegativeImage.getPixel(0, 0);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }

  @Test
  public void testNegativePixelValuesInGreyscale() {

    Image negativeGreyscaleImage = new Image(2, 2);
    negativeGreyscaleImage.setPixel(0, 0, new Pixel(-30, -60, -90));
    opn.saveImage("negative_greyscale_image.png", negativeGreyscaleImage);

    clr.greyscale("negative_greyscale_image.png", "greyscale_negative_output.png", null);
    Image greyscaleNegativeImage = opn.getImage("greyscale_negative_output.png");

    Pixel pixel = greyscaleNegativeImage.getPixel(0, 0);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullImageName() {
    clr.sepia(null, "output.png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonExistentImage() {
    clr.sepia("non_existent_image.png", "output.png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidImageDimensions() {
    Image invalidImage = new Image(-1, -1);
    opn.saveImage("invalid_image.png", invalidImage);
    clr.sepia("invalid_image.png", "output.png", null);
  }


  @Test
  public void testBlackImage() {
    Image blackImage = new Image(3, 3);
    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        blackImage.setPixel(x, y, new Pixel(0, 0, 0));
      }
    }
    opn.saveImage("black_image.png", blackImage);

    clr.greyscale("black_image.png", "greyscale_black_output.png", null);
    Image greyscaleBlackImage = opn.getImage("greyscale_black_output.png");

    Pixel pixel = greyscaleBlackImage.getPixel(0, 0);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }

  @Test
  public void testNonStandardDimensions() {

    Image nonStandardImage = new Image(4, 2);
    nonStandardImage.setPixel(0, 0, new Pixel(255, 0, 0)); // Red
    nonStandardImage.setPixel(1, 0, new Pixel(0, 255, 0)); // Green
    nonStandardImage.setPixel(2, 0, new Pixel(0, 0, 255)); // Blue
    nonStandardImage.setPixel(3, 0, new Pixel(255, 255, 255)); // White
    opn.saveImage("non_standard_image.png", nonStandardImage);

    clr.greyscale("non_standard_image.png", "non_standard_grayscale_output.png", null);
    Image grayscaleImage = opn.getImage("non_standard_grayscale_output.png");

    assertEquals(nonStandardImage.getWidth(), grayscaleImage.getWidth());
    assertEquals(nonStandardImage.getHeight(), grayscaleImage.getHeight());
  }

  private void assertPixelEquals(Pixel expected, Pixel actual) {
    assertEquals(expected.getRed(), actual.getRed());
    assertEquals(expected.getGreen(), actual.getGreen());
    assertEquals(expected.getBlue(), actual.getBlue());
  }

  @Test
  public void testSepiaWithSplit2x2() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(0, 1, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(250, 250, 250));

    opn.saveImage("test_sepia_with_split_2x2.png", testImage);

    clr.sepia("test_sepia_with_split_2x2.png", "test_output_sepia_with_split_2x2.png", 50);

    Image result = opn.getImage("test_output_sepia_with_split_2x2.png");

    assertPixelEquals(new Pixel(135, 120, 93), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(270, 240, 187), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(250, 250, 250), result.getPixel(1, 1));
  }

  @Test
  public void testSepiaWithoutSplit2x2() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(0, 1, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(250, 250, 250));

    opn.saveImage("test_sepia_without_split_2x2.png", testImage);

    clr.sepia("test_sepia_without_split_2x2.png", "test_output_sepia_without_split_2x2.png", 100);

    Image result = opn.getImage("test_output_sepia_without_split_2x2.png");

    assertPixelEquals(new Pixel(135, 120, 93), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(202, 180, 140), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(270, 240, 187), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(337, 300, 234), result.getPixel(1, 1));
  }

  @Test
  public void testGreyscaleWithSplit2x2() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(0, 1, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(250, 250, 250));

    opn.saveImage("test_greyscale_with_split_2x2.png", testImage);

    clr.greyscale("test_greyscale_with_split_2x2.png", "test_output_greyscale_with_split_2x2.png",
        50);

    Image result = opn.getImage("test_output_greyscale_with_split_2x2.png");

    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(250, 250, 250), result.getPixel(1, 1));
  }

  @Test
  public void testGreyscaleWithoutSplit2x2() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(0, 1, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(250, 250, 250));

    opn.saveImage("test_greyscale_without_split_2x2.png", testImage);

    clr.greyscale("test_greyscale_without_split_2x2.png",
        "test_output_greyscale_without_split_2x2.png", 100);

    Image result = opn.getImage("test_output_greyscale_without_split_2x2.png");

    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(250, 250, 250), result.getPixel(1, 1));
  }

  @Test
  public void testSepiaWithSplitAt0() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_sepia_split_0.png", testImage);

    clr.sepia("test_input_sepia_split_0.png", "test_output_sepia_split_0.png", 0);

    Image result = opn.getImage("test_output_sepia_split_0.png");

    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 1));
  }

  @Test
  public void testSepiaWithSplitAt50() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_sepia_split_50.png", testImage);

    clr.sepia("test_input_sepia_split_50.png", "test_output_sepia_split_50.png", 50);

    Image result = opn.getImage("test_output_sepia_split_50.png");

    assertPixelEquals(new Pixel(67, 60, 46), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(135, 120, 93), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 1));
  }

  @Test
  public void testSepiaWithSplitAt100() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_sepia_split_100.png", testImage);

    clr.sepia("test_input_sepia_split_100.png", "test_output_sepia_split_100.png", 100);

    Image result = opn.getImage("test_output_sepia_split_100.png");

    assertPixelEquals(new Pixel(67, 60, 46), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(202, 180, 140), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(135, 120, 93), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(270, 240, 187), result.getPixel(1, 1));
  }

  @Test
  public void testGreyscaleWithSplitAt0() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_greyscale_split_0.png", testImage);

    clr.greyscale("test_input_greyscale_split_0.png", "test_output_greyscale_split_0.png", 0);

    Image result = opn.getImage("test_output_greyscale_split_0.png");

    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 1));
  }

  @Test
  public void testGreyscaleWithSplitAt50() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_greyscale_split_50.png", testImage);

    clr.greyscale("test_input_greyscale_split_50.png", "test_output_greyscale_split_50.png", 50);

    Image result = opn.getImage("test_output_greyscale_split_50.png");

    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 1));
  }

  @Test
  public void testGreyscaleWithSplitAt100() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_greyscale_split_100.png", testImage);

    clr.greyscale("test_input_greyscale_split_100.png", "test_output_greyscale_split_100.png", 100);

    Image result = opn.getImage("test_output_greyscale_split_100.png");

    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(150, 150, 150), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaWithNegativePercentage() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_sepia_negative.png", testImage);

    clr.sepia("test_input_sepia_negative.png", "test_output_sepia_negative.png", -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleWithNegativePercentage() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(150, 150, 150));
    testImage.setPixel(1, 1, new Pixel(200, 200, 200));

    opn.saveImage("test_input_greyscale_negative.png", testImage);

    clr.greyscale("test_input_greyscale_negative.png", "test_output_greyscale_negative.png", -20);
  }

  @Test
  public void testSepiaWithMask() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(135, 135, 135)); // Pixel 0,0
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150)); // Pixel 1,0
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200)); // Pixel 0,1
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250)); // Pixel 1,1

    Image maskImage = new Image(2, 2);
    maskImage.setPixel(0, 0, new Pixel(0, 0, 0));  // Masked pixel
    maskImage.setPixel(1, 0, new Pixel(0, 0, 0));  // Masked pixel
    maskImage.setPixel(0, 1, new Pixel(255, 255, 255));  // Unmasked pixel
    maskImage.setPixel(1, 1, new Pixel(255, 255, 255));  // Unmasked pixel

    opn.saveImage("original_image.png", originalImage);
    opn.saveImage("mask_image.png", maskImage);

    clr.sepia("original_image.png", "sepia_masked_output.png", 50, "mask_image.png");

    Image sepiaMaskedImage = opn.getImage("sepia_masked_output.png");

    Pixel pixel = sepiaMaskedImage.getPixel(0, 0);
    assertEquals(182, pixel.getRed());
    assertEquals(162, pixel.getGreen());
    assertEquals(126, pixel.getBlue());

    pixel = sepiaMaskedImage.getPixel(1, 0);
    assertEquals(150, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(150, pixel.getBlue());

    pixel = sepiaMaskedImage.getPixel(0, 1);
    assertEquals(200, pixel.getRed());
    assertEquals(200, pixel.getGreen());
    assertEquals(200, pixel.getBlue());

    pixel = sepiaMaskedImage.getPixel(1, 1);
    assertEquals(250, pixel.getRed());
    assertEquals(250, pixel.getGreen());
    assertEquals(250, pixel.getBlue());
  }

  @Test
  public void testGreyscaleWithMask() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 100, 100));
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150));
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200));
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250));

    Image maskImage = new Image(2, 2);
    maskImage.setPixel(0, 0, new Pixel(0, 0, 0));
    maskImage.setPixel(1, 0, new Pixel(0, 0, 0));
    maskImage.setPixel(0, 1, new Pixel(255, 255, 255));
    maskImage.setPixel(1, 1, new Pixel(255, 255, 255));

    opn.saveImage("original_image.png", originalImage);
    opn.saveImage("mask_image.png", maskImage);

    clr.greyscale("original_image.png", "greyscale_masked_output.png", 50, "mask_image.png");

    Image greyscaleMaskedImage = opn.getImage("greyscale_masked_output.png");

    Pixel pixel = greyscaleMaskedImage.getPixel(0, 0);
    assertEquals(100, pixel.getRed());
    assertEquals(100, pixel.getGreen());
    assertEquals(100, pixel.getBlue());

    pixel = greyscaleMaskedImage.getPixel(1, 0);
    assertEquals(150, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(150, pixel.getBlue());

    pixel = greyscaleMaskedImage.getPixel(0, 1);// Should be transformed (greyscale)
    assertEquals(200, pixel.getGreen());
    assertEquals(200, pixel.getBlue());

    pixel = greyscaleMaskedImage.getPixel(1, 1);
    assertEquals(250, pixel.getRed());
    assertEquals(250, pixel.getGreen());
    assertEquals(250, pixel.getBlue());
  }


  @Test
  public void testSepiaWithMaskAndSplit() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(135, 135, 135));
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150));
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200));
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250));

    Image maskImage = new Image(2, 2);
    maskImage.setPixel(0, 0, new Pixel(0, 0, 0));
    maskImage.setPixel(1, 0, new Pixel(0, 0, 0));
    maskImage.setPixel(0, 1, new Pixel(255, 255, 255));
    maskImage.setPixel(1, 1, new Pixel(255, 255, 255));

    opn.saveImage("original_image.png", originalImage);
    opn.saveImage("mask_image.png", maskImage);

    clr.sepia("original_image.png", "sepia_masked_split_output.png", 50, "mask_image.png");

    Image sepiaMaskedSplitImage = opn.getImage("sepia_masked_split_output.png");

    Pixel[][] expectedPixels = {{new Pixel(182, 162, 126), new Pixel(150, 150, 150)},
        {new Pixel(200, 200, 200), new Pixel(250, 250, 250)}};

    for (int y = 0; y < sepiaMaskedSplitImage.getHeight(); y++) {
      for (int x = 0; x < sepiaMaskedSplitImage.getWidth(); x++) {
        Pixel actual = sepiaMaskedSplitImage.getPixel(x, y);
        Pixel expected = expectedPixels[y][x];
        assertEquals(expected.getRed(), actual.getRed());
        assertEquals(expected.getGreen(), actual.getGreen());
        assertEquals(expected.getBlue(), actual.getBlue());
      }
    }
  }

  @Test
  public void testGreyscaleWithMaskAndSplit() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 100, 100));
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150));
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200));
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250));

    Image maskImage = new Image(2, 2);
    maskImage.setPixel(0, 0, new Pixel(0, 0, 0));
    maskImage.setPixel(1, 0, new Pixel(0, 0, 0));
    maskImage.setPixel(0, 1, new Pixel(255, 255, 255));
    maskImage.setPixel(1, 1, new Pixel(255, 255, 255));

    opn.saveImage("original_image.png", originalImage);
    opn.saveImage("mask_image.png", maskImage);

    clr.greyscale("original_image.png", "greyscale_masked_split_output.png", 50, "mask_image.png");

    Image greyscaleMaskedSplitImage = opn.getImage("greyscale_masked_split_output.png");

    Pixel[][] expectedPixels = {{new Pixel(100, 100, 100), new Pixel(150, 150, 150)},
        {new Pixel(200, 200, 200), new Pixel(250, 250, 250)}};

    for (int y = 0; y < greyscaleMaskedSplitImage.getHeight(); y++) {
      for (int x = 0; x < greyscaleMaskedSplitImage.getWidth(); x++) {
        Pixel actual = greyscaleMaskedSplitImage.getPixel(x, y);
        Pixel expected = expectedPixels[y][x];
        assertEquals(expected.getRed(), actual.getRed());
        assertEquals(expected.getGreen(), actual.getGreen());
        assertEquals(expected.getBlue(), actual.getBlue());
      }
    }
  }

  @Test
  public void testSepiaWithNullMask() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 100, 100));
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150));
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200));
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250));

    opn.saveImage("original_image.png", originalImage);

    clr.sepia("original_image.png", "sepia_no_mask_output.png", 50, null);

    Image sepiaImage = opn.getImage("sepia_no_mask_output.png");

    Pixel pixel = sepiaImage.getPixel(0, 0);
    assertEquals(135, pixel.getRed());
    assertEquals(120, pixel.getGreen());
    assertEquals(93, pixel.getBlue());

    pixel = sepiaImage.getPixel(1, 0);
    assertEquals(150, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(150, pixel.getBlue());

    pixel = sepiaImage.getPixel(0, 1);
    assertEquals(255, pixel.getRed());
    assertEquals(240, pixel.getGreen());
    assertEquals(187, pixel.getBlue());

    pixel = sepiaImage.getPixel(1, 1);
    assertEquals(250, pixel.getRed());
    assertEquals(250, pixel.getGreen());
    assertEquals(250, pixel.getBlue());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSepiaWithNonExistentMask() {

    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 100, 100));
    originalImage.setPixel(1, 0, new Pixel(150, 150, 150));
    originalImage.setPixel(0, 1, new Pixel(200, 200, 200));
    originalImage.setPixel(1, 1, new Pixel(250, 250, 250));

    opn.saveImage("original_image.png", originalImage);
    clr.sepia("original_image.png", "sepia_output.png", 50, "non_existent_mask.png");
  }

}
