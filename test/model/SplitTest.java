package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.Split;
import model.transformations.interfaces.SplitInterface;
import org.junit.Test;

/**
 * SplitTest is a JUnit test class that verifies the functionality of the RGB split operation on
 * images. It tests the correctness of splitting an image into its red, green, and blue channels by
 * comparing the expected pixel values in the resulting images with the actual values after
 * performing the split.
 **/


public class SplitTest {

  ImageOperations opn = new ImageOperations();
  SplitInterface spl = new Split();

  @Test
  public void testNormalSplit() {

    Image original = new Image(2, 2);
    original.setPixel(0, 0, new Pixel(255, 0, 0));
    original.setPixel(0, 1, new Pixel(0, 255, 0));
    original.setPixel(1, 0, new Pixel(0, 0, 255));
    original.setPixel(1, 1, new Pixel(255, 255, 0));
    opn.saveImage("original.png", original);

    spl.rgbSplit("original.png", "red.png", "green.png", "blue.png");

    Image redImage = opn.getImage("red.png");
    Image greenImage = opn.getImage("green.png");
    Image blueImage = opn.getImage("blue.png");

    assertEquals(255, redImage.getPixel(0, 0).getRed());
    assertEquals(255, redImage.getPixel(0, 0).getGreen());
    assertEquals(255, redImage.getPixel(0, 0).getBlue());

    assertEquals(0, redImage.getPixel(0, 1).getRed());
    assertEquals(0, redImage.getPixel(0, 1).getGreen());
    assertEquals(0, redImage.getPixel(0, 1).getBlue());

    assertEquals(0, redImage.getPixel(1, 0).getRed());
    assertEquals(0, redImage.getPixel(1, 0).getGreen());
    assertEquals(0, redImage.getPixel(1, 0).getBlue());

    assertEquals(255, redImage.getPixel(1, 1).getRed());
    assertEquals(255, redImage.getPixel(1, 1).getGreen());
    assertEquals(255, redImage.getPixel(1, 1).getBlue());

    assertEquals(0, greenImage.getPixel(0, 0).getRed());
    assertEquals(0, greenImage.getPixel(0, 0).getGreen());
    assertEquals(0, greenImage.getPixel(0, 0).getBlue());

    assertEquals(255, greenImage.getPixel(0, 1).getRed());
    assertEquals(255, greenImage.getPixel(0, 1).getGreen());
    assertEquals(255, greenImage.getPixel(0, 1).getBlue());

    assertEquals(0, greenImage.getPixel(1, 0).getRed());
    assertEquals(0, greenImage.getPixel(1, 0).getGreen());
    assertEquals(0, greenImage.getPixel(1, 0).getBlue());

    assertEquals(255, greenImage.getPixel(1, 1).getRed());
    assertEquals(255, greenImage.getPixel(1, 1).getGreen());
    assertEquals(255, greenImage.getPixel(1, 1).getBlue());

    assertEquals(0, blueImage.getPixel(0, 0).getRed());
    assertEquals(0, blueImage.getPixel(0, 0).getGreen());
    assertEquals(0, blueImage.getPixel(0, 0).getBlue());

    assertEquals(0, blueImage.getPixel(0, 1).getRed());
    assertEquals(0, blueImage.getPixel(0, 1).getGreen());
    assertEquals(0, blueImage.getPixel(0, 1).getBlue());

    assertEquals(255, blueImage.getPixel(1, 0).getRed());
    assertEquals(255, blueImage.getPixel(1, 0).getGreen());
    assertEquals(255, blueImage.getPixel(1, 0).getBlue());

    assertEquals(0, blueImage.getPixel(1, 1).getRed());
    assertEquals(0, blueImage.getPixel(1, 1).getGreen());
    assertEquals(0, blueImage.getPixel(1, 1).getBlue());


  }


  @Test
  public void testSingleColorImage() {
    Image redImage = new Image(2, 2);
    redImage.setPixel(0, 0, new Pixel(255, 0, 0));
    redImage.setPixel(0, 1, new Pixel(255, 0, 0));
    redImage.setPixel(1, 0, new Pixel(255, 0, 0));
    redImage.setPixel(1, 1, new Pixel(255, 0, 0));
    opn.saveImage("red_only.png", redImage);

    spl.rgbSplit("red_only.png", "red_split.png", "green_split.png", "blue_split.png");

    Image splitRed = opn.getImage("red_split.png");
    assertEquals(255, splitRed.getPixel(0, 0).getRed());
  }

