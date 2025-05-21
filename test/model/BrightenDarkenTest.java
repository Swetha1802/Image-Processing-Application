package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.BrightenDarken;
import model.transformations.interfaces.BrightenDarkenInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * The class below tests various scenarios of brightening and darkening operations.
 */

public class BrightenDarkenTest {

  ImageOperations opn = new ImageOperations();
  BrightenDarkenInterface bd = new BrightenDarken();
  private Image image;

  @Before
  public void setUp() {

    image = new Image(3, 3);
    image.setPixel(0, 0, new Pixel(0, 0, 0));
    image.setPixel(1, 1, new Pixel(150, 150, 150));
    image.setPixel(2, 2, new Pixel(200, 200, 200));

    opn.saveImage("testImage", image);
  }

  @Test
  public void testBrightenWithValidIncrement() {
    bd.brighten(50, "testImage", "brightenedImage");
    Image brightened = opn.getImage("brightenedImage");

    assertEquals(50, brightened.getPixel(0, 0).getRed());
    assertEquals(50, brightened.getPixel(0, 0).getGreen());
    assertEquals(50, brightened.getPixel(0, 0).getBlue());
    assertEquals(200, brightened.getPixel(1, 1).getRed());
    assertEquals(200, brightened.getPixel(1, 1).getGreen());
    assertEquals(200, brightened.getPixel(1, 1).getBlue());
    assertEquals(250, brightened.getPixel(2, 2).getRed());
    assertEquals(250, brightened.getPixel(2, 2).getGreen());
    assertEquals(250, brightened.getPixel(2, 2).getBlue()); // Clamping test
  }

  @Test
  public void testDarkenWithValidDecrement() {
    bd.darken(50, "testImage", "darkenedImage");
    Image darkened = opn.getImage("darkenedImage");

    assertEquals(0, darkened.getPixel(0, 0).getRed());
    assertEquals(0, darkened.getPixel(0, 0).getGreen());
    assertEquals(0, darkened.getPixel(0, 0).getBlue());
    assertEquals(100, darkened.getPixel(1, 1).getRed());
    assertEquals(100, darkened.getPixel(1, 1).getGreen());
    assertEquals(100, darkened.getPixel(1, 1).getBlue());
    assertEquals(150, darkened.getPixel(2, 2).getRed());
    assertEquals(150, darkened.getPixel(2, 2).getGreen());
    assertEquals(150, darkened.getPixel(2, 2).getBlue());
  }

  @Test
  public void testBrightenWithClamping() {
    bd.brighten(300, "testImage", "brightenedImage");
    Image brightened = opn.getImage("brightenedImage");

    assertEquals(255, brightened.getPixel(0, 0).getRed());
    assertEquals(255, brightened.getPixel(0, 0).getGreen());
    assertEquals(255, brightened.getPixel(0, 0).getBlue());
  }

  @Test
  public void testDarkenWithClamping() {
    bd.darken(200, "testImage", "darkenedImage");
    Image darkened = opn.getImage("darkenedImage");

    assertEquals(0, darkened.getPixel(0, 0).getRed());
    assertEquals(0, darkened.getPixel(0, 0).getGreen());
    assertEquals(0, darkened.getPixel(0, 0).getBlue());
  }


  @Test(expected = NullPointerException.class)
  public void testBrightenWithNullImageName() {
    bd.brighten(50, null, "brightenedImage"); // Null image name
  }

  @Test(expected = NullPointerException.class)
  public void testDarkenWithNullImageName() {
    bd.darken(50, null, "darkenedImage"); // Null image name
  }

  @Test(expected = NullPointerException.class)
  public void testBrightenWithNullDestImageName() {
    bd.brighten(50, "testImage", null); // Null destination image name
  }

  @Test(expected = NullPointerException.class)
  public void testDarkenWithNullDestImageName() {
    bd.darken(50, "testImage", null); // Null destination image name
  }

