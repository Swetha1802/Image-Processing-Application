package controller;

import java.util.Scanner;
import model.imagerepresentation.ImageOperations;
import view.ConsoleImageLoaderView;
import view.GUI;

/**
 * The Main class runs the image manipulation program. It can execute a script, run in interactive
 * text mode, or launch the GUI.
 */
public class Main {

  /**
   * Main function of the code, where the application begins by getting user arguments.
   *
   * @param args is the command line argument the user feeds in.
   */
  public static void main(String[] args) {
    // Checks the arguments to determine how to run the program
    if (args.length == 0) {
      // No arguments - launch the GUI
      javax.swing.SwingUtilities.invokeLater(() -> new GUI());
    } else if (args.length == 1 && "-text".equalsIgnoreCase(args[0])) {
      // Interactive text mode
      runInteractiveMode();
    } else if (args.length == 2 && "-file".equalsIgnoreCase(args[0])) {
      // Script mode
      runScriptMode(args[1]);
    } else {
      System.out.println("Invalid arguments. Usage:");
      System.out.println("java -jar Program.jar -file path-of-script-file");
      System.out.println("java -jar Program.jar -text");
      System.out.println("java -jar Program.jar");
    }
  }

  /**
   * Runs the program in interactive text mode.
   */
  private static void runInteractiveMode() {
    // Creates an instance of ImageLoader to load images.
    ImageLoader imageLoader = new ImageLoader();

    // Creates an instance of ImageOperations using the images loaded by ImageLoader.
    ImageOperations imageOperations = new ImageOperations();

    ConsoleImageLoaderView view = new ConsoleImageLoaderView();

    // Creates a ScriptParser to handle and parse script files interactively.
    ScriptParser scriptParser = new ScriptParser(imageLoader, imageOperations, view);

    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "Interactive mode started. Type commands one line at a time. Type 'exit' to quit.");

    while (true) {
      System.out.print("> ");
      String inputLine = scanner.nextLine().trim();
      if ("exit".equalsIgnoreCase(inputLine)) {
        System.out.println("Exiting interactive mode.");
        break;
      }
      try {
        scriptParser.executeLine(inputLine);
      } catch (Exception e) {
        System.out.println("Error processing command: " + e.getMessage());
      }
    }

    scanner.close();
  }

  /**
   * Runs the program in script mode with the given script file path.
   *
   * @param scriptPath The path to the script file to be executed.
   */
  private static void runScriptMode(String scriptPath) {
    // Creates an instance of ImageLoader to load images.
    ImageLoader imageLoader = new ImageLoader();

    // Creates an instance of ImageOperations using the images loaded by ImageLoader.
    ImageOperations imageOperations = new ImageOperations();

    ConsoleImageLoaderView view = new ConsoleImageLoaderView();

    // Creates a ScriptParser to handle and parse script files.
    ScriptParser scriptParser = new ScriptParser(imageLoader, imageOperations, view);

    try {
      scriptParser.parseScript(scriptPath);
    } catch (Exception e) {
      System.out.println("Error processing script: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

