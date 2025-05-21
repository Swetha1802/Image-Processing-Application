package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit4 class for the testing of Image class.
 */

public class ImageTest {

  private Image image;

  @Before
  public void setup() {
    image = new Image(3, 3);
  }


  @Test
  public void testSetAndGetPixel() {
    Pixel pixel = new Pixel(100, 150, 200);
    image.setPixel(1, 1, pixel);

    Pixel retrievedPixel = image.getPixel(1, 1);
    assertEquals(pixel.getRed(), retrievedPixel.getRed());
    assertEquals(pixel.getGreen(), retrievedPixel.getGreen());
    assertEquals(pixel.getBlue(), retrievedPixel.getBlue());
  }


  @Test
  public void testImageResize() {
    image = new Image(5, 5);
    assertEquals(5, image.getWidth());
    assertEquals(5, image.getHeight());

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        assertNotNull(image.getPixel(x, y));
        assertEquals(0, image.getPixel(x, y).getRed());
        assertEquals(0, image.getPixel(x, y).getGreen());
        assertEquals(0, image.getPixel(x, y).getBlue());
      }
    }
  }

  @Test
  public void testImageWithDifferentSizes() {
    Image image2 = new Image(4, 4);
    assertEquals(4, image2.getWidth());
    assertEquals(4, image2.getHeight());

    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        assertNotNull(image2.getPixel(x, y));
        assertEquals(0, image2.getPixel(x, y).getRed());
        assertEquals(0, image2.getPixel(x, y).getGreen());
        assertEquals(0, image2.getPixel(x, y).getBlue());
      }
    }
  }

  @Test
  public void testInitializePixels() {
    Image newImage = new Image(2, 2);
    assertNotNull(newImage.getPixel(0, 0));
    assertEquals(0, newImage.getPixel(0, 0).getRed());
    assertEquals(0, newImage.getPixel(0, 0).getGreen());
    assertEquals(0, newImage.getPixel(0, 0).getBlue());
  }

  @Test
  public void testSetNullPixel() {
    try {
      image.setPixel(1, 1, null);
      fail("Expected an exception to be thrown");
    } catch (NullPointerException e) {
      assertEquals("Pixel cannot be null", e.getMessage());
    }
  }

  @Test
  public void testSetPixelAndVerify() {
    Pixel pixel = new Pixel(255, 255, 255);
    image.setPixel(0, 0, pixel);

    assertEquals(pixel, image.getPixel(0, 0));
  }

  @Test
  public void testAllBlackPixels() {
    Image image = new Image(5, 5);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        assertEquals(0, image.getPixel(x, y).getRed());
        assertEquals(0, image.getPixel(x, y).getGreen());
        assertEquals(0, image.getPixel(x, y).getBlue());
      }
    }
  }


  @Test
  public void testImageWithZeroDimensions() {
    try {
      Image zeroImage = new Image(0, 0);
      fail("Expected an exception to be thrown for zero dimensions");
    } catch (IllegalArgumentException e) {
      assertEquals("Width and height must be positive", e.getMessage());
    }
  }

  @Test
  public void testImageWithNegativeDimensions() {
    try {
      Image negativeImage = new Image(-1, 5);
      fail("Expected an exception to be thrown for negative width");
    } catch (IllegalArgumentException e) {
      assertEquals("Width and height must be positive", e.getMessage());
    }

    try {
      Image negativeImage = new Image(5, -1);
      fail("Expected an exception to be thrown for negative height");
    } catch (IllegalArgumentException e) {
      assertEquals("Width and height must be positive", e.getMessage());
    }
  }


  @Test
  public void testSetAndGetMultiplePixels() {
    Pixel redPixel = new Pixel(255, 0, 0);
    Pixel greenPixel = new Pixel(0, 255, 0);
    Pixel bluePixel = new Pixel(0, 0, 255);

    image.setPixel(0, 0, redPixel);
    image.setPixel(1, 1, greenPixel);
    image.setPixel(2, 2, bluePixel);

    assertEquals(redPixel, image.getPixel(0, 0));
    assertEquals(greenPixel, image.getPixel(1, 1));
    assertEquals(bluePixel, image.getPixel(2, 2));
  }

  @Test
  public void testSettingSamePixelMultipleTimes() {
    Pixel pixel = new Pixel(100, 150, 200);
    image.setPixel(1, 1, pixel);
    image.setPixel(1, 1, pixel);

    assertEquals(pixel, image.getPixel(1, 1));
  }


  @Test
  public void testPixelColorValueRange() {
    Pixel pixel = new Pixel(256, -1, 100);
    assertTrue(pixel.getRed() >= 0 && pixel.getRed() <= 255);
    assertTrue(pixel.getGreen() >= 0 && pixel.getGreen() <= 255);
    assertTrue(pixel.getBlue() >= 0 && pixel.getBlue() <= 255);
  }


}
