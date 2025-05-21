package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.Flipping;
import model.transformations.interfaces.FlippingInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit4 Test for flipping class.
 */

public class FlippingTest {

  ImageOperations opn = new ImageOperations();
  FlippingInterface flip = new Flipping();
  private String sourceImageName = "sourceImage";
  private String destImageName = "flippedImage";

  @Before
  public void setUp() {
    Image image = new Image(3, 2);
    image.setPixel(0, 0, new Pixel(255, 0, 0)); // Red
    image.setPixel(1, 0, new Pixel(0, 255, 0)); // Green
    image.setPixel(2, 0, new Pixel(0, 0, 255)); // Blue
    image.setPixel(0, 1, new Pixel(255, 255, 0)); // Yellow
    image.setPixel(1, 1, new Pixel(0, 255, 255)); // Cyan
    image.setPixel(2, 1, new Pixel(255, 0, 255)); // Magenta

    opn.saveImage(sourceImageName, image);

    Image image2 = new Image(3, 3);
    image.setPixel(0, 0, new Pixel(255, 0, 0));
    image.setPixel(1, 0, new Pixel(0, 255, 0));
    image.setPixel(2, 0, new Pixel(0, 0, 255));
    image.setPixel(0, 1, new Pixel(255, 255, 0));
    image.setPixel(1, 1, new Pixel(0, 255, 255));
    image.setPixel(2, 1, new Pixel(255, 0, 255));
    image.setPixel(0, 2, new Pixel(255, 255, 255));
    image.setPixel(1, 2, new Pixel(0, 0, 0));
    image.setPixel(2, 2, new Pixel(127, 127, 127));

    opn.saveImage(sourceImageName, image2);
  }

  @Test
  public void testHorizontalFlip() {
    flip.horizontalFlip(sourceImageName, destImageName);

    Image flippedImage = opn.getImage(destImageName);

    assertEquals(0, flippedImage.getPixel(0, 0).getRed());
    assertEquals(0, flippedImage.getPixel(1, 0).getRed());
    assertEquals(255, flippedImage.getPixel(2, 0).getRed());

  }


  @Test
  public void testVerticalFlip() {
    flip.verticalFlip(sourceImageName, destImageName);

    Image flippedImage = opn.getImage(destImageName);

    assertEquals(255, flippedImage.getPixel(0, 0).getRed());
    assertEquals(0, flippedImage.getPixel(1, 0).getRed());
    assertEquals(255, flippedImage.getPixel(2, 0).getRed());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageName() {
    flip.horizontalFlip(null, destImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDestinationImageName() {
    flip.horizontalFlip(sourceImageName, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonExistentImage() {
    flip.horizontalFlip("invalidImage", destImageName);
  }

  @Test
  public void testSinglePixelFlip() {
    Image singlePixelImage = new Image(1, 1);
    singlePixelImage.setPixel(0, 0, new Pixel(255, 0, 0));
    opn.saveImage(sourceImageName, singlePixelImage);

    flip.horizontalFlip(sourceImageName, destImageName);
    Image flippedImageH = opn.getImage(destImageName);
    assertEquals(255, flippedImageH.getPixel(0, 0).getRed());

    flip.verticalFlip(sourceImageName, destImageName);
    Image flippedImageV = opn.getImage(destImageName);
    assertEquals(255, flippedImageV.getPixel(0, 0).getRed());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyImageFlip() {
    Image emptyImage = new Image(0, 0);
    opn.saveImage(sourceImageName, emptyImage);
    flip.horizontalFlip(sourceImageName, destImageName);
  }


}
