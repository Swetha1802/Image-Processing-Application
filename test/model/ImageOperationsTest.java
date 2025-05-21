package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import org.junit.Before;
import org.junit.Test;

/**
 * Test to check the working of images properly.
 */
public class ImageOperationsTest {

  private ImageOperations imageOps;
  private Image testImage;

  @Before
  public void setUp() {
    imageOps = new ImageOperations();
    testImage = new Image(100, 100); // Example dimensions
  }

  @Test
  public void testSaveImageSuccessfully() {
    imageOps.saveImage("testImage", testImage);
    assertNotNull(imageOps.getImage("testImage"));
  }

  @Test
  public void testGetImageSuccessfully() {
    imageOps.saveImage("existingImage", testImage);
    assertEquals(testImage, imageOps.getImage("existingImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageThrowsExceptionForNonExistingImage() {
    imageOps.getImage("nonExistingImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveNullImageThrowsException() {
    imageOps.saveImage("nullImage", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageWithEmptyNameThrowsException() {
    imageOps.saveImage("", testImage);
    imageOps.getImage(""); // This line won't actually be reached
  }

  @Test
  public void testOverwritingImageWithSameName() {
    Image firstImage = new Image(200, 200);
    imageOps.saveImage("overwriteImage", firstImage);
    Image secondImage = new Image(300, 300);
    imageOps.saveImage("overwriteImage", secondImage);
    assertEquals(secondImage, imageOps.getImage("overwriteImage"));
  }

  @Test
  public void testRetrieveImageAfterMultipleSaves() {
    Image image1 = new Image(400, 400);
    Image image2 = new Image(500, 500);
    imageOps.saveImage("multiSaveImage", image1);
    imageOps.saveImage("multiSaveImage", image2);
    assertEquals(image2, imageOps.getImage("multiSaveImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageWithNullNameThrowsException() {
    imageOps.getImage(null);
  }

  @Test
  public void testGetImageWithSpecialCharactersInName() {
    Image specialImage = new Image(150, 150);
    imageOps.saveImage("image@123", specialImage);
    assertEquals(specialImage, imageOps.getImage("image@123"));
  }

  @Test
  public void testSaveImageWithSpecialCharactersInName() {
    Image specialImage = new Image(250, 250);
    imageOps.saveImage("image!#%", specialImage);
    assertNotNull(imageOps.getImage("image!#%"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRetrieveImageFromEmptyStorage() {
    imageOps.getImage("emptyStorage");
  }

  @Test
  public void testSavingAndRetrievingMultipleImages() {
    Image image1 = new Image(600, 600);
    Image image2 = new Image(700, 700);
    imageOps.saveImage("firstImage", image1);
    imageOps.saveImage("secondImage", image2);
    assertEquals(image1, imageOps.getImage("firstImage"));
    assertEquals(image2, imageOps.getImage("secondImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSavingImageWithNullNameThrowsException() {
    Image testImage = new Image(800, 800);
    imageOps.saveImage(null, testImage);
  }

  @Test
  public void testSaveImageWithSameNameMultipleTimes() {
    Image firstImage = new Image(300, 300);
    imageOps.saveImage("duplicateImage", firstImage);
    Image secondImage = new Image(400, 400);
    imageOps.saveImage("duplicateImage", secondImage);
    assertEquals(secondImage, imageOps.getImage("duplicateImage"));
  }

  @Test
  public void testGetImageAfterMultipleSavesWithDifferentNames() {
    Image image1 = new Image(100, 100);
    Image image2 = new Image(200, 200);
    imageOps.saveImage("imageOne", image1);
    imageOps.saveImage("imageTwo", image2);
    assertEquals(image1, imageOps.getImage("imageOne"));
    assertEquals(image2, imageOps.getImage("imageTwo"));
  }

  @Test
  public void testSavingImageWithNamesDifferingOnlyByCase() {
    Image lowerCaseImage = new Image(150, 150);
    Image upperCaseImage = new Image(250, 250);
    imageOps.saveImage("CaseSensitive", lowerCaseImage);
    imageOps.saveImage("casesensitive", upperCaseImage);
    assertEquals(upperCaseImage, imageOps.getImage("casesensitive"));
  }

  @Test
  public void testGetImageWithLeadingAndTrailingSpaces() {
    Image spacedImage = new Image(100, 100);
    imageOps.saveImage("  spacedImage  ", spacedImage);
    assertEquals(spacedImage, imageOps.getImage("  spacedImage  "));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageWithOnlySpacesThrowsException() {
    imageOps.getImage("    ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveImageWithOnlySpacesThrowsException() {
    imageOps.saveImage("   ", testImage);
  }

  @Test
  public void testRetrievingImagesWithLargeDimensions() {
    Image largeImage = new Image(10000, 10000);
    imageOps.saveImage("largeImage", largeImage);
    assertEquals(largeImage, imageOps.getImage("largeImage"));
  }


  @Test
  public void testRetrievingNonExistentImageHandlesCaseSensitively() {
    imageOps.saveImage("UniqueImage", testImage);
    assertEquals(testImage, imageOps.getImage("UniqueImage"));
    try {
      imageOps.getImage("uniqueimage"); // should throw an exception
      fail("Expected an IllegalArgumentException to be thrown");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }


  }
}