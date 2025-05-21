package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.Filtering;
import model.transformations.interfaces.FilteringInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for verifying the behavior of the filtering operations (blur and
 * sharpen) on images. It tests a variety of scenarios to ensure correct functionality and error
 * handling in the Filtering class.
 */

public class FilteringTest {

  ImageOperations opn = new ImageOperations();
  FilteringInterface filter = new Filtering();

  @Before
  public void setUp() {

    opn.saveImage("testImage", new Image(10, 10));
    opn.saveImage("onePixelImage", new Image(1, 1));
    opn.saveImage("maxRGBImage", createMaxRGBImage());
    opn.saveImage("minRGBImage", createMinRGBImage());
  }

  private Image createTestImage() {
    Image image = new Image(2, 2);
    image.setPixel(0, 0, new Pixel(100, 150, 200));
    image.setPixel(0, 1, new Pixel(50, 50, 50));
    image.setPixel(1, 0, new Pixel(255, 0, 0));
    image.setPixel(1, 1, new Pixel(0, 255, 0));
    return image;
  }

  private Image createMaskImage(boolean isBlack) {
    Image mask = new Image(10, 10);
    if (isBlack) {
      for (int y = 0; y < mask.getHeight(); y++) {
        for (int x = 0; x < mask.getWidth(); x++) {
          mask.setPixel(x, y, new Pixel(0, 0, 0));
        }
      }
    } else {
      for (int y = 0; y < mask.getHeight(); y++) {
        for (int x = 0; x < mask.getWidth(); x++) {
          mask.setPixel(x, y, new Pixel(255, 255, 255));
        }
      }
    }
    return mask;
  }

