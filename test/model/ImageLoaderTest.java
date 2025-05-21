package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import controller.ImageLoader;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import org.junit.Before;
import org.junit.Test;

/**
 * Test to check the working of loading images.
 */
public class ImageLoaderTest {

  private ImageLoader imageLoader;
  private ImageOperations imageOperations;

  @Before
  public void setUp() {
    imageLoader = new ImageLoader();
    imageOperations = new ImageOperations();
  }

  @Test
  public void testLoadValidPPMImage() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.ppm"))) {
      writer.write("P3\n");
      writer.write("2 2\n");
      writer.write("255\n");
      writer.write("255 0 0\n");
      writer.write("0 255 0\n");
      writer.write("0 0 255\n");
      writer.write("255 255 0\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Image image = imageLoader.loadImage("test.ppm");
    imageOperations.saveImage("testImage", image);

    assertNotNull(imageOperations.getImage("testImage"));
  }

  @Test
  public void testLoadValidPNGImage() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("test.png");
    ImageIO.write(bufferedImage, "png", outputFile);

    Image image = imageLoader.loadImage("test.png");
    imageOperations.saveImage("testPNGImage", image);

    assertNotNull(imageOperations.getImage("testPNGImage"));

    outputFile.delete();
  }

  @Test
  public void testLoadValidJPEGImage() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("test.jpg");
    ImageIO.write(bufferedImage, "jpg", outputFile);

    Image image = imageLoader.loadImage("test.jpg");
    imageOperations.saveImage("testJPEGImage", image);

    assertNotNull(imageOperations.getImage("testJPEGImage"));

    outputFile.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadInvalidImagePath() {
    imageLoader.loadImage("invalid/path/to/image.jpg");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLoadInvalidPPMFile() {
    imageLoader.loadImage("invalid.ppm");
  }

  @Test
  public void testSaveImageValidPath() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("test.png");
    ImageIO.write(bufferedImage, "png", outputFile);

    Image image = imageLoader.loadImage("test.png");
    imageOperations.saveImage("testPNGImage", image);

    imageLoader.saveImage("output.png", imageOperations.getImage("testPNGImage"));

    File savedOutputFile = new File("output.png");
    assertTrue(savedOutputFile.exists());

    outputFile.delete();
    savedOutputFile.delete();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageNonExistent() {
    Image nonExistentImage = imageOperations.getImage("nonExistentImage");
    imageLoader.saveImage("output.jpg", nonExistentImage);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageInvalidPath() {
    Image image = imageLoader.loadImage("test.png");
    imageOperations.saveImage("testPNGImage", image);
    imageLoader.saveImage("invalid/path/output.jpg", imageOperations.getImage("testPNGImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageToInvalidFormat() {
    Image image = imageLoader.loadImage("test.png");
    imageOperations.saveImage("testPNGImage", image);
    imageLoader.saveImage("output.invalid", imageOperations.getImage("testPNGImage"));
  }


  @Test
  public void testLoadMultipleImages() throws IOException {
    // Load a valid PNG image
    BufferedImage bufferedImage1 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File pngFile = new File("test1.png");
    ImageIO.write(bufferedImage1, "png", pngFile);

    Image image1 = imageLoader.loadImage("test1.png");
    imageOperations.saveImage("image1", image1);

    BufferedImage bufferedImage2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File jpgFile = new File("test2.jpg");
    ImageIO.write(bufferedImage2, "jpg", jpgFile);

    Image image2 = imageLoader.loadImage("test2.jpg");
    imageOperations.saveImage("image2", image2);

    assertNotNull(imageOperations.getImage("image1"));
    assertNotNull(imageOperations.getImage("image2"));

    pngFile.delete();
    jpgFile.delete();
  }

  @Test
  public void testLoadImageWithWhitespaceInName() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("test image.png");
    ImageIO.write(bufferedImage, "png", outputFile);
    Image image = imageLoader.loadImage("test image.png");
    imageOperations.saveImage("test image", image);
    assertNotNull(imageOperations.getImage("test image"));
    outputFile.delete();
  }

  @Test
  public void testLoadImageWithSpecialCharactersInName() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("test_image@#$.png");
    ImageIO.write(bufferedImage, "png", outputFile);
    Image image = imageLoader.loadImage("test_image@#$.png");
    imageOperations.saveImage("test_image@#$", image);
    assertNotNull(imageOperations.getImage("test_image@#$"));
    outputFile.delete();
  }

  @Test
  public void testLoadSameImageMultipleTimes() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("duplicate.png");
    ImageIO.write(bufferedImage, "png", outputFile);
    Image image1 = imageLoader.loadImage("duplicate.png");
    imageOperations.saveImage("duplicateImage", image1);
    Image image2 = imageLoader.loadImage("duplicate.png");
    imageOperations.saveImage("duplicateImage", image2);
    assertNotNull(imageOperations.getImage("duplicateImage"));
    outputFile.delete();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLoadUnsupportedFileFormat() {
    imageLoader.loadImage("test.bmp");
  }

  @Test
  public void testSaveImageToSamePath() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    File outputFile = new File("same_output.png");
    ImageIO.write(bufferedImage, "png", outputFile);

    Image image = imageLoader.loadImage("same_output.png");
    imageOperations.saveImage("sameImage", image);

    imageLoader.saveImage("same_output.png", imageOperations.getImage("sameImage"));

    File checkFile = new File("same_output.png");
    assertTrue(checkFile.exists());

    checkFile.delete();
  }

}
