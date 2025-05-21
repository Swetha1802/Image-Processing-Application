package model.transformations.interfaces;

import model.imagerepresentation.Image;

/**
 * Helps create histograms for images. A histogram shows how pixel colors are spread out.
 */
public interface HistogramInterface {

  /**
   * Makes a histogram for an image and saves it.
   *
   * @param imageName     name of the image to use
   * @param destImageName name to save the new histogram
   * @throws IllegalArgumentException if image can't be found
   */
  void generateHistogram(String imageName, String destImageName);

  /**
   * Gets color histograms for red, green, and blue channels.
   *
   * @param image the image to analyze
   * @return 2D array with color histograms (first row is red, second is green, third is blue)
   */
  int[][] calculateHistograms(Image image);
}