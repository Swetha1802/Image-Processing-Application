package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.ColorRepresentation;
import model.transformations.interfaces.ColorRepresentationInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the ColorRepresentation class. It validates the color
 * component extraction methods (value, intensity, and luma) error handling for invalid input  and
 * make sure that images with varying colors and pixel values are processed correctly.
 */


public class ColorRepresentationTest {

  ImageOperations opn = new ImageOperations();
  ColorRepresentationInterface cr = new ColorRepresentation();

  private String testImageName = "testImage";
  private String destImageName = "destImage";

  @Before
  public void setUp() {
    Image testImage;
    testImage = new Image(3, 3);
    testImage.setPixel(0, 0, new Pixel(255, 0, 0));
    testImage.setPixel(1, 0, new Pixel(0, 255, 0));
    testImage.setPixel(2, 0, new Pixel(0, 0, 255));
    testImage.setPixel(0, 1, new Pixel(255, 255, 0));
    testImage.setPixel(1, 1, new Pixel(0, 255, 255));
    testImage.setPixel(2, 1, new Pixel(255, 0, 255));
    opn.saveImage(testImageName, testImage);
  }

  @Test
  public void testValueComponent() {
    cr.valueComponent(testImageName, destImageName);
    Image result = opn.getImage(destImageName);
    assertEquals(255, result.getPixel(0, 0).getRed()); // Red
    assertEquals(255, result.getPixel(1, 0).getRed()); // Green
    assertEquals(255, result.getPixel(2, 0).getRed()); // Blue
    assertEquals(255, result.getPixel(0, 1).getRed()); // Yellow
    assertEquals(255, result.getPixel(1, 1).getRed()); // Cyan
    assertEquals(255, result.getPixel(2, 1).getRed()); // Magenta
  }

  @Test
  public void testIntensityComponent() {
    cr.intensityComponent(testImageName, destImageName);
    Image result = opn.getImage(destImageName);
    assertEquals(85, result.getPixel(0, 0).getRed());
    assertEquals(85, result.getPixel(1, 0).getRed());
    assertEquals(85, result.getPixel(2, 0).getRed());
  }

