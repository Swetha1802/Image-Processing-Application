package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.LevelAdjust;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit4 Test for the LevelAdjustClass.
 */
public class LevelAdjustTest {

  private LevelAdjust levelAdjuster;
  private ImageOperations imageOps;

  @Before
  public void setUp() {
    levelAdjuster = new LevelAdjust();
    imageOps = new ImageOperations();
  }

  @Test
  public void testLevelAdjustmentWithoutSplit() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(255, 255, 255));

    imageOps.saveImage("test_input_without_split.png", testImage);

    levelAdjuster.levelsAdjust(0, 128, 255, "test_input_without_split.png",
        "test_output_without_split.png", null);

    Image result = imageOps.getImage("test_output_without_split.png");

    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(255, 255, 255), result.getPixel(1, 1));
  }

  @Test
  public void testLevelAdjustmentWithSplit() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(255, 255, 255));

    imageOps.saveImage("test_input_with_split.png", testImage);

    levelAdjuster.levelsAdjust(0, 128, 255, "test_input_with_split.png",
        "test_output_with_split.png", 50);

    Image result = imageOps.getImage("test_output_with_split.png");

    assertPixelEquals(new Pixel(255, 255, 255), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(255, 255, 255), result.getPixel(1, 1));
    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithNegativeSplitPercentage() {
    LevelAdjust levelAdjust = new LevelAdjust();
    levelAdjust.levelsAdjust(50, 100, 150, "input_image.png", "output_image.png", -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithGreaterThan100SplitPercentage() {
    LevelAdjust levelAdjust = new LevelAdjust();
    levelAdjust.levelsAdjust(50, 100, 150, "input_image.png", "output_image.png", 105);
  }

  @Test
  public void testBasicLevelAdjustment() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(0, 0, 0));
    testImage.setPixel(0, 1, new Pixel(128, 128, 128));
    testImage.setPixel(1, 0, new Pixel(255, 255, 255));
    testImage.setPixel(1, 1, new Pixel(64, 64, 64));

    imageOps.saveImage("test_input.png", testImage);

    levelAdjuster.levelsAdjust(0, 128, 255, "test_input.png", "test_output.png", null);

    Image result = imageOps.getImage("test_output.png");

    assertPixelEquals(new Pixel(0, 0, 0), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(128, 128, 128), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(255, 255, 255), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(64, 64, 64), result.getPixel(1, 1));
  }

  @Test
  public void testLevelAdjustmentWithTransformation() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(50, 50, 50));
    testImage.setPixel(0, 1, new Pixel(100, 100, 100));
    testImage.setPixel(1, 0, new Pixel(200, 200, 200));
    testImage.setPixel(1, 1, new Pixel(255, 255, 255));

    imageOps.saveImage("test_input_transformed.png", testImage);

    levelAdjuster.levelsAdjust(0, 128, 255, "test_input_transformed.png",
        "test_output_transformed.png", null);

    Image result = imageOps.getImage("test_output_transformed.png");

    assertPixelEquals(new Pixel(50, 50, 50), result.getPixel(0, 0));
    assertPixelEquals(new Pixel(100, 100, 100), result.getPixel(0, 1));
    assertPixelEquals(new Pixel(200, 200, 200), result.getPixel(1, 0));
    assertPixelEquals(new Pixel(255, 255, 255), result.getPixel(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBlackPoint() {
    levelAdjuster.levelsAdjust(-1, 128, 255, "test.png", "output.png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWhitePoint() {
    levelAdjuster.levelsAdjust(0, 128, 256, "test.png", "output.png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMidPoint() {
    levelAdjuster.levelsAdjust(0, 256, 255, "test.png", "output.png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPointOrder() {
    levelAdjuster.levelsAdjust(128, 64, 255, "test.png", "output.png", null);
  }

  @Test
  public void testExtremeLevels() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(0, 0, 0));
    testImage.setPixel(0, 1, new Pixel(255, 255, 255));
    testImage.setPixel(1, 0, new Pixel(128, 128, 128));
    testImage.setPixel(1, 1, new Pixel(64, 64, 64));

    imageOps.saveImage("extreme_input.png", testImage);

    levelAdjuster.levelsAdjust(100, 127, 150, "extreme_input.png", "extreme_output.png", null);

    Image result = imageOps.getImage("extreme_output.png");

    for (int x = 0; x < 2; x++) {
      for (int y = 0; y < 2; y++) {
        Pixel pixel = result.getPixel(x, y);
        assertTrue(pixel.getRed() == 0 || pixel.getRed() == 255 || (pixel.getRed() >= 100
            && pixel.getRed() <= 150));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateLevelsWithInvalidBlackLevel() {
    levelAdjuster.levelsAdjust(-1, 128, 255, "input_image.jpg", "output_image.jpg", 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateLevelsWithInvalidMidLevel() {
    levelAdjuster.levelsAdjust(0, 300, 255, "input_image.jpg", "output_image.jpg", 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateLevelsWithInvalidWhiteLevel() {
    levelAdjuster.levelsAdjust(0, 128, 300, "input_image.jpg", "output_image.jpg", 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateLevelsWithInvalidOrderBlackGreaterOrEqualMid() {
    levelAdjuster.levelsAdjust(200, 200, 255, "input_image.jpg", "output_image.jpg", 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateLevelsWithInvalidOrderMidGreaterOrEqualWhite() {
    levelAdjuster.levelsAdjust(0, 200, 200, "input_image.jpg", "output_image.jpg", 50);
  }

  @Test
  public void testValidateLevelsWithValidInputs() {
    ImageOperations imageOps = new ImageOperations();
    Image testImage = new Image(10, 10);

    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 10; x++) {
        testImage.setPixel(x, y, new Pixel(128, 128, 128));
      }
    }

    imageOps.saveImage("input_image.jpg", testImage);

    try {
      levelAdjuster.levelsAdjust(0, 128, 255, "input_image.jpg", "output_image.jpg", 50);
    } catch (IllegalArgumentException e) {
      fail("Unexpected exception for valid levels: " + e.getMessage());
    }
  }

  private void assertPixelEquals(Pixel expected, Pixel actual) {
    assertEquals(expected.getRed(), actual.getRed());
    assertEquals(expected.getGreen(), actual.getGreen());
    assertEquals(expected.getBlue(), actual.getBlue());
  }
}
