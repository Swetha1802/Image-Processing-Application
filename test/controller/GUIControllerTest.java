package controller;

import static org.junit.Assert.assertEquals;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import model.imagerepresentation.Image;
import model.imagerepresentation.ImageOperations;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

/**
 * A Junit test for GUIController class.
 */
public class GUIControllerTest {

  private GUI mockView;
  private JFileChooser mockFileChooser;
  private Image mockImage;
  private JFrame mockFrame;

  private MockGUIController controller;
  private StringBuilder log;

  @Before
  public void setUp() {
    mockView = new MockGUI();
    ImageOperations mockImageOperations = new ImageOperations();
    mockFileChooser = new JFileChooser();
    mockImage = new Image(10, 10);
    mockFrame = new JFrame();

    log = new StringBuilder();
    controller = new MockGUIController(mockView, log);
    controller.imageOperations = mockImageOperations;
  }

  @Test
  public void testLoadImageSuccessful() {
    controller.loadImage();
    assertEquals("Load image method called\n", log.toString());
  }

  @Test
  public void testSaveImageWithoutCurrentImage() {
    mockView.setCurrentImage(null);
    controller.saveImage();
    assertEquals("Save image without current image\n", log.toString());
  }

  @Test
  public void testSaveImageSuccessful() {
    mockView.setCurrentImage(mockImage);
    controller.saveImage();
    assertEquals("Save image method called\n", log.toString());
  }

  @Test
  public void testVisualizeComponentBlue() {
    mockView.setCurrentImage(mockImage);
    controller.visualizeComponent("blue");
    assertEquals("Visualize blue component called\n", log.toString());
  }

  @Test
  public void testVisualizeComponentRed() {
    mockView.setCurrentImage(mockImage);
    controller.visualizeComponent("red");
    assertEquals("Visualize red component called\n", log.toString());
  }

  @Test
  public void testVisualizeComponentGreen() {
    mockView.setCurrentImage(mockImage);
    controller.visualizeComponent("green");
    assertEquals("Visualize green component called\n", log.toString());
  }

  @Test
  public void testFlipImageHorizontal() {
    mockView.setCurrentImage(mockImage);
    controller.flipImage(true);
    assertEquals("Flip image horizontally called\n", log.toString());
  }

  @Test
  public void testFlipImageVertical() {
    mockView.setCurrentImage(mockImage);
    controller.flipImage(false);
    assertEquals("Flip image vertically called\n", log.toString());
  }

  @Test
  public void testApplyFilterBlur() {
    mockView.setCurrentImage(mockImage);
    controller.applyFilter("blur", 100);
    assertEquals("Apply blur filter called\n", log.toString());
  }

  @Test
  public void testApplyFilterSharpen() {
    mockView.setCurrentImage(mockImage);
    controller.applyFilter("sharpen", 100);
    assertEquals("Apply sharpen filter called\n", log.toString());
  }

  @Test
  public void testApplyColorTransformationSepia() {
    mockView.setCurrentImage(mockImage);
    controller.applyColorTransformation("sepia", 100);
    assertEquals("Apply sepia transformation called\n", log.toString());
  }

  @Test
  public void testApplyColorTransformationGreyscale() {
    mockView.setCurrentImage(mockImage);
    controller.applyColorTransformation("greyscale", 100);
    assertEquals("Apply greyscale transformation called\n", log.toString());
  }

  @Test
  public void testApplyLumaGrayscale() {
    mockView.setCurrentImage(mockImage);
    controller.applyLumaGrayscale();
    assertEquals("Apply luma called\n", log.toString());
  }

  @Test
  public void testApplyLevelsAdjustment() {
    mockView.setCurrentImage(mockImage);
    controller.applyLevelsAdjustment();
    assertEquals("Apply levels adjustment called\n", log.toString());
  }

  @Test
  public void testApplyDownscaling() {
    mockView.setCurrentImage(mockImage);
    controller.applyDownscaling(100, 100);
    assertEquals("Apply downscaling called\n", log.toString());
  }

  // Mock version of GUIController for testing
  private class MockGUI extends GUI {

    private Image currentImage;

    public MockGUI() {
      // Prevent GUI initialization to avoid opening the window
    }

    @Override
    public Image getCurrentImage() {
      return currentImage;
    }

    @Override
    public void setCurrentImage(Image image) {
      this.currentImage = image;
    }

    @Override
    public JFileChooser getFileChooser() {
      return mockFileChooser; // Use the mock file chooser
    }

    @Override
    public JFrame getFrame() {
      return mockFrame; // Use the mock frame
    }
  }

  private class MockGUIController extends GUIController {

    private StringBuilder log;
    private GUI view; // Store a reference to the view

    public MockGUIController(GUI view, StringBuilder log) {
      super(view);
      this.view = view; // Initialize the view reference
      this.log = log;
    }

    @Override
    public void loadImage() {
      log.append("Load image method called\n");
      super.loadImage();
    }

    @Override
    public void saveImage() {
      if (view.getCurrentImage() == null) {
        log.append("Save image without current image\n");
      } else {
        log.append("Save image method called\n");
      }
      super.saveImage();
    }

    @Override
    public void visualizeComponent(String component) {
      log.append("Visualize " + component + " component called\n");
      super.visualizeComponent(component);
    }

    @Override
    public void flipImage(boolean horizontal) {
      log.append("Flip image " + (horizontal ? "horizontally" : "vertically") + " called\n");
      super.flipImage(horizontal);
    }

    @Override
    public void applyFilter(String filterType, Integer splitPercentage) {
      log.append("Apply " + filterType + " filter called\n");
      super.applyFilter(filterType, splitPercentage);
    }

    @Override
    public void applyLumaGrayscale() {
      log.append("Apply luma called\n");
      super.applyLumaGrayscale();
    }

    @Override
    public void applyColorTransformation(String transformationType, Integer splitPercentage) {
      log.append("Apply " + transformationType + " transformation called\n");
      super.applyColorTransformation(transformationType, splitPercentage);
    }

    @Override
    public void applyCompression(double compressionFactor) {
      log.append("Apply Compression called\n");
      super.applyCompression(compressionFactor);

    }

    @Override
    public void applyColorCorrection(Integer splitPositionPercentage) {
      log.append("Apply Color Correction called\n");

    }

    @Override
    public void applyLevelsAdjustment() {
      log.append("Apply levels adjustment called\n");
      super.applyLevelsAdjustment();
    }

    @Override
    public void applyDownscaling(int targetWidth, int targetHeight) {
      log.append("Apply downscaling called\n");
      super.applyDownscaling(targetWidth, targetHeight);
    }

    @Override
    public void openPreviewWindow(String operation) {
      log.append("Preview window called\n");
      super.openPreviewWindow(operation);

    }

  }
}