  @Test
  public void testLumaComponent() {
    cr.lumaComponent(testImageName, destImageName);
    Image result = opn.getImage(destImageName);
    assertEquals(54, result.getPixel(0, 0).getRed());
    assertEquals(182, result.getPixel(1, 0).getRed());
    assertEquals(18, result.getPixel(2, 0).getRed());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullImageName() {
    cr.valueComponent(null, destImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonExistentImage() {
    cr.valueComponent("invalidImage", destImageName);
  }


  @Test
  public void testImageWithOutOfBoundsPixelValues() {
    Image outOfBoundsImage = new Image(1, 1);
    outOfBoundsImage.setPixel(0, 0, new Pixel(300, -20, 256));
    opn.saveImage("outOfBoundsImage", outOfBoundsImage);

    cr.valueComponent("outOfBoundsImage", "outOfBoundsValueDest");
    Image result = opn.getImage("outOfBoundsValueDest");
    assertEquals(255, result.getPixel(0, 0).getRed());
  }

  @Test
  public void testMultipleComponentExtractions() {
    cr.valueComponent(testImageName, "valueDest");
    cr.intensityComponent(testImageName, "intensityDest");
    cr.lumaComponent(testImageName, "lumaDest");

    Image valueResult = opn.getImage("valueDest");
    Image intensityResult = opn.getImage("intensityDest");
    Image lumaResult = opn.getImage("lumaDest");

    assertNotNull(valueResult);
    assertNotNull(intensityResult);
    assertNotNull(lumaResult);
  }

  @Test
  public void testVaryingRGBValues() {
    Image varyingImage = new Image(2, 2);
    varyingImage.setPixel(0, 0, new Pixel(100, 150, 200));
    varyingImage.setPixel(1, 0, new Pixel(50, 75, 100));
    varyingImage.setPixel(0, 1, new Pixel(25, 50, 75));
    varyingImage.setPixel(1, 1, new Pixel(0, 0, 0));
    opn.saveImage("varyingImage", varyingImage);

    cr.valueComponent("varyingImage", "ValueDest");
    cr.intensityComponent("varyingImage", "IntensityDest");
    cr.lumaComponent("varyingImage", "LumaDest");

    assertEquals(200, opn.getImage("ValueDest").getPixel(0, 0).getRed());
    assertEquals(150, opn.getImage("IntensityDest").getPixel(0, 0).getRed());
    assertEquals(142, opn.getImage("LumaDest").getPixel(0, 0).getRed());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeImageDimensions() {
    Image invalidImage = new Image(-1, -1);
    cr.valueComponent("invalidImage", "destImage");
  }

  @Test
  public void testColorComponentHandlingNegativeValues() {
    Image negativeImage = new Image(1, 1);
    negativeImage.setPixel(0, 0, new Pixel(-50, -50, -50)); // Negative values
    opn.saveImage("negativeImage", negativeImage);

    cr.valueComponent("negativeImage", "negativeValueDest");
    cr.intensityComponent("negativeImage", "negativeIntensityDest");
    cr.lumaComponent("negativeImage", "negativeLumaDest");

    assertEquals(0, opn.getImage("negativeValueDest").getPixel(0, 0).getRed());
    assertEquals(0, opn.getImage("negativeIntensityDest").getPixel(0, 0).getRed());
    assertEquals(0, opn.getImage("negativeLumaDest").getPixel(0, 0).getRed());
  }

  @Test
  public void testGreyscaleImage() {
    Image grayImage = new Image(2, 2);
    grayImage.setPixel(0, 0, new Pixel(100, 100, 100));
    grayImage.setPixel(1, 0, new Pixel(150, 150, 150));
    grayImage.setPixel(0, 1, new Pixel(200, 200, 200));
    grayImage.setPixel(1, 1, new Pixel(255, 255, 255));
    opn.saveImage("grayImage", grayImage);

    cr.valueComponent("grayImage", "grayValueDest");
    cr.intensityComponent("grayImage", "grayIntensityDest");
    cr.lumaComponent("grayImage", "grayLumaDest");

    assertEquals(100, opn.getImage("grayValueDest").getPixel(0, 0).getRed());
    assertEquals(100, opn.getImage("grayIntensityDest").getPixel(0, 0).getRed());
    assertEquals(100, opn.getImage("grayLumaDest").getPixel(0, 0).getRed());
  }

  @Test
  public void testNonUniformColors() {
    Image nonUniformImage = new Image(3, 3);
    nonUniformImage.setPixel(0, 0, new Pixel(255, 0, 0));
    nonUniformImage.setPixel(1, 0, new Pixel(0, 255, 0));
    nonUniformImage.setPixel(2, 0, new Pixel(0, 0, 255));
    nonUniformImage.setPixel(0, 1, new Pixel(255, 255, 0));
    nonUniformImage.setPixel(1, 1, new Pixel(255, 0, 255));
    nonUniformImage.setPixel(2, 1, new Pixel(0, 255, 255));
    nonUniformImage.setPixel(0, 2, new Pixel(255, 255, 255));
    nonUniformImage.setPixel(1, 2, new Pixel(0, 0, 0));
    nonUniformImage.setPixel(2, 2, new Pixel(128, 128, 128));
    opn.saveImage("nonUniformImage", nonUniformImage);

    cr.valueComponent("nonUniformImage", "ValueDest");
    cr.intensityComponent("nonUniformImage", "IntensityDest");
    cr.lumaComponent("nonUniformImage", "LumaDest");

    assertEquals(255, opn.getImage("ValueDest").getPixel(0, 0).getRed());
    assertEquals(170, opn.getImage("IntensityDest").getPixel(1, 1).getRed());
    assertEquals(128, opn.getImage("LumaDest").getPixel(2, 2).getRed());
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testInvalidPixelAccess() {
    Image smallImage = new Image(2, 2);
    smallImage.setPixel(0, 0, new Pixel(100, 100, 100));
    smallImage.setPixel(1, 1, new Pixel(200, 200, 200));
    opn.saveImage("smallImage", smallImage);

    smallImage.getPixel(2, 2);
  }

  @Test
  public void testImageWithDifferentWidthAndHeight() {
    Image nonSquareImage = new Image(3, 5);
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 3; x++) {
        nonSquareImage.setPixel(x, y, new Pixel(x * 20, y * 50, (x + y) * 10));
      }
    }
    opn.saveImage("nonSquareImage", nonSquareImage);

    cr.valueComponent("nonSquareImage", "nonSquareValueDest");
    cr.intensityComponent("nonSquareImage", "nonSquareIntensityDest");
    cr.lumaComponent("nonSquareImage", "nonSquareLumaDest");

    assertEquals(100, opn.getImage("nonSquareValueDest").getPixel(1, 2).getRed());
    assertEquals(80, opn.getImage("nonSquareIntensityDest").getPixel(2, 3).getRed());
    assertEquals(145, opn.getImage("nonSquareLumaDest").getPixel(0, 4).getRed());
  }

  @Test
  public void testBlackWhiteImage() {
    Image mixedImage = new Image(3, 3);
    mixedImage.setPixel(0, 0, new Pixel(0, 0, 0));   // Black
    mixedImage.setPixel(1, 0, new Pixel(255, 255, 255)); // White
    mixedImage.setPixel(2, 0, new Pixel(0, 0, 0));   // Black
    mixedImage.setPixel(0, 1, new Pixel(255, 255, 255)); // White
    mixedImage.setPixel(1, 1, new Pixel(0, 0, 0));   // Black
    mixedImage.setPixel(2, 1, new Pixel(255, 255, 255)); // White
    mixedImage.setPixel(0, 2, new Pixel(0, 0, 0));   // Black
    mixedImage.setPixel(1, 2, new Pixel(255, 255, 255)); // White
    mixedImage.setPixel(2, 2, new Pixel(0, 0, 0));   // Black
    opn.saveImage("mixedBlackWhiteImage", mixedImage);

    cr.valueComponent("mixedBlackWhiteImage", "ValueDest");
    cr.intensityComponent("mixedBlackWhiteImage", "IntensityDest");
    cr.lumaComponent("mixedBlackWhiteImage", "LumaDest");

    assertEquals(255, opn.getImage("ValueDest").getPixel(0, 1).getRed()); // White pixel
    assertEquals(0, opn.getImage("IntensityDest").getPixel(0, 0).getRed()); // Black pixel
    assertEquals(0, opn.getImage("LumaDest").getPixel(1, 1).getRed()); // Average case
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyImage() {
    Image emptyImage = new Image(0, 0);
    opn.saveImage("emptyImage", emptyImage);
    cr.valueComponent("emptyImage", "ValueDest");
  }

}
