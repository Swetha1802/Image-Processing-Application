package controller;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.imagerepresentation.ImageOperations;
import org.junit.Before;
import org.junit.Test;
import view.ConsoleImageLoaderView;
import view.ImageLoaderView;

/**
 * A Junit4 test for script parser which tests all the commands being parsed.
 **/
public class ScriptParserTest {

  private static final String SCRIPT_PATH = "test/resources/scripts/test_script.txt";
  private ScriptParser scriptParser;
  private ImageOperations opns;

  @Before
  public void setUp() {
    ImageLoader imageLoader = new ImageLoader();
    ImageOperations imageOperations = new ImageOperations();
    opns = new ImageOperations();
    ImageLoaderView view = new ConsoleImageLoaderView();
    scriptParser = new ScriptParser(imageLoader, imageOperations, view);
  }

  private void writeScript(String... lines) throws IOException {
    System.out.println("Script path: " + Paths.get(SCRIPT_PATH).toAbsolutePath());
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SCRIPT_PATH))) {
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Test
  public void testParseScript_ValidImage() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_valid.txt";
    String imagePath = "test/resources/scripts/hello.jpeg";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " test2\n");
    }

    scriptParser.parseScript(scriptPath);
    //
    assertTrue("test2 should be loaded", (opns.getImage("test2") != null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNonExistentImage() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_nonexistent_load.txt";
    String imagePath = "test/resources/scripts/nonexistent.jpeg";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " test2\n");
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadUnsupportedFormat() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_unsupported_load.txt";
    String imagePath = "test/resources/scripts/unsupported.txt";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " test2\n");
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadEmptyPath() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_empty_path_load.txt";
    String imagePath = "";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " test2\n");
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNullPath() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_null_path_load.txt";
    String imagePath = null;

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " test2\n");
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test
  public void testLoadPpmImage() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_load_ppm.txt";
    String imagePath = "test/resources/scripts/hello-img.ppm";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " hello-img_ppm\n");
    }

    scriptParser.parseScript(scriptPath);

    assertTrue("test2_ppm should be loaded", (opns.getImage("hello-img_ppm") != null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadMissingDestinationName() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_missing_dest_load.txt";
    String imagePath = "test/resources/scripts/hello.jpeg";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + "\n");
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadAdditionalArgument() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_extra_arg_load.txt";
    String imagePath = "test/resources/scripts/hello.jpeg";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("load " + imagePath + " validImage extraArg\n");
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadTypoInCommand() throws IOException {
    String scriptPath = "test/resources/scripts/test_script_typo_load.txt";
    String imagePath = "test/resources/scripts/hello.jpeg";

    Files.createDirectories(Paths.get("test/resources/scripts"));

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath))) {
      writer.write("loadd " + imagePath + " validImage\n"); // Typo in the command
    }

    scriptParser.parseScript(scriptPath);
  }

  @Test
  public void testLoadAndSaveImage() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/saved_hello.jpeg";
    writeScript("load " + imagePath + " validImageLoadSave",
        "save " + savePath + " validImageLoadSave");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("validImageLoadSave should be loaded",
        (opns.getImage("validImageLoadSave") != null));
  }

  @Test
  public void testSaveLoadedImage() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/saved_hello2.jpg";
    writeScript("load " + imagePath + " test2", "save " + savePath + " test2");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("test2 should be loaded", (opns.getImage("test2") != null));
    assertTrue("Saved image file should exist", Files.exists(Paths.get(savePath)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveNonLoadedImage() throws IOException {
    String savePath = "test/resources/scripts/nonexistent-save.jpg";
    writeScript("save " + savePath + " nonexistent-image");

    scriptParser.parseScript(SCRIPT_PATH);
  }

  @Test
  public void testSaveToDifferentFormats() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePathJpg = "test/resources/scripts/hello_saved_df_jpg.jpg";
    String savePathPng = "test/resources/scripts/hello_saved_df_png.png";
    String savePathppm = "test/resources/scripts/hello_saved_df_ppm.ppm";

    writeScript("load " + imagePath + " hello", "save " + savePathJpg + " hello",
        "save " + savePathPng + " hello", "save " + savePathppm + " hello"

    );

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("Saved JPG file should exist", Files.exists(Paths.get(savePathJpg)));
    assertTrue("Saved PNG file should exist", Files.exists(Paths.get(savePathPng)));
    assertTrue("Saved PPM file should exist", Files.exists(Paths.get(savePathppm)));

  }

  @Test
  public void testSaveWithOverwrite() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_overwrite.jpg";
    writeScript("load " + imagePath + " hello", "save " + savePath + " hello",
        "save " + savePath + " hello");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("Saved image file should exist", Files.exists(Paths.get(savePath)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveWithInvalidPath() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "invalid/path/hello_save.jpg";
    writeScript("load " + imagePath + " hello", "save " + savePath + " hello");

    scriptParser.parseScript(SCRIPT_PATH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveWithoutLoad() throws IOException {
    String savePath = "test/resources/scripts/nonexistent-save.jpg";
    writeScript("save nonexistent-image " + savePath);

    scriptParser.parseScript(SCRIPT_PATH);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSaveWithNullImageName() throws IOException {
    String imagePath = "test/resources/scripts/test2.jpeg";
    String savePath = "test/resources/scripts/null_save.jpg";
    writeScript("load " + imagePath + " test2", "save null " + savePath);

    scriptParser.parseScript(SCRIPT_PATH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveWithEmptyImageName() throws IOException {
    String imagePath = "test/resources/scripts/test2.jpeg";
    String savePath = "test/resources/scripts/empty_save.jpg";
    writeScript("load " + imagePath + " test2", "save  " + savePath);

    scriptParser.parseScript(SCRIPT_PATH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveWithInvalidCharactersInPath() throws IOException {
    String imagePath = "test/resources/scripts/test2.jpeg";
    String savePath = "test/resources/scripts/invalid<>|:*\"?.jpg";
    writeScript("load " + imagePath + " test2", "save test2 " + savePath);

    scriptParser.parseScript(SCRIPT_PATH);
  }

  @Test
  public void testRedComponent() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_red_component.jpg";
    writeScript("load " + imagePath + " hello", "red-component " + "hello" + " hello_red_component",
        "save " + savePath + " hello_red_component");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_red_component should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testGreenComponent() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_green_component.jpg";
    writeScript("load " + imagePath + " hello", "green-component hello hello_green_component",
        "save " + savePath + " hello_green_component");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_green_component should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testBlueComponent() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_blue_component.jpg";
    writeScript("load " + imagePath + " hello", "blue-component hello hello_blue_component",
        "save " + savePath + " hello_blue_component");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_blue_component should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testValueComponent() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_value_component.jpg";
    writeScript("load " + imagePath + " hello", "value-component hello hello_value_component",
        "save " + savePath + " hello_value_component");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_value_component should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testLumaComponent() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_luma_component.jpg";
    writeScript("load " + imagePath + " hello", "luma-component hello hello_luma_component",
        "save " + savePath + " hello_luma_component");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_luma_component should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testIntensityComponent() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_intensity_component.jpg";
    writeScript("load " + imagePath + " hello",
        "intensity-component hello hello_intensity_component",
        "save " + savePath + " hello_intensity_component");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_intensity_component should be created", Files.exists(Paths.get(savePath)));

  }

  @Test
  public void testHorizontalFlip() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_horizontal_flip.jpg";
    writeScript("load " + imagePath + " hello", "horizontal-flip hello hello_horizontal_flip",
        "save " + savePath + " hello_horizontal_flip");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_horizontal_flip should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testVerticalFlip() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_vertical_flip.jpg";
    writeScript("load " + imagePath + " hello", "vertical-flip hello hello_vertical_flip",
        "save " + savePath + " hello_vertical_flip"

    );

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_vertical_flip should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testBrighten() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_brighten.jpg";
    writeScript("load " + imagePath + " hello", "brighten 50 hello hello_brighten",
        "save " + savePath + " hello_brighten");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_brighten should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testDarken() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_darken.jpg";
    writeScript("load " + imagePath + " hello", "darken 50 hello hello_darken",
        "save " + savePath + " hello_darken");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_darken should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testRgbSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePathRed = "test/resources/scripts/hello_rgb_split_red.jpg";
    String savePathGreen = "test/resources/scripts/hello_rgb_split_green.jpg";
    String savePathBlue = "test/resources/scripts/hello_rgb_split_blue.jpg";
    writeScript("load " + imagePath + " hello",
        "rgb-split hello hello_rgb_split_red hello_rgb_split_green hello_rgb_split_blue",
        "save " + savePathRed + " hello_rgb_split_red",
        "save " + savePathGreen + " hello_rgb_split_green",
        "save " + savePathBlue + " hello_rgb_split_blue");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_rgb_split_red should be created", Files.exists(Paths.get(savePathRed)));
    assertTrue("hello_rgb_split_green should be created", Files.exists(Paths.get(savePathGreen)));
    assertTrue("hello_rgb_split_blue should be created", Files.exists(Paths.get(savePathBlue)));
  }

  @Test
  public void testRgbCombine() throws IOException {
    String imagePathRed = "test/resources/scripts/hello_rgb_split_red.jpg";
    String imagePathGreen = "test/resources/scripts/hello_rgb_split_green.jpg";
    String imagePathBlue = "test/resources/scripts/hello_rgb_split_blue.jpg";
    String savePath = "test/resources/scripts/hello_rgb_combine.jpg";
    writeScript("load " + imagePathRed + " hello_red", "load " + imagePathGreen + " hello_green",
        "load " + imagePathBlue + " hello_blue",
        "rgb-combine hello_rgb_combine hello_red hello_green hello_blue",
        "save " + savePath + " hello_rgb_combine");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_rgb_combine should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testBlur() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_blur.jpg";
    writeScript("load " + imagePath + " hello", "blur hello hello_blur",
        "save " + savePath + " hello_blur");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_blur should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testBlurWithSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_blur_split.jpg";
    writeScript("load " + imagePath + " hello", "blur hello hello_blur_split split 80",
        "save " + savePath + " hello_blur_split");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_blur should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testSharpen() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_sharpen.jpg";
    writeScript("load " + imagePath + " hello", "sharpen hello hello_sharpen",
        "save " + savePath + " hello_sharpen");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_sharpen should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testSharpenWithSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_sharpen_split.jpg";
    writeScript("load " + imagePath + " hello", "sharpen hello hello_sharpen_split split 40",
        "save " + savePath + " hello_sharpen_split");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_sharpen should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testSepia() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_sepia.jpg";
    writeScript("load " + imagePath + " hello", "sepia hello hello_sepia",
        "save " + savePath + " hello_sepia");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_sepia should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testSepiaWithSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_sepia_split.jpg";
    writeScript("load " + imagePath + " hello", "sepia hello hello_sepia_split split 30",
        "save " + savePath + " hello_sepia_split");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_sepia_split should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testGreyscale() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_greyscale.jpg";
    writeScript("load " + imagePath + " hello", "greyscale hello hello_greyscale",
        "save " + savePath + " hello_greyscale");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_greyscale should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testGreyscaleWithSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_greyscale_split.jpg";
    writeScript("load " + imagePath + " hello", "greyscale hello hello_greyscale_split split 60",
        "save " + savePath + " hello_greyscale_split");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_greyscale_split should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testHistogram() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_histogram.jpg";
    writeScript("load " + imagePath + " hello", "histogram hello hello_histogram",
        "save " + savePath + " hello_histogram");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_histogram should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testColorCorrection() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_colorCorrection.jpg";
    writeScript("load " + imagePath + " hello", "color-correct hello hello_colorCorrection",
        "save " + savePath + " hello_colorCorrection");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_colorCorrection should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testColorCorrectionWithSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_colorCorrection_split.jpg";
    writeScript("load " + imagePath + " hello",
        "color-correct hello hello_colorCorrection_split split 50",
        "save " + savePath + " hello_colorCorrection_split");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_colorCorrection_split should be created", Files.exists(Paths.get(savePath)));
  }


  @Test
  public void testLevelAdjustWithoutSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_levelAdjust.jpg";
    writeScript("load " + imagePath + " hello", "level-adjust 0 100 200 hello hello_levelAdjust",
        "save " + savePath + " hello_levelAdjust");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_levelAdjust should be created", Files.exists(Paths.get(savePath)));
  }

  @Test
  public void testLevelAdjustWithSplit() throws IOException {
    String imagePath = "test/resources/scripts/hello.jpeg";
    String savePath = "test/resources/scripts/hello_levelAdjust_split.jpg";
    writeScript("load " + imagePath + " hello",
        "level-adjust 0 100 200 hello hello_levelAdjust_split split 50",
        "save " + savePath + " hello_levelAdjust_split");

    scriptParser.parseScript(SCRIPT_PATH);

    assertTrue("hello_levelAdjust_split should be created", Files.exists(Paths.get(savePath)));
  }


}
