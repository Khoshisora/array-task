package edu.arrays.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.arrays.exception.ArrayException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TextFileArrayDataReaderTest {

  private static final String FILE_CONTENT = "1; 2; 3\n\n1, x, 3\n";
  private static final List<String> EXPECTED_LINES = List.of("1; 2; 3", "", "1, x, 3");
  private static final String MISSING_FILE = "data/missing_file.txt";

  @TempDir
  Path tempDir;

  @Test
  void readLinesShouldReturnAllLinesIncludingEmpty() throws IOException, ArrayException {
    // given
    Path file = tempDir.resolve("arrays.txt");
    Files.writeString(file, FILE_CONTENT, StandardCharsets.UTF_8);
    ArrayDataReader reader = new TextFileArrayDataReader();
    String path = file.toString();
    // when
    List<String> actual = reader.readLines(path);
    // then
    assertEquals(EXPECTED_LINES, actual);
  }

  @Test
  void readLinesShouldThrowExceptionForMissingFile() {
    // given
    ArrayDataReader reader = new TextFileArrayDataReader();
    // when and then
    assertThrows(ArrayException.class, () -> reader.readLines(MISSING_FILE));
  }
}
