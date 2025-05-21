package controller;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import model.transformations.classes.ColorCorrection;
import model.transformations.classes.ColorRepresentation;
import model.transformations.classes.ColorTransformation;
import model.transformations.classes.ComponentVisualization;
import model.transformations.classes.Filtering;
import model.transformations.classes.Flipping;
import model.transformations.classes.Histogram;
import model.transformations.classes.ImageCompression;
import model.transformations.classes.ImageDownsizing;
import model.transformations.classes.LevelAdjust;
import view.GUI;

/**
 * Controls the interactions between the GUI and image processing operations. Manages user
 * interactions like loading, saving and transforming images.
 */
public class GUIController {

  public ImageOperations imageOperations;
  public ImageLoader imageLoader;
  private GUI view;

  /**
   * Creates a new controller with a GUI view and image processing capabilities. Sets up image
   * operations and image loader.
   *
   * @param view the main graphical user interface for image editing
   */
  public GUIController(GUI view) {
    this.view = view;
    this.imageOperations = new ImageOperations();
    this.imageLoader = new ImageLoader();
  }

  /**
   * Loads an image file selected by the user through a file chooser. Displays the loaded image in
   * the GUI and updates the histogram. Shows an error message if image loading fails.
   */
  public void loadImage() {
    JFileChooser fileChooser = view.getFileChooser();
    int returnValue = fileChooser.showOpenDialog(view.getFrame());
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      try {
        Image currentImage = imageLoader.loadImage(selectedFile.getAbsolutePath());
        if (currentImage == null) {
          throw new Exception("Failed to load image: Image object is null.");
        }
        imageOperations.saveImage("currentImage", currentImage);
        view.setCurrentImage(currentImage);
        displayImage(currentImage);
        updateHistogram(currentImage);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(view.getFrame(), "Error loading image: " + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Saves the current image to a user-selected file location. Opens a file chooser dialog to select
   * save destination. Displays a success message if saved, or an error message if saving fails.
   */
  public void saveImage() {
    Image currentImage = view.getCurrentImage();
    if (currentImage != null) {
      JFileChooser fileChooser = view.getFileChooser();
      int saveReturnValue = fileChooser.showSaveDialog(view.getFrame());
      if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
        File saveFile = fileChooser.getSelectedFile();
        try {
          imageLoader.saveImage(saveFile.getAbsolutePath(), currentImage);
          JOptionPane.showMessageDialog(view.getFrame(), "Image saved successfully!", "Success",
              JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(view.getFrame(), "Error saving image: " + ex.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    } else {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to save.", "Warning",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Visualizes a specific color component of the current image. Supports extracting and displaying
   * red, green, or blue color channels. Updates the image display and histogram accordingly.
   *
   * @param component Color component to visualize (red, green, or blue)
   */
  public void visualizeComponent(String component) {
    ComponentVisualization visualizer = new ComponentVisualization();
    String tempDestName = "temp_" + component + "_component_image.ppm";

    switch (component.toLowerCase()) {
      case "red":
        visualizer.redComponent("currentImage", tempDestName);
        break;
      case "green":
        visualizer.greenComponent("currentImage", tempDestName);
        break;
      case "blue":
        visualizer.blueComponent("currentImage", tempDestName);
        break;
      default:
        JOptionPane.showMessageDialog(view.getFrame(), "Unknown component: " + component, "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    Image currentImage = imageOperations.getImage(tempDestName);
    imageOperations.saveImage("currentImage", currentImage);
    view.setCurrentImage(currentImage);
    displayImage(currentImage);
    updateHistogram(currentImage, component);
  }

  /**
   * Flips the current image either horizontally or vertically. Creates a new image with pixels
   * rearranged based on flip direction. Updates the image display and histogram.
   *
   * @param horizontal True for horizontal flip, false for vertical flip
   */
  public void flipImage(boolean horizontal) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to flip.", "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      Flipping flipper = new Flipping();
      String flippedImageName = horizontal ? "flipped_horizontal" : "flipped_vertical";

      if (horizontal) {
        flipper.horizontalFlip("currentImage", flippedImageName);
      } else {
        flipper.verticalFlip("currentImage", flippedImageName);
      }

      currentImage = imageOperations.getImage(flippedImageName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);
      displayImage(currentImage);
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(), "Error flipping image: " + ex.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Applies a blur or sharpen filter to the current image. Allows partial image filtering with
   * split preview.
   *
   * @param filterType      Type of filter to apply (blur or sharpen)
   * @param splitPercentage Percentage of image to apply filter (for split preview)
   */
  public void applyFilter(String filterType, Integer splitPercentage) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to apply filter.", "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      Filtering filter = new Filtering();
      String filteredImageName = "filtered_" + filterType;

      if ("blur".equals(filterType)) {
        filter.blur("currentImage", filteredImageName, splitPercentage);
      } else if ("sharpen".equals(filterType)) {
        filter.sharpen("currentImage", filteredImageName, splitPercentage);
      } else {
        JOptionPane.showMessageDialog(view.getFrame(), "Unknown filter type: " + filterType,
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      currentImage = imageOperations.getImage(filteredImageName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);
      displayImage(currentImage);
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(), "Error applying filter: " + ex.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Converts the current image to grayscale using the luma component. Calculates grayscale based on
   * weighted color intensities. Updates the image display and histogram.
   */
  public void applyLumaGrayscale() {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to apply luma grayscale.",
          "Warning", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      ColorRepresentation colorRep = new ColorRepresentation();
      String tempDestName = "temp_luma_grayscale_image.ppm";

      // Apply the luma transformation
      colorRep.lumaComponent("currentImage", tempDestName);

      // Update currentImage and save the transformed image
      currentImage = imageOperations.getImage(tempDestName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);

      // Display the updated image in the view
      displayImage(currentImage);

      // Update the histogram to reflect the new image
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(),
          "Error applying luma grayscale: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Applies a color transformation to the current image. Supports sepia and greyscale
   * transformations. Allows partial image transformation with split preview.
   *
   * @param transformationType Type of color transformation to apply
   * @param splitPercentage    Percentage of image to apply transformation
   */
  public void applyColorTransformation(String transformationType, Integer splitPercentage) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(),
          "No image loaded to apply color transformation.", "Warning", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      ColorTransformation colorTrans = new ColorTransformation();
      String tempDestName = "temp_" + transformationType + "_image.ppm";

      if ("sepia".equals(transformationType)) {
        colorTrans.sepia("currentImage", tempDestName, splitPercentage);
      } else if ("greyscale".equals(transformationType)) {
        colorTrans.greyscale("currentImage", tempDestName, splitPercentage);
      } else {
        JOptionPane.showMessageDialog(view.getFrame(),
            "Unknown transformation type: " + transformationType, "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      currentImage = imageOperations.getImage(tempDestName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);
      displayImage(currentImage);
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(),
          "Error applying " + transformationType + ": " + ex.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Compresses the current image using a specified compression factor. Reduces image file size
   * while attempting to maintain visual quality.
   *
   * @param compressionFactor Degree of image compression (lower values mean more compression)
   */
  public void applyCompression(double compressionFactor) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to compress.", "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      ImageCompression compressor = new ImageCompression();
      String compressedImageName = "compressed_image";

      compressor.compress(compressionFactor, "currentImage", compressedImageName);
      currentImage = imageOperations.getImage(compressedImageName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);
      displayImage(currentImage);
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(),
          "Error applying compression: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Applies color correction to the current image. Adjusts color balance and tone of the image.
   *
   * @param splitPositionPercentage Percentage for split preview of color correction
   */
  public void applyColorCorrection(Integer splitPositionPercentage) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to apply color correction.",
          "Warning", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      ColorCorrection colorCorrector = new ColorCorrection();
      String tempDestName = "temp_color_corrected_image.ppm";

      colorCorrector.colorCorrect("currentImage", tempDestName, splitPositionPercentage);
      currentImage = imageOperations.getImage(tempDestName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);
      displayImage(currentImage);
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(),
          "Error applying color correction: " + ex.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Adjusts image levels by modifying black, mid, and white point values. Allows fine-tuning of
   * image brightness, contrast, and tone. Validates input levels before applying transformation.
   */
  public void applyLevelsAdjustment() {
    System.out.println("Starting applyLevelsAdjustment");

    try {
      // Retrieve level adjustment values from the view
      int[] levels = view.getLevelAdjustmentValues();
      int black = levels[0];
      int mid = levels[1];
      int white = levels[2];

      // Validate the values
      if (!validateLevelValues(black, mid, white)) {
        return;
      }

      // Apply level adjustment to the image using the model
      LevelAdjust levelAdjuster = new LevelAdjust();
      String adjustedImageName = "adjusted_levels_image";

      System.out.println(
          "Applying level adjustment with values - Black: " + black + ", Mid: " + mid + ", White: "
              + white);
      levelAdjuster.levelsAdjust(black, mid, white, "currentImage", adjustedImageName, 100);

      // Update current image in view and model
      Image adjustedImage = imageOperations.getImage(adjustedImageName);
      imageOperations.saveImage("currentImage", adjustedImage);
      view.setCurrentImage(adjustedImage);
      displayImage(adjustedImage);
      updateHistogram(adjustedImage);
      System.out.println("Level adjustment applied successfully");

    } catch (NumberFormatException ex) {
      System.out.println("NumberFormatException occurred: " + ex.getMessage());
      JOptionPane.showMessageDialog(view.getFrame(),
          "Please enter valid integer values for Black, Mid, and White levels.", "Invalid Input",
          JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
      System.out.println("Exception occurred while applying levels adjustment: " + ex.getMessage());
      JOptionPane.showMessageDialog(view.getFrame(),
          "Error applying levels adjustment: " + ex.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private boolean validateLevelValues(int black, int mid, int white) {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      System.out.println("Invalid input values for levels adjustment");
      JOptionPane.showMessageDialog(view.getFrame(), "Values must be between 0 and 255.",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (black >= mid || mid >= white) {
      System.out.println("Ensure that Black < Mid < White validation failed");
      JOptionPane.showMessageDialog(view.getFrame(), "Ensure that Black < Mid < White.",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }


  /**
   * Reduces the image size to specified dimensions. Scales down the image while attempting to
   * preserve image quality.
   *
   * @param targetWidth  Desired width of the downsized image
   * @param targetHeight Desired height of the downsized image
   */
  public void applyDownscaling(int targetWidth, int targetHeight) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to downscale.", "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      ImageDownsizing downsizer = new ImageDownsizing();
      String downsizedImageName = "downsized_image";

      downsizer.downsize("currentImage", downsizedImageName, targetWidth, targetHeight);
      currentImage = imageOperations.getImage(downsizedImageName);
      imageOperations.saveImage("currentImage", currentImage);
      view.setCurrentImage(currentImage);
      displayImage(currentImage);
      updateHistogram(currentImage);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(view.getFrame(), "Error downsizing image: " + ex.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Opens a preview window for image operations. Allows users to see the effect of an operation
   * before applying. Provides a slider to adjust the split preview position.
   *
   * @param operation Type of image operation to preview
   */
  public void openPreviewWindow(String operation) {
    Image currentImage = view.getCurrentImage();
    if (currentImage == null) {
      JOptionPane.showMessageDialog(view.getFrame(), "No image loaded to preview.", "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    // Create the preview dialog window
    JDialog previewDialog = new JDialog(view.getFrame(), "Preview " + operation, true);
    previewDialog.setSize(700, 650);
    previewDialog.setLayout(new BorderLayout());

    // Create a label to display the preview image
    JLabel previewImageLabel = new JLabel();
    previewImageLabel.setHorizontalAlignment(JLabel.CENTER);
    previewImageLabel.setVerticalAlignment(JLabel.CENTER);

    // Add the label to the dialog
    JScrollPane scrollPane = new JScrollPane(previewImageLabel);
    previewDialog.add(scrollPane, BorderLayout.CENTER);

    // Control panel for the split slider, apply and cancel buttons
    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    JSlider splitSlider = new JSlider(0, 100, 50);
    JLabel splitValueLabel = new JLabel("Split Position: 50");

    JButton applyButton = new JButton("Apply");
    JButton cancelButton = new JButton("Cancel");

    controlPanel.add(splitValueLabel);
    controlPanel.add(splitSlider);
    controlPanel.add(applyButton);
    controlPanel.add(cancelButton);

    previewDialog.add(controlPanel, BorderLayout.SOUTH);

    // Listener to update the preview image and split position label based on the split slider value
    splitSlider.addChangeListener(e -> {
      int splitPosition = splitSlider.getValue();
      splitValueLabel.setText("Split Position: " + splitPosition);

      Image previewImage = applySplitPreview(currentImage, operation, splitPosition);
      BufferedImage bufferedImage = ImageLoader.convertToBufferedImage(previewImage);
      BufferedImage scaledImage = drawSplitLine(bufferedImage, splitPosition,
          previewImageLabel.getWidth(), previewImageLabel.getHeight());
      previewImageLabel.setIcon(new ImageIcon(scaledImage));
    });

    applyButton.addActionListener(e -> {
      applyOperation(operation, 100);
      previewDialog.dispose();
    });

    cancelButton.addActionListener(e -> {
      previewDialog.dispose();
    });

    // Generate the initial preview
    int initialSplitPosition = splitSlider.getValue();
    Image previewImage = applySplitPreview(currentImage, operation, initialSplitPosition);
    BufferedImage bufferedImage = ImageLoader.convertToBufferedImage(previewImage);
    BufferedImage scaledImage = drawSplitLine(bufferedImage, initialSplitPosition, 600, 450);
    previewImageLabel.setIcon(new ImageIcon(scaledImage));

    previewDialog.setVisible(true);
  }

  /**
   * Generates a preview image for a specific operation. Used in the preview window to show
   * operation effects.
   *
   * @param image         Original image to apply preview on
   * @param operation     Type of operation to preview
   * @param splitPosition Percentage position for split preview
   * @return Preview image after applying the operation
   */
  public Image applySplitPreview(Image image, String operation, int splitPosition) {
    try {
      String previewImageName = "previewImage";
      switch (operation) {
        case "blur":
          Filtering filter = new Filtering();
          filter.blur("currentImage", previewImageName, splitPosition);
          break;
        case "sharpen":
          Filtering sharpenFilter = new Filtering();
          sharpenFilter.sharpen("currentImage", previewImageName, splitPosition);
          break;
        case "sepia":
          ColorTransformation sepiaTrans = new ColorTransformation();
          sepiaTrans.sepia("currentImage", previewImageName, splitPosition);
          break;
        case "greyscale":
          ColorTransformation greyTrans = new ColorTransformation();
          greyTrans.greyscale("currentImage", previewImageName, splitPosition);
          break;
        case "colorCorrect":
          ColorCorrection colorCorrection = new ColorCorrection();
          colorCorrection.colorCorrect("currentImage", previewImageName, splitPosition);
          break;
        case "levelsAdjust":
          // Assume black, mid, and white values are taken from text fields in the view.
          int black = Integer.parseInt(view.getBlackTextField().getText().trim());
          int mid = Integer.parseInt(view.getMidTextField().getText().trim());
          int white = Integer.parseInt(view.getWhiteTextField().getText().trim());
          LevelAdjust levelAdjust = new LevelAdjust();
          levelAdjust.levelsAdjust(black, mid, white, "currentImage", previewImageName,
              splitPosition);
          break;
        default:
          throw new IllegalArgumentException("Unknown operation for preview: " + operation);
      }
      return imageOperations.getImage(previewImageName);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(view.getFrame(), "Error generating preview: " + e.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
    return image;
  }

  /**
   * Draws a split line on a buffered image to show preview boundaries. Creates a visual indicator
   * for split preview functionality.
   *
   * @param image                   Original buffered image
   * @param splitPositionPercentage Position of split line
   * @param width                   Width of the image display
   * @param height                  Height of the image display
   * @return Buffered image with split line drawn
   */
  private BufferedImage drawSplitLine(BufferedImage image, int splitPositionPercentage, int width,
      int height) {
    BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = scaledImage.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.drawImage(image, 0, 0, width, height, null);

    int splitPositionX = (int) (width * (splitPositionPercentage / 100.0));

    float[] dash = {5.0f};
    BasicStroke dashedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
        10.0f, dash, 0.0f);
    g2d.setStroke(dashedStroke);
    g2d.setColor(Color.WHITE);
    g2d.drawLine(splitPositionX, 0, splitPositionX, height);
    g2d.dispose();

    return scaledImage;
  }

  /**
   * Applies a specific image operation based on operation type. Used to execute the final
   * transformation after preview.
   *
   * @param operation     Type of image operation to apply
   * @param splitPosition Percentage for split preview
   */
  public void applyOperation(String operation, int splitPosition) {
    try {
      switch (operation) {
        case "blur":
          applyFilter("blur", splitPosition);
          break;
        case "sharpen":
          applyFilter("sharpen", splitPosition);
          break;
        case "sepia":
          applyColorTransformation("sepia", splitPosition);
          break;
        case "greyscale":
          applyColorTransformation("greyscale", splitPosition);
          break;
        case "colorCorrect":
          applyColorCorrection(splitPosition);
          break;
        case "levelsAdjust":
          // Get the current values from the view and apply them
          int[] levels = view.getLevelAdjustmentValues();
          if (validateLevelValues(levels[0], levels[1], levels[2])) {
            LevelAdjust levelAdjust = new LevelAdjust();
            String adjustedImageName = "adjusted_levels_image";
            levelAdjust.levelsAdjust(levels[0], levels[1], levels[2], "currentImage",
                adjustedImageName, 100);

            Image adjustedImage = imageOperations.getImage(adjustedImageName);
            imageOperations.saveImage("currentImage", adjustedImage);
            view.setCurrentImage(adjustedImage);
            displayImage(adjustedImage);
            updateHistogram(adjustedImage);
          }
          break;
        default:
          throw new IllegalArgumentException("Unknown operation: " + operation);
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(view.getFrame(), "Error applying operation: " + e.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }


  private void displayImage(Image image) {
    BufferedImage bufferedImage = ImageLoader.convertToBufferedImage(image);
    ImageIcon imageIcon = new ImageIcon(bufferedImage);
    view.getImageLabel().setIcon(imageIcon);
    view.getImageLabel().setText("");
  }

  private void updateHistogram(Image image) {
    if (image == null) {
      view.getHistogramLabel().setIcon(null);
      view.getHistogramLabel().setText("No image loaded");
      return;
    }

    Histogram histogram = new Histogram();
    Image histogramImage = histogram.createHistogram(image);
    BufferedImage histogramBufferedImage = ImageLoader.convertToBufferedImage(histogramImage);
    ImageIcon histogramIcon = new ImageIcon(histogramBufferedImage);
    view.getHistogramLabel().setIcon(histogramIcon);
    view.getHistogramLabel().setText("");
  }

  private void updateHistogram(Image image, String component) {
    if (image == null) {
      view.getHistogramLabel().setIcon(null);
      view.getHistogramLabel().setText("No image loaded");
      return;
    }

    Histogram histogram = new Histogram();
    Image histogramImage = histogram.createHistogram(image, component);
    BufferedImage histogramBufferedImage = ImageLoader.convertToBufferedImage(histogramImage);
    ImageIcon histogramIcon = new ImageIcon(histogramBufferedImage);
    view.getHistogramLabel().setIcon(histogramIcon);
    view.getHistogramLabel().setText("");
  }


}

