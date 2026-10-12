package edu.arrays.reader;

import edu.arrays.exception.ArrayException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TextFileArrayDataReader implements ArrayDataReader {

  @Override
  public List<String> readLines(String path) throws ArrayException {
    Path filePath = Paths.get(path);
    try {
      return Files.readAllLines(filePath, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new ArrayException("Cannot read data file: " + path, e);
    }
  }
}
