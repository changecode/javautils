package com.java.utils.qrcode;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Writes a {@link com.google.zxing.common.BitMatrix} to {@link java.awt.image.BufferedImage},
 * file or stream. Provided here instead of core since it depends on
 * Java SE libraries.
 *
 * @author Sean Owen
 */
public final class MatrixToImageWriter {

  private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

  private MatrixToImageWriter() {}

  /**
   * Renders a {@link com.google.zxing.common.BitMatrix} as an image, where "false" bits are rendered
   * as white, and "true" bits are rendered as black.
   */
  public static BufferedImage toBufferedImage(BitMatrix matrix) {
    return toBufferedImage(matrix, DEFAULT_CONFIG);
  }

  /**
   * As {@link #toBufferedImage(com.google.zxing.common.BitMatrix)}, but allows customization of the output.
   */
  public static BufferedImage toBufferedImage(BitMatrix matrix, MatrixToImageConfig config) {
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    BufferedImage image = new BufferedImage(width, height, config.getBufferedImageColorModel());
    int onColor = config.getPixelOnColor();
    int offColor = config.getPixelOffColor();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
      }
    }
    return image;
  }

  /**
   * Writes a {@link com.google.zxing.common.BitMatrix} to a file.
   *
   * @see #toBufferedImage(com.google.zxing.common.BitMatrix)
   */
  public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
    writeToFile(matrix, format, file, DEFAULT_CONFIG);
  }

  /**
   * As {@link #writeToFile(com.google.zxing.common.BitMatrix, String, java.io.File)}, but allows customization of the output.
   */
  public static void writeToFile(BitMatrix matrix, String format, File file, MatrixToImageConfig config) 
      throws IOException {  
    BufferedImage image = toBufferedImage(matrix, config);
    if (!ImageIO.write(image, format, file)) {
      throw new IOException("Could not write an image of format " + format + " to " + file);
    }
  }

  /**
   * Writes a {@link com.google.zxing.common.BitMatrix} to a stream.
   *
   * @see #toBufferedImage(com.google.zxing.common.BitMatrix)
   */
  public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
    writeToStream(matrix, format, stream, DEFAULT_CONFIG);
  }

  /**
   * As {@link #writeToStream(com.google.zxing.common.BitMatrix, String, java.io.OutputStream)}, but allows customization of the output.
   */
  public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, MatrixToImageConfig config) 
      throws IOException {  
    BufferedImage image = toBufferedImage(matrix, config);
    if (!ImageIO.write(image, format, stream)) {
      throw new IOException("Could not write an image of format " + format);
    }
  }

}
