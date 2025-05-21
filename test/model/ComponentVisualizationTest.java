package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.classes.ComponentVisualization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for verifying the behavior of component visualization operations
 * (red, green, and blue) on an image, including masking functionality.
 */
public class ComponentVisualizationTest {

  private ImageOperations opn;
  private ComponentVisualization componentVisualization;

  @BeforeEach
  public void setUp() {
    opn = new ImageOperations();
    componentVisualization = new ComponentVisualization();
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
    Image mask = new Image(2, 2);
    if (isBlack) {
      mask.setPixel(0, 0, new Pixel(0, 0, 0));
      mask.setPixel(0, 1, new Pixel(0, 0, 0));
      mask.setPixel(1, 0, new Pixel(0, 0, 0));
      mask.setPixel(1, 1, new Pixel(0, 0, 0));
    } else {
      mask.setPixel(0, 0, new Pixel(255, 255, 255));
      mask.setPixel(0, 1, new Pixel(255, 255, 255));
      mask.setPixel(1, 0, new Pixel(255, 255, 255));
      mask.setPixel(1, 1, new Pixel(255, 255, 255));
    }
    return mask;
  }

  // Existing Tests (Component Visualization without Mask)

  @Test
  public void testRedComponent() {
    Image result = new Image(2, 2);

    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Pixel pixel = createTestImage().getPixel(x, y);
        int red = pixel.getRed();
        result.setPixel(x, y, new Pixel(red, 0, 0));
      }
    }

    Assertions.assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(0, result.getPixel(0, 0).getGreen());
    assertEquals(0, result.getPixel(0, 0).getBlue());
    assertEquals(50, result.getPixel(0, 1).getRed());
    assertEquals(0, result.getPixel(0, 1).getGreen());
    assertEquals(0, result.getPixel(0, 1).getBlue());
  }

  @Test
  public void testGreenComponent() {
    Image result = new Image(2, 2);

    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Pixel pixel = createTestImage().getPixel(x, y);
        int green = pixel.getGreen();
        result.setPixel(x, y, new Pixel(0, green, 0));
      }
    }

    assertEquals(150, result.getPixel(0, 0).getGreen());
    assertEquals(0, result.getPixel(0, 0).getRed());
    assertEquals(0, result.getPixel(0, 0).getBlue());
    assertEquals(50, result.getPixel(0, 1).getGreen());
    assertEquals(0, result.getPixel(0, 1).getRed());
    assertEquals(0, result.getPixel(0, 1).getBlue());
  }

  @Test
  public void testBlueComponent() {
    Image result = new Image(2, 2);

    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Pixel pixel = createTestImage().getPixel(x, y);
        int blue = pixel.getBlue();
        result.setPixel(x, y, new Pixel(0, 0, blue));
      }
    }

    assertEquals(200, result.getPixel(0, 0).getBlue());
    assertEquals(0, result.getPixel(0, 0).getRed());
    assertEquals(0, result.getPixel(0, 0).getGreen());
    assertEquals(50, result.getPixel(0, 1).getBlue());
    assertEquals(0, result.getPixel(0, 1).getRed());
    assertEquals(0, result.getPixel(0, 1).getGreen());
  }

  @Test
  public void testOnePixelImage() {
    Image onePixelImage = new Image(1, 1);
    onePixelImage.setPixel(0, 0, new Pixel(100, 150, 200));

    Image result = new Image(1, 1);
    Pixel pixel = onePixelImage.getPixel(0, 0);
    int red = pixel.getRed();
    result.setPixel(0, 0, new Pixel(red, 0, 0));

    assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(0, result.getPixel(0, 0).getGreen());
    assertEquals(0, result.getPixel(0, 0).getBlue());
  }

  @Test
  public void testBlackImage() {
    Image blackImage = new Image(2, 2);
    blackImage.setPixel(0, 0, new Pixel(0, 0, 0));
    blackImage.setPixel(0, 1, new Pixel(0, 0, 0));
    blackImage.setPixel(1, 0, new Pixel(0, 0, 0));
    blackImage.setPixel(1, 1, new Pixel(0, 0, 0));

    Image result = new Image(2, 2);
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Pixel pixel = blackImage.getPixel(x, y);
        result.setPixel(x, y, new Pixel(pixel.getRed(), 0, 0));
      }
    }
    assertEquals(0, result.getPixel(0, 0).getRed());
    assertEquals(0, result.getPixel(0, 0).getGreen());
    assertEquals(0, result.getPixel(0, 0).getBlue());
  }

  @Test
  public void testWhiteImage() {
    Image whiteImage = new Image(2, 2);
    whiteImage.setPixel(0, 0, new Pixel(255, 255, 255));
    whiteImage.setPixel(0, 1, new Pixel(255, 255, 255));
    whiteImage.setPixel(1, 0, new Pixel(255, 255, 255));
    whiteImage.setPixel(1, 1, new Pixel(255, 255, 255));

    Image result = new Image(2, 2);
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        Pixel pixel = whiteImage.getPixel(x, y);
        result.setPixel(x, y, new Pixel(0, pixel.getGreen(), 0));
      }
    }

    assertEquals(255, result.getPixel(0, 0).getGreen());
    assertEquals(0, result.getPixel(0, 0).getRed());
    assertEquals(0, result.getPixel(0, 0).getBlue());
  }


  @Test
  public void testRedComponentWithMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    componentVisualization.redComponent("original_image.png", "red_output_with_mask.png",
        "mask_image.png");

    Image result = opn.getImage("red_output_with_mask.png");

    Pixel[][] expectedPixels = {
        {new Pixel(100, 100, 100), new Pixel(255, 255, 255)},
        {new Pixel(50, 50, 50), new Pixel(0, 0, 0)}
    };

    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        Pixel actual = result.getPixel(x, y);
        Pixel expected = expectedPixels[y][x];
        assertEquals(expected.getRed(), actual.getRed());
        assertEquals(expected.getGreen(), actual.getGreen());
        assertEquals(expected.getBlue(), actual.getBlue());
      }
    }
  }


  @Test
  public void testGreenComponentWithMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    componentVisualization.greenComponent("original_image.png", "green_output_with_mask.png",
        "mask_image.png");

    Image result = opn.getImage("green_output_with_mask.png");

    Pixel[][] expectedPixels = {
        {new Pixel(150, 150, 150), new Pixel(0, 0, 0)},  // Unmasked pixels
        {new Pixel(50, 50, 50), new Pixel(255, 255, 255)}      // Masked pixels (original)
    };

    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        Pixel actual = result.getPixel(x, y);
        Pixel expected = expectedPixels[y][x];
        assertEquals(expected.getRed(), actual.getRed());
        assertEquals(expected.getGreen(), actual.getGreen());
        assertEquals(expected.getBlue(), actual.getBlue());
      }
    }
  }

  @Test
  public void testBlueComponentWithMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(true);
    opn.saveImage("mask_image.png", mask);

    componentVisualization.blueComponent("original_image.png", "blue_output_with_mask.png",
        "mask_image.png");

    Image result = opn.getImage("blue_output_with_mask.png");

    Pixel[][] expectedPixels = {
        {new Pixel(200, 200, 200), new Pixel(0, 0, 0)},
        {new Pixel(50, 50, 50), new Pixel(0, 0, 0)}
    };

    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        Pixel actual = result.getPixel(x, y);
        Pixel expected = expectedPixels[y][x];
        assertEquals(expected.getRed(), actual.getRed());
        assertEquals(expected.getGreen(), actual.getGreen());
        assertEquals(expected.getBlue(), actual.getBlue());
      }
    }
  }


  @Test
  public void testNoMaskApplied() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    componentVisualization.redComponent("original_image.png", "red_output_no_mask.png", null);

    Image result = opn.getImage("red_output_no_mask.png");

    assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(100, result.getPixel(0, 0).getGreen());
    assertEquals(100, result.getPixel(0, 0).getBlue());
    assertEquals(50, result.getPixel(0, 1).getRed());
    assertEquals(50, result.getPixel(0, 1).getGreen());
    assertEquals(50, result.getPixel(0, 1).getBlue());
  }

  @Test
  public void testEmptyMask() {
    Image image = createTestImage();
    opn.saveImage("original_image.png", image);

    Image mask = createMaskImage(false);
    opn.saveImage("mask_image.png", mask);

    componentVisualization.redComponent("original_image.png", "red_output_empty_mask.png",
        "mask_image.png");

    Image result = opn.getImage("red_output_empty_mask.png");

    assertEquals(100, result.getPixel(0, 0).getRed());
    assertEquals(150, result.getPixel(0, 0).getGreen());
    assertEquals(200, result.getPixel(0, 0).getBlue());
    assertEquals(50, result.getPixel(0, 1).getRed());
    assertEquals(50, result.getPixel(0, 1).getGreen());
    assertEquals(50, result.getPixel(0, 1).getBlue());
  }

}