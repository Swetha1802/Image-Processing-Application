package view;

/**
 * Interface which holds the skeleton of the types of messages to be displayed.
 */
public interface ImageLoaderView {

  /**
   * Method to display the processing status when the image operation is on going.
   */
  void displayProcessingMessage();

  /**
   * Method to display the completion status once the image gets saved.
   */
  void displayCompletionMessage();
}
