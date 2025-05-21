package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.ColorCorrection;
import org.junit.Before;
import org.junit.Test;

/**
 * The below test cases verifies the color correction operation on an image and cover various
 * scenarios.
 */

public class ColorCorrectionTest {

  private ColorCorrection colorCorrection;
  private ImageOperations imageOperations;

  @Before
  public void setUp() {
    colorCorrection = new ColorCorrection();
    imageOperations = new ImageOperations();
  }

  @Test
  public void testColorCorrectionWithSplit_HardcodedValues() {
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

    String inputImageName = "input_image.jpg";
    String outputImageName = "output_image.jpg";
    imageOperations.saveImage(inputImageName, original);

    colorCorrection.colorCorrect(inputImageName, outputImageName, 50);

    Image correctedImage = imageOperations.getImage(outputImageName);

    Image expectedCorrectedImage = new Image(3, 3);
    expectedCorrectedImage.setPixel(0, 0, new Pixel(100, 100, 100)); // Hypothetical expected values
    expectedCorrectedImage.setPixel(1, 0, new Pixel(255, 255, 255));
    expectedCorrectedImage.setPixel(2, 0, new Pixel(70, 120, 170)); // Unchanged due to split
    expectedCorrectedImage.setPixel(0, 1, new Pixel(130, 130, 130));
    expectedCorrectedImage.setPixel(1, 1, new Pixel(255, 255, 255));
    expectedCorrectedImage.setPixel(2, 1, new Pixel(100, 150, 200)); // Unchanged due to split
    expectedCorrectedImage.setPixel(0, 2, new Pixel(160, 160, 160));
    expectedCorrectedImage.setPixel(1, 2, new Pixel(255, 255, 255)); // Unchanged due to split
    expectedCorrectedImage.setPixel(2, 2, new Pixel(130, 180, 230)); // Unchanged due to split

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        Pixel expectedPixel = expectedCorrectedImage.getPixel(x, y);
        Pixel correctedPixel = correctedImage.getPixel(x, y);

        assertEquals("Red channel mismatch at (" + x + "," + y + ")", expectedPixel.getRed(),
            correctedPixel.getRed());
        assertEquals("Green channel mismatch at (" + x + "," + y + ")", expectedPixel.getGreen(),
            correctedPixel.getGreen());
        assertEquals("Blue channel mismatch at (" + x + "," + y + ")", expectedPixel.getBlue(),
            correctedPixel.getBlue());
      }
    }
  }

  @Test
  public void testColorCorrectionWithoutSplit_HardcodedValues() {
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

    String inputImageName = "input_image_without_split.jpg";
    String outputImageName = "output_image_without_split.jpg";
    imageOperations.saveImage(inputImageName, original);

    colorCorrection.colorCorrect(inputImageName, outputImageName, null);

    Image correctedImage = imageOperations.getImage(outputImageName);

    Image expectedCorrectedImage = new Image(3, 3);
    expectedCorrectedImage.setPixel(0, 0, new Pixel(100, 100, 100)); // Example corrected values
    expectedCorrectedImage.setPixel(1, 0, new Pixel(110, 110, 110));
    expectedCorrectedImage.setPixel(2, 0, new Pixel(120, 120, 120));
    expectedCorrectedImage.setPixel(0, 1, new Pixel(130, 130, 130));
    expectedCorrectedImage.setPixel(1, 1, new Pixel(140, 140, 140));
    expectedCorrectedImage.setPixel(2, 1, new Pixel(150, 150, 150));
    expectedCorrectedImage.setPixel(0, 2, new Pixel(160, 160, 160));
    expectedCorrectedImage.setPixel(1, 2, new Pixel(170, 170, 170));
    expectedCorrectedImage.setPixel(2, 2, new Pixel(180, 180, 180));

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        Pixel expectedPixel = expectedCorrectedImage.getPixel(x, y);
        Pixel correctedPixel = correctedImage.getPixel(x, y);

        assertEquals("Red channel mismatch at (" + x + "," + y + ")", expectedPixel.getRed(),
            correctedPixel.getRed());
        assertEquals("Green channel mismatch at (" + x + "," + y + ")", expectedPixel.getGreen(),
            correctedPixel.getGreen());
        assertEquals("Blue channel mismatch at (" + x + "," + y + ")", expectedPixel.getBlue(),
            correctedPixel.getBlue());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorCorrectWithInvalidSplitPercentageBelowZero() {
    ColorCorrection colorCorrection = new ColorCorrection();
    colorCorrection.colorCorrect("input.jpg", "output.jpg", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorCorrectWithInvalidSplitPercentageAbove100() {
    ColorCorrection colorCorrection = new ColorCorrection();
    colorCorrection.colorCorrect("input.jpg", "output.jpg", 101);
  }

}