  private Image createMaxRGBImage() {
    Image image = new Image(10, 10);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        image.setPixel(x, y, new Pixel(255, 255, 255));
      }
    }
    return image;
  }

  private Image createMinRGBImage() {
    Image image = new Image(10, 10);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        image.setPixel(x, y, new Pixel(0, 0, 0));
      }
    }
    return image;
  }

  @Test
  public void testBlurValidImage() {
    filter.blur("testImage", "blurredImage", null);
    Image result = opn.getImage("blurredImage");
    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
  }

  @Test
  public void testSharpenValidImage() {
    filter.sharpen("testImage", "sharpenedImage", null);
    Image result = opn.getImage("sharpenedImage");
    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurEmptyImage() {
    filter.blur("emptyImage", "emptyBlurredImage", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenEmptyImage() {
    filter.sharpen("emptyImage", "emptySharpenedImage", null);
  }

  @Test
  public void testBlurOnePixelImage() {
    filter.blur("onePixelImage", "blurredOnePixelImage", null);
    Image result = opn.getImage("blurredOnePixelImage");
    assertNotNull(result);
    assertEquals(1, result.getWidth());
    assertEquals(1, result.getHeight());
    Pixel originalPixel = opn.getImage("onePixelImage").getPixel(0, 0);
    Pixel resultPixel = result.getPixel(0, 0);

    assertEquals(originalPixel.getRed(), resultPixel.getRed());
    assertEquals(originalPixel.getGreen(), resultPixel.getGreen());
    assertEquals(originalPixel.getBlue(), resultPixel.getBlue());
  }

  @Test
  public void testSharpenOnePixelImage() {
    filter.sharpen("onePixelImage", "sharpenedOnePixelImage", null);
    Image result = opn.getImage("sharpenedOnePixelImage");
    assertNotNull(result);
    assertEquals(1, result.getWidth());
    assertEquals(1, result.getHeight());
    Pixel originalPixel = opn.getImage("onePixelImage").getPixel(0, 0);
    Pixel resultPixel = result.getPixel(0, 0);

    assertEquals(originalPixel.getRed(), resultPixel.getRed());
    assertEquals(originalPixel.getGreen(), resultPixel.getGreen());
    assertEquals(originalPixel.getBlue(), resultPixel.getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurNonExistingImage() {
    filter.blur("nonExistingImage", "blurredImage", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenNonExistingImage() {
    filter.sharpen("nonExistingImage", "sharpenedImage", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurNullImageName() {
    filter.blur(null, "blurredNullImage", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenNullImageName() {
    filter.sharpen(null, "sharpenedNullImage", null);
  }

  @Test
  public void testBlurMaxRGBImage() {
    filter.blur("maxRGBImage", "blurredMaxRGBImage", null);
    Image result = opn.getImage("blurredMaxRGBImage");
    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        Pixel pixel = result.getPixel(x, y);
        assertTrue(pixel.getRed() <= 255 && pixel.getRed() >= 0);
        assertTrue(pixel.getGreen() <= 255 && pixel.getGreen() >= 0);
        assertTrue(pixel.getBlue() <= 255 && pixel.getBlue() >= 0);
      }
    }
  }

  @Test
  public void testSharpenMinRGBImage() {
    filter.sharpen("minRGBImage", "sharpenedMinRGBImage", null);
    Image result = opn.getImage("sharpenedMinRGBImage");
    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        Pixel pixel = result.getPixel(x, y);
        assertEquals(0, pixel.getRed());
        assertEquals(0, pixel.getGreen());
        assertEquals(0, pixel.getBlue());
      }
    }
  }

  @Test
  public void testVerifyImageSavedAfterBlur() {
    filter.blur("testImage", "filteredImage", null);
    Image result = opn.getImage("filteredImage");
    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
  }

  @Test
  public void testVerifyImageSavedAfterSharpen() {
    filter.sharpen("testImage", "filteredImage", null);
    Image result = opn.getImage("filteredImage");
    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
  }

  @Test
  public void testBlurOddDimensionImage() {
    Image oddDimensionImage = new Image(5, 5);
    opn.saveImage("oddDimensionImage", oddDimensionImage);
    filter.blur("oddDimensionImage", "blurredOddDimensionImage", null);
    Image result = opn.getImage("blurredOddDimensionImage");
    assertNotNull(result);
    assertEquals(5, result.getWidth());
    assertEquals(5, result.getHeight());
  }

  @Test
  public void testSharpenOddDimensionImage() {
    Image oddDimensionImage = new Image(5, 5);
    opn.saveImage("oddDimensionImage", oddDimensionImage);
    filter.sharpen("oddDimensionImage", "sharpenedOddDimensionImage", null);
    Image result = opn.getImage("sharpenedOddDimensionImage");
    assertNotNull(result);
    assertEquals(5, result.getWidth());
    assertEquals(5, result.getHeight());
  }

  @Test
  public void testBlurWithoutSplit() {
    Image original = new Image(3, 3);
    original.setPixel(0, 0, new Pixel(50, 100, 150));
    original.setPixel(1, 0, new Pixel(60, 110, 160));
    original.setPixel(2, 0, new Pixel(70, 120, 170));
    original.setPixel(0, 1, new Pixel(80, 130, 180));
    original.setPixel(1, 1, new Pixel(90, 140, 190));
    original.setPixel(2, 1, new Pixel(100, 150, 200));
    original.setPixel(0, 2, new Pixel(110, 160, 210));
    original.setPixel(1, 2, new Pixel(120, 170, 220));
    original.setPixel(2, 2, new Pixel(130, 180, 230));

    String inputImageName = "input_image_blur_no_split.jpg";
    String outputImageName = "output_image_blur_no_split.jpg";
    opn.saveImage(inputImageName, original);

    filter.blur(inputImageName, outputImageName, null);

    Image blurredImage = opn.getImage(outputImageName);

    Image expectedBlurredImage = new Image(3, 3);
    expectedBlurredImage.setPixel(0, 0, new Pixel(60, 110, 160));
    expectedBlurredImage.setPixel(1, 0, new Pixel(67, 117, 167));
    expectedBlurredImage.setPixel(2, 0, new Pixel(75, 125, 175));
    expectedBlurredImage.setPixel(0, 1, new Pixel(82, 132, 182));
    expectedBlurredImage.setPixel(1, 1, new Pixel(90, 140, 190));
    expectedBlurredImage.setPixel(2, 1, new Pixel(97, 147, 197));
    expectedBlurredImage.setPixel(0, 2, new Pixel(105, 155, 205));
    expectedBlurredImage.setPixel(1, 2, new Pixel(112, 162, 212));
    expectedBlurredImage.setPixel(2, 2, new Pixel(120, 170, 220));

    verifyImageEquality(blurredImage, expectedBlurredImage);
  }

  @Test
  public void testBlurWithSplit() {
    Image original = new Image(3, 3);
    original.setPixel(0, 0, new Pixel(50, 100, 150));
    original.setPixel(1, 0, new Pixel(60, 110, 160));
    original.setPixel(2, 0, new Pixel(70, 120, 170));
    original.setPixel(0, 1, new Pixel(80, 130, 180));
    original.setPixel(1, 1, new Pixel(90, 140, 190));
    original.setPixel(2, 1, new Pixel(100, 150, 200));
    original.setPixel(0, 2, new Pixel(110, 160, 210));
    original.setPixel(1, 2, new Pixel(120, 170, 220));
    original.setPixel(2, 2, new Pixel(130, 180, 230));

    String inputImageName = "input_image_blur_with_split.jpg";
    String outputImageName = "output_image_blur_with_split.jpg";
    opn.saveImage(inputImageName, original);

    filter.blur(inputImageName, outputImageName, 50);

    Image blurredImage = opn.getImage(outputImageName);

    Image expectedBlurredImage = new Image(3, 3);
    expectedBlurredImage.setPixel(0, 0, new Pixel(60, 110, 160));
    expectedBlurredImage.setPixel(1, 0, new Pixel(255, 255, 255));
    expectedBlurredImage.setPixel(2, 0, new Pixel(70, 120, 170));
    expectedBlurredImage.setPixel(0, 1, new Pixel(82, 132, 182));
    expectedBlurredImage.setPixel(1, 1, new Pixel(255, 255, 255));
    expectedBlurredImage.setPixel(2, 1, new Pixel(100, 150, 200));
    expectedBlurredImage.setPixel(0, 2, new Pixel(105, 155, 205));
    expectedBlurredImage.setPixel(1, 2, new Pixel(255, 255, 255));
    expectedBlurredImage.setPixel(2, 2, new Pixel(130, 180, 230));

    verifyImageEquality(blurredImage, expectedBlurredImage);
  }

  @Test
  public void testSharpenWithoutSplit() {
    Image original = new Image(3, 3);
    original.setPixel(0, 0, new Pixel(50, 100, 150));
    original.setPixel(1, 0, new Pixel(60, 110, 160));
    original.setPixel(2, 0, new Pixel(70, 120, 170));
    original.setPixel(0, 1, new Pixel(80, 130, 180));
    original.setPixel(1, 1, new Pixel(90, 140, 190));
    original.setPixel(2, 1, new Pixel(100, 150, 200));
    original.setPixel(0, 2, new Pixel(110, 160, 210));
    original.setPixel(1, 2, new Pixel(120, 170, 220));
    original.setPixel(2, 2, new Pixel(130, 180, 230));

    String inputImageName = "input_image_sharpen_no_split.jpg";
    String outputImageName = "output_image_sharpen_no_split.jpg";
    opn.saveImage(inputImageName, original);

    filter.sharpen(inputImageName, outputImageName, null);

    Image sharpenedImage = opn.getImage(outputImageName);

    Image expectedSharpenedImage = new Image(3, 3);
    expectedSharpenedImage.setPixel(0, 0, new Pixel(20, 70, 120));
    expectedSharpenedImage.setPixel(1, 0, new Pixel(37, 87, 137));
    expectedSharpenedImage.setPixel(2, 0, new Pixel(55, 105, 155));
    expectedSharpenedImage.setPixel(0, 1, new Pixel(72, 122, 172));
    expectedSharpenedImage.setPixel(1, 1, new Pixel(90, 140, 190));
    expectedSharpenedImage.setPixel(2, 1, new Pixel(107, 157, 207));
    expectedSharpenedImage.setPixel(0, 2, new Pixel(125, 175, 225));
    expectedSharpenedImage.setPixel(1, 2, new Pixel(142, 192, 242));
    expectedSharpenedImage.setPixel(2, 2, new Pixel(160, 210, 255));

    verifyImageEquality(sharpenedImage, expectedSharpenedImage);
  }

  @Test
  public void testSharpenWithSplit() {
    Image original = new Image(3, 3);
    original.setPixel(0, 0, new Pixel(50, 100, 150));
    original.setPixel(1, 0, new Pixel(60, 110, 160));
    original.setPixel(2, 0, new Pixel(70, 120, 170));
    original.setPixel(0, 1, new Pixel(80, 130, 180));
    original.setPixel(1, 1, new Pixel(90, 140, 190));
    original.setPixel(2, 1, new Pixel(100, 150, 200));
    original.setPixel(0, 2, new Pixel(110, 160, 210));
    original.setPixel(1, 2, new Pixel(120, 170, 220));
    original.setPixel(2, 2, new Pixel(130, 180, 230));

    String inputImageName = "input_image_sharpen_with_split.jpg";
    String outputImageName = "output_image_sharpen_with_split.jpg";
    opn.saveImage(inputImageName, original);

    filter.sharpen(inputImageName, outputImageName, 50);

    Image sharpenedImage = opn.getImage(outputImageName);

    Image expectedSharpenedImage = new Image(3, 3);
    expectedSharpenedImage.setPixel(0, 0, new Pixel(20, 70, 120));
    expectedSharpenedImage.setPixel(1, 0, new Pixel(255, 255, 255));
    expectedSharpenedImage.setPixel(2, 0, new Pixel(70, 120, 170));
    expectedSharpenedImage.setPixel(0, 1, new Pixel(72, 122, 172));
    expectedSharpenedImage.setPixel(1, 1, new Pixel(255, 255, 255));
    expectedSharpenedImage.setPixel(2, 1, new Pixel(100, 150, 200));
    expectedSharpenedImage.setPixel(0, 2, new Pixel(125, 175, 225));
    expectedSharpenedImage.setPixel(1, 2, new Pixel(255, 255, 255));
    expectedSharpenedImage.setPixel(2, 2, new Pixel(130, 180, 230));

    verifyImageEquality(sharpenedImage, expectedSharpenedImage);
  }

  private void verifyImageEquality(Image actual, Image expected) {
    for (int y = 0; y < expected.getHeight(); y++) {
      for (int x = 0; x < expected.getWidth(); x++) {
        Pixel expectedPixel = expected.getPixel(x, y);
        Pixel actualPixel = actual.getPixel(x, y);

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurWithInvalidSplitPositionBelowZero() {
    Filtering filtering = new Filtering();
    filtering.blur("input.jpg", "output.jpg", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurWithInvalidSplitPositionAbove100() {
    Filtering filtering = new Filtering();
    filtering.blur("input.jpg", "output.jpg", 101);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenWithInvalidSplitPositionBelowZero() {
    Filtering filtering = new Filtering();
    filtering.sharpen("input.jpg", "output.jpg", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenWithInvalidSplitPositionAbove100() {
    Filtering filtering = new Filtering();
    filtering.sharpen("input.jpg", "output.jpg", 101);
  }

  @Test
  public void testBlurWithMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    filter.blur("original_image.png", "blurred_with_mask.png", null, "mask_image.png");

    Image result = opn.getImage("blurred_with_mask.png");

    Pixel[][] expectedPixels = {{new Pixel(113, 109, 121), new Pixel(255, 0, 0)},
        {new Pixel(62, 104, 65), new Pixel(0, 255, 0)}};

    verifyImagePixels(result, expectedPixels);
  }

  @Test
  public void testSharpenWithMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    filter.sharpen("original_image.png", "sharpened_with_mask.png", null, "mask_image.png");

    Image result = opn.getImage("sharpened_with_mask.png");

    Pixel[][] expectedPixels = {{new Pixel(112, 136, 225), new Pixel(255, 0, 0)},
        {new Pixel(24, 56, 56), new Pixel(0, 255, 0)}};

    verifyImagePixels(result, expectedPixels);
  }

  @Test
  public void testBlurWithMaskAndSplit() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    filter.blur("original_image.png", "blurred_with_mask_and_split.png", 50, "mask_image.png");

    Image result = opn.getImage("blurred_with_mask_and_split.png");

    Pixel[][] expectedPixels = {{new Pixel(113, 109, 121), new Pixel(255, 0, 0)},
        {new Pixel(62, 104, 65), new Pixel(0, 255, 0)}};

    verifyImagePixels(result, expectedPixels);
  }

  @Test
  public void testSharpenWithMaskAndSplit() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    filter.sharpen("original_image.png", "sharpened_with_mask_and_split.png", 50, "mask_image.png");

    Image result = opn.getImage("sharpened_with_mask_and_split.png");

    Pixel[][] expectedPixels = {{new Pixel(112, 136, 225), new Pixel(255, 0, 0)},
        {new Pixel(24, 56, 56), new Pixel(0, 255, 0)}};

    verifyImagePixels(result, expectedPixels);
  }

  @Test
  public void testBlurWithEmptyMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(false);
    opn.saveImage("mask_image.png", mask);

    filter.blur("original_image.png", "blurred_with_empty_mask.png", null, "mask_image.png");

    Image result = opn.getImage("blurred_with_empty_mask.png");

    assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(150, result.getPixel(0, 0).getGreen());
    assertEquals(200, result.getPixel(0, 0).getBlue());

    assertEquals(50, result.getPixel(0, 1).getRed());
    assertEquals(50, result.getPixel(0, 1).getGreen());
    assertEquals(50, result.getPixel(0, 1).getBlue());
  }

  @Test
  public void testSharpenWithEmptyMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(false);
    opn.saveImage("mask_image.png", mask);

    filter.sharpen("original_image.png", "sharpened_with_empty_mask.png", null, "mask_image.png");

    Image result = opn.getImage("sharpened_with_empty_mask.png");

    assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(150, result.getPixel(0, 0).getGreen());
    assertEquals(200, result.getPixel(0, 0).getBlue());

    assertEquals(50, result.getPixel(0, 1).getRed());
    assertEquals(50, result.getPixel(0, 1).getGreen());
    assertEquals(50, result.getPixel(0, 1).getBlue());
  }

  @Test
  public void testNoMaskAppliedBlur() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    filter.blur("original_image.png", "blurred_no_mask.png", null);

    Image result = opn.getImage("blurred_no_mask.png");

    assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(150, result.getPixel(0, 0).getGreen());
    assertEquals(200, result.getPixel(0, 0).getBlue());

    assertEquals(50, result.getPixel(0, 1).getRed());
    assertEquals(50, result.getPixel(0, 1).getGreen());
    assertEquals(50, result.getPixel(0, 1).getBlue());
  }

  @Test
  public void testNoMaskAppliedSharpen() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    filter.sharpen("original_image.png", "sharpened_no_mask.png", null);

    Image result = opn.getImage("sharpened_no_mask.png");

    assertEquals(112, result.getPixel(0, 0).getRed());
    assertEquals(136, result.getPixel(0, 0).getGreen());
    assertEquals(225, result.getPixel(0, 0).getBlue());

    assertEquals(24, result.getPixel(0, 1).getRed());
    assertEquals(56, result.getPixel(0, 1).getGreen());
    assertEquals(56, result.getPixel(0, 1).getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskAndImageDimensionMismatch() {
    Image originalImage = new Image(2, 2);
    originalImage.setPixel(0, 0, new Pixel(100, 150, 200));
    originalImage.setPixel(0, 1, new Pixel(50, 75, 100));
    originalImage.setPixel(1, 0, new Pixel(200, 50, 150));
    originalImage.setPixel(1, 1, new Pixel(25, 50, 75));
    opn.saveImage("original_image.png", originalImage);

    Image mismatchedMask = new Image(3, 3);
    mismatchedMask.setPixel(0, 0, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(0, 1, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(0, 2, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(1, 0, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(1, 1, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(1, 2, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(2, 0, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(2, 1, new Pixel(255, 255, 255));
    mismatchedMask.setPixel(2, 2, new Pixel(255, 255, 255));
    opn.saveImage("mismatched_mask_image.png", mismatchedMask);

    // Attempt to apply sharpen filter with the mismatched mask
    // Expect an IllegalArgumentException to be thrown
    filter.sharpen("original_image.png", "sharpened_with_mismatched_mask.png", null,
        "mismatched_mask_image.png");
  }


  /**
   * Verifies all pixels in the result image against expected pixel values.
   *
   * @param actual   The result image after the operation.
   * @param expected A 2D array of expected pixels.
   */

  private void verifyImagePixels(Image actual, Pixel[][] expected) {
    for (int y = 0; y < expected.length; y++) {
      for (int x = 0; x < expected[0].length; x++) {
        Pixel actualPixel = actual.getPixel(x, y);
        Pixel expectedPixel = expected[y][x];

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }
  }
}