  @Test
  public void testBrightenWithZeroIncrement() {
    bd.brighten(0, "testImage", "brightenedImage"); // Valid increment

    Image resultImage = opn.getImage("brightenedImage");

    // Verify that the pixel values remain the same
    assertEquals(image.getPixel(0, 0).getRed(), resultImage.getPixel(0, 0).getRed());
    assertEquals(image.getPixel(0, 0).getGreen(), resultImage.getPixel(0, 0).getGreen());
    assertEquals(image.getPixel(0, 0).getBlue(), resultImage.getPixel(0, 0).getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenWithNonExistentImage() {
    bd.brighten(50, "nonExistentImage", "newImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDarkenWithNonExistentImage() {
    bd.darken(50, "nonExistentImage", "newImage");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBrightenWithInvalidImageName() {
    bd.brighten(50, "invalidImage", "brightenedImage"); // Should throw IllegalArgumentException
  }

  @Test
  public void testBrightenWithEdgeValues() {

    Image edgeImage = new Image(3, 3);
    edgeImage.setPixel(0, 0, new Pixel(255, 255, 255));
    edgeImage.setPixel(1, 1, new Pixel(0, 0, 0));
    edgeImage.setPixel(2, 2, new Pixel(128, 128, 128));

    opn.saveImage("edgeImage", edgeImage);

    bd.brighten(50, "edgeImage", "brightenedEdgeImage");
    Image brightened = opn.getImage("brightenedEdgeImage");

    assertEquals(255, brightened.getPixel(0, 0).getRed()); // Clamped at 255
    assertEquals(255, brightened.getPixel(0, 0).getGreen());
    assertEquals(255, brightened.getPixel(0, 0).getBlue());

    assertEquals(50, brightened.getPixel(1, 1).getRed()); // Clamped to 50
    assertEquals(50, brightened.getPixel(1, 1).getGreen());
    assertEquals(50, brightened.getPixel(1, 1).getBlue());

    assertEquals(178, brightened.getPixel(2, 2).getRed()); // 128 + 50
    assertEquals(178, brightened.getPixel(2, 2).getGreen());
    assertEquals(178, brightened.getPixel(2, 2).getBlue());
  }

  @Test
  public void testDarkenWithEdgeValues() {
    // Test darkening an image where pixels are at the edge of the value range
    Image edgeImage = new Image(3, 3);
    edgeImage.setPixel(0, 0, new Pixel(255, 255, 255)); // White pixel
    edgeImage.setPixel(1, 1, new Pixel(0, 0, 0)); // Black pixel
    edgeImage.setPixel(2, 2, new Pixel(128, 128, 128)); // Gray pixel

    opn.saveImage("edgeImage", edgeImage);

    bd.darken(50, "edgeImage", "darkenedEdgeImage");
    Image darkened = opn.getImage("darkenedEdgeImage");

    assertEquals(205, darkened.getPixel(0, 0).getRed()); // 255 - 50
    assertEquals(205, darkened.getPixel(0, 0).getGreen());
    assertEquals(205, darkened.getPixel(0, 0).getBlue());

    assertEquals(0, darkened.getPixel(1, 1).getRed()); // Clamped at 0
    assertEquals(0, darkened.getPixel(1, 1).getGreen());
    assertEquals(0, darkened.getPixel(1, 1).getBlue());

    assertEquals(78, darkened.getPixel(2, 2).getRed()); // 128 - 50
    assertEquals(78, darkened.getPixel(2, 2).getGreen());
    assertEquals(78, darkened.getPixel(2, 2).getBlue());
  }

  @Test
  public void testBrightenWithLargeIncrementAndCheckMultiplePixels() {
    // Initialize a new image
    Image multiplePixelImage = new Image(3, 3);
    multiplePixelImage.setPixel(0, 0, new Pixel(10, 10, 10)); // Dark pixel
    multiplePixelImage.setPixel(1, 0, new Pixel(20, 20, 20)); // Slightly brighter pixel
    multiplePixelImage.setPixel(2, 0, new Pixel(30, 30, 30)); // Brighter pixel
    multiplePixelImage.setPixel(0, 1, new Pixel(40, 40, 40));
    multiplePixelImage.setPixel(1, 1, new Pixel(50, 50, 50));
    multiplePixelImage.setPixel(2, 1, new Pixel(60, 60, 60));
    multiplePixelImage.setPixel(0, 2, new Pixel(70, 70, 70));
    multiplePixelImage.setPixel(1, 2, new Pixel(80, 80, 80));
    multiplePixelImage.setPixel(2, 2, new Pixel(90, 90, 90));

    opn.saveImage("multiplePixelImage", multiplePixelImage);

    bd.brighten(100, "multiplePixelImage", "brightenedMultipleImage");
    Image brightened = opn.getImage("brightenedMultipleImage");

    // Check specific pixels for expected values
    assertEquals(110, brightened.getPixel(0, 0).getRed()); // 10 + 100
    assertEquals(110, brightened.getPixel(0, 0).getGreen());
    assertEquals(110, brightened.getPixel(0, 0).getBlue());

    assertEquals(120, brightened.getPixel(1, 0).getRed()); // 20 + 100
    assertEquals(120, brightened.getPixel(1, 0).getGreen());
    assertEquals(120, brightened.getPixel(1, 0).getBlue());

    assertEquals(130, brightened.getPixel(2, 0).getRed()); // 30 + 100
    assertEquals(130, brightened.getPixel(2, 0).getGreen());
    assertEquals(130, brightened.getPixel(2, 0).getBlue());

  }

  @Test
  public void testDarkenWithLargeDecrementAndCheckMultiplePixels() {
    // Similar to the previous case, but testing darkening
    Image multiplePixelImage = new Image(3, 3);
    multiplePixelImage.setPixel(0, 0, new Pixel(150, 150, 150));
    multiplePixelImage.setPixel(1, 0, new Pixel(160, 160, 160));
    multiplePixelImage.setPixel(2, 0, new Pixel(170, 170, 170));
    multiplePixelImage.setPixel(0, 1, new Pixel(180, 180, 180));
    multiplePixelImage.setPixel(1, 1, new Pixel(190, 190, 190));
    multiplePixelImage.setPixel(2, 1, new Pixel(200, 200, 200));
    multiplePixelImage.setPixel(0, 2, new Pixel(210, 210, 210));
    multiplePixelImage.setPixel(1, 2, new Pixel(220, 220, 220));
    multiplePixelImage.setPixel(2, 2, new Pixel(230, 230, 230));

    opn.saveImage("multiplePixelImage", multiplePixelImage);

    bd.darken(100, "multiplePixelImage", "darkenedMultipleImage");
    Image darkened = opn.getImage("darkenedMultipleImage");

    // Check specific pixels for expected values
    assertEquals(50, darkened.getPixel(0, 0).getRed()); // 150 - 100
    assertEquals(50, darkened.getPixel(0, 0).getGreen());
    assertEquals(50, darkened.getPixel(0, 0).getBlue());

    assertEquals(60, darkened.getPixel(1, 0).getRed()); // 160 - 100
    assertEquals(60, darkened.getPixel(1, 0).getGreen());
    assertEquals(60, darkened.getPixel(1, 0).getBlue());

    assertEquals(70, darkened.getPixel(2, 0).getRed()); // 170 - 100
    assertEquals(70, darkened.getPixel(2, 0).getGreen());
    assertEquals(70, darkened.getPixel(2, 0).getBlue());

  }

  @Test
  public void testBrightenDarkenSequence() {

    Image sequenceImage = new Image(3, 3);
    sequenceImage.setPixel(0, 0, new Pixel(100, 100, 100));
    sequenceImage.setPixel(1, 1, new Pixel(150, 150, 150));
    sequenceImage.setPixel(2, 2, new Pixel(200, 200, 200));

    opn.saveImage("sequenceImage", sequenceImage);

    bd.brighten(50, "sequenceImage", "brightenedSequenceImage");
    Image brightened = opn.getImage("brightenedSequenceImage");

    assertEquals(150, brightened.getPixel(0, 0).getRed()); // 100 + 50
    assertEquals(150, brightened.getPixel(0, 0).getGreen());
    assertEquals(150, brightened.getPixel(0, 0).getBlue());

    bd.darken(100, "brightenedSequenceImage", "darkenedSequenceImage");
    Image darkened = opn.getImage("darkenedSequenceImage");

    assertEquals(50, darkened.getPixel(0, 0).getRed()); // 150 - 100
    assertEquals(50, darkened.getPixel(0, 0).getGreen());
    assertEquals(50, darkened.getPixel(0, 0).getBlue());
  }

}