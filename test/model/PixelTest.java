package model;

import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Pixel;
import org.junit.Test;

/**
 * A JUnit4 test for Pixel Class.
 */

public class PixelTest {

  @Test
  public void testPixelCreationValidValues() {
    Pixel pixel = new Pixel(100, 150, 200);
    assertEquals(100, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(200, pixel.getBlue());
  }

  @Test
  public void testPixelCreationClampingRedBelowZero() {
    Pixel pixel = new Pixel(-10, 150, 200);
    assertEquals(0, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(200, pixel.getBlue());
  }

  @Test
  public void testPixelCreationClampingGreenBelowZero() {
    Pixel pixel = new Pixel(100, -20, 200);
    assertEquals(100, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(200, pixel.getBlue());
  }

  @Test
  public void testPixelCreationClampingBlueBelowZero() {
    Pixel pixel = new Pixel(100, 150, -30);
    assertEquals(100, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }

  @Test
  public void testPixelCreationClampingRedAbove255() {
    Pixel pixel = new Pixel(300, 150, 200);
    assertEquals(255, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(200, pixel.getBlue());
  }

  @Test
  public void testPixelCreationClampingGreenAbove255() {
    Pixel pixel = new Pixel(100, 300, 200);
    assertEquals(100, pixel.getRed());
    assertEquals(255, pixel.getGreen());
    assertEquals(200, pixel.getBlue());
  }

  @Test
  public void testPixelCreationClampingBlueAbove255() {
    Pixel pixel = new Pixel(100, 150, 300);
    assertEquals(100, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(255, pixel.getBlue());
  }

  @Test
  public void testSetRedValidValue() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setRed(120);
    assertEquals(120, pixel.getRed());
  }

  @Test
  public void testSetRedClampingBelowZero() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setRed(-50);
    assertEquals(0, pixel.getRed());
  }

  @Test
  public void testSetRedClampingAbove255() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setRed(300);
    assertEquals(255, pixel.getRed());
  }

  @Test
  public void testSetGreenValidValue() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setGreen(180);
    assertEquals(180, pixel.getGreen());
  }

  @Test
  public void testSetGreenClampingBelowZero() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setGreen(-20);
    assertEquals(0, pixel.getGreen());
  }

  @Test
  public void testSetGreenClampingAbove255() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setGreen(300);
    assertEquals(255, pixel.getGreen());
  }

  @Test
  public void testSetBlueValidValue() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setBlue(250);
    assertEquals(250, pixel.getBlue());
  }

  @Test
  public void testSetBlueClampingBelowZero() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setBlue(-10);
    assertEquals(0, pixel.getBlue());
  }

  @Test
  public void testSetBlueClampingAbove255() {
    Pixel pixel = new Pixel(100, 150, 200);
    pixel.setBlue(500);
    assertEquals(255, pixel.getBlue());
  }

  @Test
  public void testConstructorValidValues() {
    Pixel pixel = new Pixel(100, 150, 200);
    assertEquals(100, pixel.getRed());
    assertEquals(150, pixel.getGreen());
    assertEquals(200, pixel.getBlue());
  }

  @Test
  public void testConstructorClampingAbove255() {
    Pixel pixel = new Pixel(300, 400, 500);
    assertEquals(255, pixel.getRed());
    assertEquals(255, pixel.getGreen());
    assertEquals(255, pixel.getBlue());
  }

  @Test
  public void testConstructorClampingBelow0() {
    Pixel pixel = new Pixel(-10, -20, -30);
    assertEquals(0, pixel.getRed());
    assertEquals(0, pixel.getGreen());
    assertEquals(0, pixel.getBlue());
  }
}
