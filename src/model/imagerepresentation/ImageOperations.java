package model.imagerepresentation;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling operations on images. It can store images and retrieve
 * them.
 */
public class ImageOperations {

  private static final Map<String, Image> images = new HashMap<>();

  /**
   * Gets an image by its name.
   *
   * @param imageName the name of the image to retrieve.
   * @return the Image object if found.
   * @throws IllegalArgumentException if no image with the given name is found.
   */
  public Image getImage(String imageName) {
    Image image = images.get(imageName);
    if (image == null) {
      throw new IllegalArgumentException("No image found with the name: " + imageName);
    }
    return image;
  }

  /**
   * Saves an image with a given name.
   *
   * @param destName the name to save the image under.
   * @param image    the Image object to save.
   * @throws IllegalArgumentException if the image is null.
   */
  public void saveImage(String destName, Image image) {
    if (image == null || destName == null || destName.trim().isEmpty()) {
      throw new IllegalArgumentException("Cannot save a null image.");
    }
    images.put(destName, image);
  }
}
