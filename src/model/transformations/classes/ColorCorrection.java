package model.transformations.classes;

import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.imagerepresentation.Pixel;
import model.transformations.interfaces.ColorCorrectionInterface;

/**
 * This class provides a method to perform color correction on images.
 */
public class ColorCorrection implements ColorCorrectionInterface {

  @Override
  public void colorCorrect(String imageName, String destImageName,
      Integer splitPositionPercentage) {
    if (splitPositionPercentage != null && (splitPositionPercentage < 0
        || splitPositionPercentage > 100)) {
      throw new IllegalArgumentException("Split position percentage must be between 0 and 100.");
    }

    ImageOperations opn = new ImageOperations();
    Image original = opn.getImage(imageName);
    Histogram histogramGenerator = new Histogram();
    int[][] histograms = histogramGenerator.calculateHistograms(original);

    int[] redHistogram = histograms[0];
    int[] greenHistogram = histograms[1];
    int[] blueHistogram = histograms[2];

    int redPeak = findMeaningfulPeak(redHistogram);
    int greenPeak = findMeaningfulPeak(greenHistogram);
    int bluePeak = findMeaningfulPeak(blueHistogram);

    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    Image correctedImage = new Image(original.getWidth(), original.getHeight());

    // Calculate split position
    int splitPosition = SplitView.calculateSplitPosition(original.getWidth(),
        splitPositionPercentage);

    // Apply color correction to pixels before the split position
    applyColorCorrection(original, correctedImage, redPeak, greenPeak, bluePeak, averagePeak,
        splitPosition);

    // Apply split view effect using SplitViewHandler
    SplitView.applySplit(original, correctedImage, splitPositionPercentage);

    // Save the corrected image
    opn.saveImage(destImageName, correctedImage);
  }

  private void applyColorCorrection(Image original, Image correctedImage, int redPeak,
      int greenPeak, int bluePeak,
      int averagePeak, int splitPosition) {
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < splitPosition; x++) {
        Pixel pixel = original.getPixel(x, y);
        Pixel correctedPixel = new Pixel(
            adjustColorValue(pixel.getRed(), redPeak, averagePeak),
            adjustColorValue(pixel.getGreen(), greenPeak, averagePeak),
            adjustColorValue(pixel.getBlue(), bluePeak, averagePeak)
        );
        correctedImage.setPixel(x, y, correctedPixel);
      }
    }
  }

  private int findMeaningfulPeak(int[] histogram) {
    int maxFrequency = 0;
    int peakValue = 128;

    for (int i = 10; i < 245; i++) {
      if (histogram[i] > maxFrequency) {
        maxFrequency = histogram[i];
        peakValue = i;
      }
    }
    return peakValue;
  }

  private int adjustColorValue(int colorValue, int originalPeak, int targetPeak) {
    int offset = targetPeak - originalPeak;
    int adjustedValue = colorValue + offset;
    return Math.max(0, Math.min(255, adjustedValue));
  }
}