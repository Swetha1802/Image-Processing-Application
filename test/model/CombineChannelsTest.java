package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.CombineChannels;
import model.transformations.interfaces.CombineChannelsInterface;
import org.junit.Test;

/**
 * This class contains test cases for the CombineChannels class. The tests validate the
 * functionality of combining individual red, green, and blue channels to form a single RGB image.
 */

public class CombineChannelsTest {

  ImageOperations opn = new ImageOperations();
  CombineChannelsInterface cci = new CombineChannels();

  @Test
  public void testCombination() {
    // Create red, green, and blue channel images
    Image redImage = new Image(2, 2);
    redImage.setPixel(0, 0, new Pixel(255, 0, 0));
    redImage.setPixel(1, 0, new Pixel(255, 0, 0));
    redImage.setPixel(0, 1, new Pixel(255, 0, 0));
    redImage.setPixel(1, 1, new Pixel(255, 0, 0));
    opn.saveImage("red_image.png", redImage);

    Image greenImage = new Image(2, 2);
    greenImage.setPixel(0, 0, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 0, new Pixel(0, 255, 0));
    greenImage.setPixel(0, 1, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 1, new Pixel(0, 255, 0));
    opn.saveImage("green_image.png", greenImage);

    Image blueImage = new Image(2, 2);
    blueImage.setPixel(0, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(1, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(0, 1, new Pixel(0, 0, 255));
    blueImage.setPixel(1, 1, new Pixel(0, 0, 255));
    opn.saveImage("blue_image.png", blueImage);

    cci.rgbCombine("combined_image.png", "red_image.png", "green_image.png", "blue_image.png");
    Image combinedImage = opn.getImage("combined_image.png");

    assertEquals(255, combinedImage.getPixel(0, 0).getRed());
    assertEquals(255, combinedImage.getPixel(0, 0).getGreen());
    assertEquals(255, combinedImage.getPixel(0, 0).getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVaryingSizes() {
    Image redImage = new Image(2, 2);
    redImage.setPixel(0, 0, new Pixel(255, 0, 0));
    redImage.setPixel(0, 1, new Pixel(255, 0, 0));
    redImage.setPixel(1, 0, new Pixel(255, 0, 0));
    redImage.setPixel(1, 1, new Pixel(255, 0, 0));
    opn.saveImage("red_image.png", redImage);

    Image greenImage = new Image(2, 3);
    greenImage.setPixel(0, 0, new Pixel(0, 255, 0));
    greenImage.setPixel(0, 1, new Pixel(0, 255, 0));
    greenImage.setPixel(0, 2, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 0, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 1, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 2, new Pixel(0, 255, 0));
    opn.saveImage("green_image.png", greenImage);

    Image blueImage = new Image(3, 2);
    blueImage.setPixel(0, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(0, 1, new Pixel(0, 0, 255));
    blueImage.setPixel(1, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(1, 1, new Pixel(0, 0, 255));
    blueImage.setPixel(2, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(2, 1, new Pixel(0, 0, 255));
    opn.saveImage("blue_image.png", blueImage);

    cci.rgbCombine("combined_image.png", "red_image.png", "green_image.png", "blue_image.png");
  }

  @Test
  public void testCombineWhenImageIsNull() {
    Image redImage = new Image(2, 2);
    redImage.setPixel(0, 0, new Pixel(255, 0, 0));
    redImage.setPixel(0, 1, new Pixel(255, 0, 0));
    opn.saveImage("red_image.png", redImage);

    Image blueImage = new Image(2, 2);
    blueImage.setPixel(0, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(0, 1, new Pixel(0, 0, 255));
    opn.saveImage("blue_image.png", blueImage);

    assertThrows(IllegalArgumentException.class, () -> {
      cci.rgbCombine("combined_image.png", "red_image.png", null, "blue_image.png");
    });
  }

  @Test
  public void testImageDoesNotExist() {
    assertThrows(IllegalArgumentException.class, () -> {
      cci.rgbCombine("combined_image.png", "non_existent_red.png", "non_existent_green.png",
          "non_existent_blue.png");
    });
  }

  @Test
  public void testBlackChannels() {
    Image blackImage = new Image(2, 2);
    blackImage.setPixel(0, 0, new Pixel(0, 0, 0));
    blackImage.setPixel(0, 1, new Pixel(0, 0, 0));
    blackImage.setPixel(1, 0, new Pixel(0, 0, 0));
    blackImage.setPixel(1, 1, new Pixel(0, 0, 0));

    opn.saveImage("black_red_image.png", blackImage);
    opn.saveImage("black_green_image.png", blackImage);
    opn.saveImage("black_blue_image.png", blackImage);

    cci.rgbCombine("combined_black_image.png", "black_red_image.png", "black_green_image.png",
        "black_blue_image.png");
    Image combinedImage = opn.getImage("combined_black_image.png");

    assertEquals(0, combinedImage.getPixel(0, 0).getRed());
    assertEquals(0, combinedImage.getPixel(0, 0).getGreen());
    assertEquals(0, combinedImage.getPixel(0, 0).getBlue());
  }

  @Test
  public void testMaxValues() {
    Image redImage = new Image(2, 2);
    redImage.setPixel(0, 0, new Pixel(255, 0, 0));
    redImage.setPixel(1, 0, new Pixel(255, 0, 0));
    redImage.setPixel(0, 1, new Pixel(255, 0, 0));
    redImage.setPixel(1, 1, new Pixel(255, 0, 0));
    opn.saveImage("red_image.png", redImage);

    Image greenImage = new Image(2, 2);
    greenImage.setPixel(0, 0, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 0, new Pixel(0, 255, 0));
    greenImage.setPixel(0, 1, new Pixel(0, 255, 0));
    greenImage.setPixel(1, 1, new Pixel(0, 255, 0));
    opn.saveImage("green_image.png", greenImage);

    Image blueImage = new Image(2, 2);
    blueImage.setPixel(0, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(1, 0, new Pixel(0, 0, 255));
    blueImage.setPixel(0, 1, new Pixel(0, 0, 255));
    blueImage.setPixel(1, 1, new Pixel(0, 0, 255));
    opn.saveImage("blue_image.png", blueImage);

    cci.rgbCombine("combined_image.png", "red_image.png", "green_image.png", "blue_image.png");
    Image combinedImage = opn.getImage("combined_image.png");

    assertEquals(255, combinedImage.getPixel(0, 0).getRed());
    assertEquals(255, combinedImage.getPixel(0, 0).getGreen());
    assertEquals(255, combinedImage.getPixel(0, 0).getBlue());
  }

  @Test
  public void testLargeImage() {

    Image redImage = new Image(100, 100);
    Image greenImage = new Image(100, 100);
    Image blueImage = new Image(100, 100);

    for (int y = 0; y < 100; y++) {
      for (int x = 0; x < 100; x++) {
        redImage.setPixel(x, y, new Pixel(255, 0, 0));
        greenImage.setPixel(x, y, new Pixel(0, 255, 0));
        blueImage.setPixel(x, y, new Pixel(0, 0, 255));
      }
    }
    opn.saveImage("red_image.png", redImage);
    opn.saveImage("green_image.png", greenImage);
    opn.saveImage("blue_image.png", blueImage);

    cci.rgbCombine("combined_image.png", "red_image.png", "green_image.png", "blue_image.png");

    Image combinedImage = opn.getImage("combined_image.png");
    assertEquals(255, combinedImage.getPixel(0, 0).getRed());
    assertEquals(255, combinedImage.getPixel(0, 0).getGreen());
    assertEquals(255, combinedImage.getPixel(0, 0).getBlue());
  }


}