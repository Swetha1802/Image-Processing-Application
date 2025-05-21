package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.Histogram;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit4 test for Histogram Class.
 */
public class HistogramTest {

  private Histogram histogram;
  private ImageOperations imageOps;

  @Before
  public void setUp() {
    histogram = new Histogram();
    imageOps = new ImageOperations();
  }

  @Test
  public void testCalculateHistogramsBasic() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(100, 150, 200));
    testImage.setPixel(0, 1, new Pixel(100, 150, 200));
    testImage.setPixel(1, 0, new Pixel(100, 150, 200));
    testImage.setPixel(1, 1, new Pixel(100, 150, 200));

    int[][] histograms = histogram.calculateHistograms(testImage);

    assertEquals(4, histograms[0][100]);
    assertEquals(4, histograms[1][150]);
    assertEquals(4, histograms[2][200]);

    for (int x = 0; x < testImage.getWidth(); x++) {
      for (int y = 0; y < testImage.getHeight(); y++) {
        Pixel pixel = testImage.getPixel(x, y);
        assertEquals("Pixel (" + x + "," + y + ") Red", 100, pixel.getRed());
        assertEquals("Pixel (" + x + "," + y + ") Green", 150, pixel.getGreen());
        assertEquals("Pixel (" + x + "," + y + ") Blue", 200, pixel.getBlue());
      }
    }
  }

  @Test
  public void testCalculateHistogramsExtremeBoundaries() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(0, 0, 0));
    testImage.setPixel(0, 1, new Pixel(255, 255, 255));
    testImage.setPixel(1, 0, new Pixel(0, 255, 0));
    testImage.setPixel(1, 1, new Pixel(255, 0, 255));

    int[][] histograms = histogram.calculateHistograms(testImage);

    assertEquals(2, histograms[0][0]);
    assertEquals(2, histograms[0][255]);
    assertEquals(2, histograms[1][0]);
    assertEquals(2, histograms[1][255]);
    assertEquals(2, histograms[2][0]);
    assertEquals(2, histograms[2][255]);

    for (int x = 0; x < testImage.getWidth(); x++) {
      for (int y = 0; y < testImage.getHeight(); y++) {
        Pixel pixel = testImage.getPixel(x, y);
        assertEquals("Pixel (" + x + "," + y + ") Red",
            (x == 1 && y == 1) ? 255 : (x == 0 && y == 0) ? 0 : (x == 1 && y == 0) ? 0 : 255,
            pixel.getRed());
        assertEquals("Pixel (" + x + "," + y + ") Green",
            (x == 1 && y == 0) ? 255 : (x == 0 && y == 1) ? 255 : 0, pixel.getGreen());
        assertEquals("Pixel (" + x + "," + y + ") Blue",
            (x == 0 && y == 0) ? 0 : (x == 1 && y == 1) ? 255 : (x == 0 && y == 1) ? 255 : 0,
            pixel.getBlue());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHistogramImageCreation() {
    Image testImage = new Image(2, 2);
    testImage.setPixel(0, 0, new Pixel(100, 150, 200));
    testImage.setPixel(0, 1, new Pixel(100, 150, 200));
    testImage.setPixel(1, 0, new Pixel(100, 150, 200));
    testImage.setPixel(1, 1, new Pixel(100, 150, 200));

    histogram.generateHistogram("test_input.png", "test_output.png");

    Image histogramImage = imageOps.getImage("test_output.png");

    assertEquals(256, histogramImage.getWidth());
    assertEquals(256, histogramImage.getHeight());

    verifyBackgroundColor(histogramImage, new Pixel(255, 255, 255));
    verifyGridLines(histogramImage, new Pixel(230, 230, 230), 16);
    verifyHistogramPeaks(histogramImage, 100, 150, 200);
  }

  private void verifyBackgroundColor(Image image, Pixel expectedColor) {
    int[] samplePoints = {32, 96, 160, 224};
    for (int x : samplePoints) {
      for (int y : samplePoints) {
        Pixel pixel = image.getPixel(x, y);
        assertEquals(expectedColor.getRed(), pixel.getRed());
        assertEquals(expectedColor.getGreen(), pixel.getGreen());
        assertEquals(expectedColor.getBlue(), pixel.getBlue());
      }
    }
  }

  private void verifyGridLines(Image image, Pixel expectedColor, int spacing) {
    for (int x = 0; x < image.getWidth(); x += spacing) {
      Pixel pixel = image.getPixel(x, image.getHeight() / 2);
      assertEquals(expectedColor.getRed(), pixel.getRed());
      assertEquals(expectedColor.getGreen(), pixel.getGreen());
      assertEquals(expectedColor.getBlue(), pixel.getBlue());
    }

    for (int y = 0; y < image.getHeight(); y += spacing) {
      Pixel pixel = image.getPixel(image.getWidth() / 2, y);
      assertEquals(expectedColor.getRed(), pixel.getRed());
      assertEquals(expectedColor.getGreen(), pixel.getGreen());
      assertEquals(expectedColor.getBlue(), pixel.getBlue());
    }
  }

  private void verifyHistogramPeaks(Image image, int redPeak, int greenPeak, int bluePeak) {
    Pixel redPixel = image.getPixel(redPeak, 255);
    assertTrue(redPixel.getRed() > redPixel.getGreen() && redPixel.getRed() > redPixel.getBlue());

    Pixel greenPixel = image.getPixel(greenPeak, 255);
    assertTrue(greenPixel.getGreen() > greenPixel.getRed()
        && greenPixel.getGreen() > greenPixel.getBlue());

    Pixel bluePixel = image.getPixel(bluePeak, 255);
    assertTrue(
        bluePixel.getBlue() > bluePixel.getRed() && bluePixel.getBlue() > bluePixel.getGreen());
  }

  @Test
  public void testSinglePixelImage() {
    Image singlePixel = new Image(1, 1);
    singlePixel.setPixel(0, 0, new Pixel(127, 127, 127));

    int[][] histograms = histogram.calculateHistograms(singlePixel);

    assertEquals(1, histograms[0][127]);
    assertEquals(1, histograms[1][127]);
    assertEquals(1, histograms[2][127]);

    Pixel pixel = singlePixel.getPixel(0, 0);
    assertEquals(127, pixel.getRed());
    assertEquals(127, pixel.getGreen());
    assertEquals(127, pixel.getBlue());
  }

  @Test
  public void testGradientImage() {
    Image gradientImage = new Image(256, 1);
    for (int x = 0; x < gradientImage.getWidth(); x++) {
      gradientImage.setPixel(x, 0, new Pixel(x, x, x));
    }

    int[][] histograms = histogram.calculateHistograms(gradientImage);

    assertEquals(1, histograms[0][0]);
    assertEquals(1, histograms[1][255]);
    assertEquals(1, histograms[2][127]);

    for (int x = 0; x < gradientImage.getWidth(); x++) {
      Pixel pixel = gradientImage.getPixel(x, 0);
      assertEquals(x, pixel.getRed());
      assertEquals(x, pixel.getGreen());
      assertEquals(x, pixel.getBlue());
    }
  }

  @Test
  public void testUniformColorImage() {
    Image uniformImage = new Image(3, 3);
    for (int x = 0; x < uniformImage.getWidth(); x++) {
      for (int y = 0; y < uniformImage.getHeight(); y++) {
        uniformImage.setPixel(x, y, new Pixel(127, 127, 127));
      }
    }

    int[][] histograms = histogram.calculateHistograms(uniformImage);

    assertEquals(9, histograms[0][127]);
    assertEquals(9, histograms[1][127]);
    assertEquals(9, histograms[2][127]);

    for (int x = 0; x < uniformImage.getWidth(); x++) {
      for (int y = 0; y < uniformImage.getHeight(); y++) {
        Pixel pixel = uniformImage.getPixel(x, y);
        assertEquals(127, pixel.getRed());
        assertEquals(127, pixel.getGreen());
        assertEquals(127, pixel.getBlue());
      }
    }
  }

  @Test
  public void testRandomColorImage() {
    Image randomImage = new Image(5, 5);
    int[][][] originalValues = new int[5][5][3];

    for (int x = 0; x < randomImage.getWidth(); x++) {
      for (int y = 0; y < randomImage.getHeight(); y++) {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        originalValues[x][y][0] = red;
        originalValues[x][y][1] = green;
        originalValues[x][y][2] = blue;

        randomImage.setPixel(x, y, new Pixel(red, green, blue));
      }
    }

    int[][] histograms = histogram.calculateHistograms(randomImage);

    for (int x = 0; x < randomImage.getWidth(); x++) {
      for (int y = 0; y < randomImage.getHeight(); y++) {
        Pixel pixel = randomImage.getPixel(x, y);

        assertEquals("Red value at (" + x + "," + y + ")", originalValues[x][y][0], pixel.getRed());
        assertEquals("Green value at (" + x + "," + y + ")", originalValues[x][y][1],
            pixel.getGreen());
        assertEquals("Blue value at (" + x + "," + y + ")", originalValues[x][y][2],
            pixel.getBlue());

        assertTrue("Histogram count for red at (" + x + "," + y + ")",
            histograms[0][pixel.getRed()] > 0);
        assertTrue("Histogram count for green at (" + x + "," + y + ")",
            histograms[1][pixel.getGreen()] > 0);
        assertTrue("Histogram count for blue at (" + x + "," + y + ")",
            histograms[2][pixel.getBlue()] > 0);
      }
    }
  }

  @Test
  public void testInvalidPixelValues() {
    Image invalidImage = new Image(2, 2);

    int[][] testValues = {{-10, 0, 0}, {0, -20, 0}, {0, 0, 300}, {266, 0, 0}};

    invalidImage.setPixel(0, 0, new Pixel(testValues[0][0], testValues[0][1], testValues[0][2]));
    invalidImage.setPixel(0, 1, new Pixel(testValues[1][0], testValues[1][1], testValues[1][2]));
    invalidImage.setPixel(1, 0, new Pixel(testValues[2][0], testValues[2][1], testValues[2][2]));
    invalidImage.setPixel(1, 1, new Pixel(testValues[3][0], testValues[3][1], testValues[3][2]));

    int[][] histograms = histogram.calculateHistograms(invalidImage);

    for (int x = 0; x < invalidImage.getWidth(); x++) {
      for (int y = 0; y < invalidImage.getHeight(); y++) {
        Pixel pixel = invalidImage.getPixel(x, y);

        int expectedRed = Math.min(255, Math.max(0, testValues[x * 2 + y][0]));
        int expectedGreen = Math.min(255, Math.max(0, testValues[x * 2 + y][1]));
        int expectedBlue = Math.min(255, Math.max(0, testValues[x * 2 + y][2]));

        assertEquals("Red value at (" + x + "," + y + ")", expectedRed, pixel.getRed());
        assertEquals("Green value at (" + x + "," + y + ")", expectedGreen, pixel.getGreen());
        assertEquals("Blue value at (" + x + "," + y + ")", expectedBlue, pixel.getBlue());

        if (expectedRed == 0) {
          assertTrue(histograms[0][0] > 0);
        }
        if (expectedRed == 255) {
          assertTrue(histograms[0][255] > 0);
        }
        if (expectedGreen == 0) {
          assertTrue(histograms[1][0] > 0);
        }
        if (expectedBlue == 0) {
          assertTrue(histograms[2][0] > 0);
        }
        if (expectedBlue == 255) {
          assertTrue(histograms[2][255] > 0);
        }
      }
    }

    assertEquals(3, histograms[0][0]);
    assertEquals(1, histograms[0][255]);
    assertEquals(4, histograms[1][0]);
    assertEquals(1, histograms[2][255]);
  }
}