  @Test
  public void testGrayscaleImage() {
    Image grayImage = new Image(2, 2);
    grayImage.setPixel(0, 0, new Pixel(128, 128, 128));
    grayImage.setPixel(0, 1, new Pixel(128, 128, 128));
    grayImage.setPixel(1, 0, new Pixel(128, 128, 128));
    grayImage.setPixel(1, 1, new Pixel(128, 128, 128));
    opn.saveImage("gray.png", grayImage);

    spl.rgbSplit("gray.png", "red_gray.png", "green_gray.png", "blue_gray.png");

    Image redImage = opn.getImage("red_gray.png");
    Image greenImage = opn.getImage("green_gray.png");
    Image blueImage = opn.getImage("blue_gray.png");

    assertEquals(128, redImage.getPixel(0, 0).getRed());
    assertEquals(128, greenImage.getPixel(0, 0).getGreen());
    assertEquals(128, blueImage.getPixel(0, 0).getBlue());
  }

  @Test
  public void testBlackPixelsImage() {
    Image blackImage = new Image(2, 2);
    blackImage.setPixel(0, 0, new Pixel(0, 0, 0));
    blackImage.setPixel(0, 1, new Pixel(0, 0, 0));
    blackImage.setPixel(1, 0, new Pixel(0, 0, 0));
    blackImage.setPixel(1, 1, new Pixel(0, 0, 0));
    opn.saveImage("black.png", blackImage);

    spl.rgbSplit("black.png", "red_black.png", "green_black.png", "blue_black.png");

    Image redImage = opn.getImage("red_black.png");
    Image greenImage = opn.getImage("green_black.png");
    Image blueImage = opn.getImage("blue_black.png");

    assertEquals(0, redImage.getPixel(0, 0).getRed());
    assertEquals(0, greenImage.getPixel(0, 0).getGreen());
    assertEquals(0, blueImage.getPixel(0, 0).getBlue());
  }

  @Test
  public void testWhitePixelsImage() {
    Image whiteImage = new Image(2, 2);
    whiteImage.setPixel(0, 0, new Pixel(255, 255, 255));
    whiteImage.setPixel(0, 1, new Pixel(255, 255, 255));
    whiteImage.setPixel(1, 0, new Pixel(255, 255, 255));
    whiteImage.setPixel(1, 1, new Pixel(255, 255, 255));
    opn.saveImage("white.png", whiteImage);

    spl.rgbSplit("white.png", "red_white.png", "green_white.png", "blue_white.png");

    Image redImage = opn.getImage("red_white.png");
    Image greenImage = opn.getImage("green_white.png");
    Image blueImage = opn.getImage("blue_white.png");

    assertEquals(255, redImage.getPixel(0, 0).getRed());
    assertEquals(255, greenImage.getPixel(0, 0).getGreen());
    assertEquals(255, blueImage.getPixel(0, 0).getBlue());
  }


  @Test
  public void testLargeImage() {
    Image largeImage = new Image(1000, 1000);
    for (int y = 0; y < 1000; y++) {
      for (int x = 0; x < 1000; x++) {
        largeImage.setPixel(x, y, new Pixel(x % 256, y % 256, (x + y) % 256));
      }
    }
    opn.saveImage("large_image.png", largeImage);

    spl.rgbSplit("large_image.png", "red_large.png", "green_large.png", "blue_large.png");

    Image redImage = opn.getImage("red_large.png");
    Image greenImage = opn.getImage("green_large.png");
    Image blueImage = opn.getImage("blue_large.png");

    assertEquals(2, redImage.getPixel(2, 2).getRed());
    assertEquals(2, greenImage.getPixel(2, 2).getGreen());
    assertEquals(4, blueImage.getPixel(2, 2).getBlue());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNonexistentImage() {
    spl.rgbSplit("nonexistent.png", "red.png", "green.png", "blue.png");
  }

  @Test
  public void testSinglePixelImage() {
    Image original = new Image(1, 1);
    original.setPixel(0, 0, new Pixel(128, 64, 32));
    opn.saveImage("single_pixel.png", original);

    spl.rgbSplit("single_pixel.png", "red_single.png", "green_single.png", "blue_single.png");

    Image redImage = opn.getImage("red_single.png");
    Image greenImage = opn.getImage("green_single.png");
    Image blueImage = opn.getImage("blue_single.png");

    assertEquals(128, redImage.getPixel(0, 0).getRed());
    assertEquals(128, redImage.getPixel(0, 0).getGreen());
    assertEquals(128, redImage.getPixel(0, 0).getBlue());

    assertEquals(64, greenImage.getPixel(0, 0).getRed());
    assertEquals(64, greenImage.getPixel(0, 0).getGreen());
    assertEquals(64, greenImage.getPixel(0, 0).getBlue());

    assertEquals(32, blueImage.getPixel(0, 0).getRed());
    assertEquals(32, blueImage.getPixel(0, 0).getGreen());
    assertEquals(32, blueImage.getPixel(0, 0).getBlue());
  }

}
