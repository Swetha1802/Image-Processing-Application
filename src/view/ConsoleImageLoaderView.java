package view;

/**
 * Console Loader which comes under the view component , to display the user with needed messages
 * while processing and once the operation gets over.
 */
public class ConsoleImageLoaderView implements ImageLoaderView {

  @Override
  public void displayProcessingMessage() {
    System.out.println("Processing...");
  }

  @Override
  public void displayCompletionMessage() {
    System.out.println("Done! Check the folder for modified images.");
  }
}
