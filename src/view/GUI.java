package view;

import controller.GUIController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.imagerepresentation.Image;

/**
 * Represents the graphical user interface for an image processing application. Provides buttons and
 * controls for various image manipulation operations.
 */
public class GUI {

  private JFrame frame;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private JFileChooser fileChooser;
  private JTextField blackTextField;
  private JTextField midTextField;
  private JTextField whiteTextField;
  private JTextField compressionTextField;
  private JTextField targetWidthField;
  private JTextField targetHeightField;
  private GUIController imageController;
  private Image currentImage;

  /**
   * Constructs the GUI, initializing the controller and setting up the user interface. Sets the
   * main application window to be visible.
   */

  public GUI() {
    imageController = new GUIController(this);
    initializeGUI();
    frame.setVisible(true);
  }

  /**
   * Sets up the entire graphical user interface, configuring all panels, buttons, and interaction
   * components. Applies Nimbus look and feel if available.
   */

  private void initializeGUI() {
    // Set Nimbus Look and Feel if available
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info :
          javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Nimbus look and feel not available, falling back to default.");
    }

    // Initialize GUI components
    frame = new JFrame("CS 5010 - Image Processing Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1200, 800);
    frame.setLayout(new BorderLayout());

    // Display area for image
    imageLabel = new JLabel("No image loaded", JLabel.CENTER);
    imageLabel.setBorder(new EmptyBorder(15, 15, 15, 15));
    imageLabel.setBackground(new Color(240, 248, 255));
    imageLabel.setOpaque(true);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Control panel with buttons for operations
    JPanel mainControlPanel = new JPanel();
    mainControlPanel.setLayout(new BorderLayout());

    JPanel histogramPanel = new JPanel(new BorderLayout());
    histogramLabel = new JLabel("Histogram", JLabel.CENTER);
    histogramLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
    histogramLabel.setBackground(new Color(200, 200, 200));
    histogramLabel.setOpaque(true);
    histogramLabel.setPreferredSize(new Dimension(256, 256));
    histogramPanel.add(histogramLabel, BorderLayout.CENTER);
    mainControlPanel.add(histogramPanel, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Load and Save Buttons
    JPanel loadSavePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton loadButton = new JButton("Load Image");
    JButton saveButton = new JButton("Save Image");
    loadSavePanel.add(loadButton);
    loadSavePanel.add(saveButton);
    buttonPanel.add(loadSavePanel);

    // Add button actions
    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.loadImage();
      }
    });

    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.saveImage();
      }
    });

    // Component Visualization Buttons
    JPanel componentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton redButton = new JButton("Red Component");
    JButton greenButton = new JButton("Green Component");
    JButton blueButton = new JButton("Blue Component");
    componentPanel.add(redButton);
    componentPanel.add(greenButton);
    componentPanel.add(blueButton);
    buttonPanel.add(componentPanel);

    redButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.visualizeComponent("red");
      }
    });

    greenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.visualizeComponent("green");
      }
    });

    blueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.visualizeComponent("blue");
      }
    });

    // Flipping Buttons
    JPanel flipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton horizontalFlipButton = new JButton("Horizontal Flip");
    JButton verticalFlipButton = new JButton("Vertical Flip");
    JButton lumaButton = new JButton("Luma");
    flipPanel.add(horizontalFlipButton);
    flipPanel.add(verticalFlipButton);
    flipPanel.add(lumaButton);
    buttonPanel.add(flipPanel);

    horizontalFlipButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.flipImage(true);
      }
    });

    verticalFlipButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.flipImage(false);
      }
    });

    lumaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        imageController.applyLumaGrayscale();
      }
    });

    // Filtering Buttons
    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton blurButton = new JButton("Blur");
    JCheckBox blurPreviewCheckbox = new JCheckBox("Preview");
    JButton sharpenButton = new JButton("Sharpen");
    JCheckBox sharpenPreviewCheckbox = new JCheckBox("Preview");
    filterPanel.add(blurButton);
    filterPanel.add(blurPreviewCheckbox);
    filterPanel.add(sharpenButton);
    filterPanel.add(sharpenPreviewCheckbox);
    buttonPanel.add(filterPanel);

    // Blur Button Action Listener
    blurButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (blurPreviewCheckbox.isSelected()) {
          imageController.openPreviewWindow("blur");
        } else {
          imageController.applyFilter("blur", 100);
        }
      }
    });

    // Sharpen Button Action Listener
    sharpenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (sharpenPreviewCheckbox.isSelected()) {
          imageController.openPreviewWindow("sharpen");
        } else {
          imageController.applyFilter("sharpen", 100);
        }
      }
    });

    // Color Transformation and Correction Buttons
    JPanel colorTransPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton sepiaButton = new JButton("Sepia");
    JCheckBox sepiaPreviewCheckbox = new JCheckBox("Preview");
    JButton greyscaleButton = new JButton("Greyscale");
    JCheckBox greyscalePreviewCheckbox = new JCheckBox("Preview");
    JButton colorCorrectButton = new JButton("Color Correction");
    JCheckBox colorCorrectPreviewCheckbox = new JCheckBox("Preview");
    colorTransPanel.add(sepiaButton);
    colorTransPanel.add(sepiaPreviewCheckbox);
    colorTransPanel.add(greyscaleButton);
    colorTransPanel.add(greyscalePreviewCheckbox);
    colorTransPanel.add(colorCorrectButton);
    colorTransPanel.add(colorCorrectPreviewCheckbox);
    buttonPanel.add(colorTransPanel);

    // Sepia Button Action Listener
    sepiaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (sepiaPreviewCheckbox.isSelected()) {
          imageController.openPreviewWindow("sepia");
        } else {
          imageController.applyColorTransformation("sepia", 100);
        }
      }
    });

    // Greyscale Button Action Listener
    greyscaleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (greyscalePreviewCheckbox.isSelected()) {
          imageController.openPreviewWindow("greyscale");
        } else {
          imageController.applyColorTransformation("greyscale", 100);
        }
      }
    });

    // Color Correction Button Action Listener
    colorCorrectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (colorCorrectPreviewCheckbox.isSelected()) {
          imageController.openPreviewWindow("colorCorrect");
        } else {
          imageController.applyColorCorrection(100);
        }
      }
    });

    // Level Adjustment Controls
    blackTextField = new JTextField(3);
    midTextField = new JTextField(3);
    whiteTextField = new JTextField(3);
    JPanel levelAdjustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel blackLabel = new JLabel("Black:");
    JLabel midLabel = new JLabel("Mid:");
    JLabel whiteLabel = new JLabel("White:");
    JButton adjustLevelsButton = new JButton("Adjust Levels");
    JCheckBox levelAdjustPreviewCheckbox = new JCheckBox("Preview");
    levelAdjustPanel.add(blackLabel);
    levelAdjustPanel.add(blackTextField);
    levelAdjustPanel.add(midLabel);
    levelAdjustPanel.add(midTextField);
    levelAdjustPanel.add(whiteLabel);
    levelAdjustPanel.add(whiteTextField);
    levelAdjustPanel.add(adjustLevelsButton);
    levelAdjustPanel.add(levelAdjustPreviewCheckbox);
    buttonPanel.add(levelAdjustPanel);

    // Level Adjustment Button Action Listener
    adjustLevelsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        try {
          // Parse the input values
          int black = Integer.parseInt(blackTextField.getText().trim());
          int mid = Integer.parseInt(midTextField.getText().trim());
          int white = Integer.parseInt(whiteTextField.getText().trim());

          // Validate the values
          if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
            JOptionPane.showMessageDialog(frame, "Values must be between 0 and 255.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;  // Exit the method if values are invalid
          }

          if (black >= mid || mid >= white) {
            JOptionPane.showMessageDialog(frame, "Ensure that Black < Mid < White.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;  // Exit the method if values are in invalid order
          }

          // If the preview checkbox is selected, open the preview window
          if (levelAdjustPreviewCheckbox.isSelected()) {
            imageController.openPreviewWindow("levelsAdjust");
          } else {
            imageController.applyOperation("levelsAdjust", 100);
          }

        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(frame,
              "Please enter valid integer values for Black, Mid, and White levels.",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    // Compression Controls
    compressionTextField = new JTextField(3);
    JPanel compressionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel compressionLabel = new JLabel("Compression %:");
    JButton compressButton = new JButton("Compress Image");
    compressionPanel.add(compressionLabel);
    compressionPanel.add(compressionTextField);
    compressionPanel.add(compressButton);
    buttonPanel.add(compressionPanel);

    compressButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          double compressionFactor = Double.parseDouble(compressionTextField.getText().trim());
          imageController.applyCompression(compressionFactor);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    // Downscaling Controls
    targetWidthField = new JTextField(3);
    targetHeightField = new JTextField(3);
    JPanel downscalePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel targetWidthLabel = new JLabel("Width:");
    JLabel targetHeightLabel = new JLabel("Height:");
    JButton downsizeButton = new JButton("Downscale");
    downscalePanel.add(targetWidthLabel);
    downscalePanel.add(targetWidthField);
    downscalePanel.add(targetHeightLabel);
    downscalePanel.add(targetHeightField);
    downscalePanel.add(downsizeButton);
    buttonPanel.add(downscalePanel);

    downsizeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int targetWidth = Integer.parseInt(targetWidthField.getText().trim());
          int targetHeight = Integer.parseInt(targetHeightField.getText().trim());
          imageController.applyDownscaling(targetWidth, targetHeight);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(frame,
              "Please enter valid integer values for Width and Height.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    mainControlPanel.add(buttonPanel, BorderLayout.CENTER);

    JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageScrollPane,
        new JScrollPane(mainControlPanel));
    mainSplitPane.setResizeWeight(0.63);
    mainSplitPane.setDividerLocation((int) (frame.getWidth() * 0.63));
    frame.add(mainSplitPane, BorderLayout.CENTER);

    fileChooser = new JFileChooser();
  }

  /**
   * Retrieves the level adjustment values entered by the user.
   *
   * @return an array of three integers representing black, mid, and white levels
   * @throws NumberFormatException if the input values cannot be parsed as integers
   */
  public int[] getLevelAdjustmentValues() throws NumberFormatException {
    int black = Integer.parseInt(blackTextField.getText().trim());
    int mid = Integer.parseInt(midTextField.getText().trim());
    int white = Integer.parseInt(whiteTextField.getText().trim());
    return new int[]{black, mid, white};
  }

  public JTextField getBlackTextField() {
    return blackTextField;
  }

  public JTextField getMidTextField() {
    return midTextField;
  }

  public JTextField getWhiteTextField() {
    return whiteTextField;
  }


  public JFrame getFrame() {
    return frame;
  }

  public JLabel getImageLabel() {
    return imageLabel;
  }

  public JLabel getHistogramLabel() {
    return histogramLabel;
  }

  public JFileChooser getFileChooser() {
    return fileChooser;
  }

  public Image getCurrentImage() {
    return currentImage;
  }

  public void setCurrentImage(Image currentImage) {
    this.currentImage = currentImage;
  }
}

