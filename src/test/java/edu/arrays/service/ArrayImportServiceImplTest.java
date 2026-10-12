package edu.arrays.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import edu.arrays.factory.IntArrayFactory;
import edu.arrays.parser.RegexArrayLineParser;
import edu.arrays.reader.ArrayDataReader;
import edu.arrays.validator.RegexArrayLineValidator;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArrayImportServiceImplTest {

  private static final String PATH = "data/any.txt";
  private static final List<String> LINES =
      List.of("1; 2; 3", "1, 2, x3, 6..5, 77", "", "11- 2 – 42-");
  private static final List<IntArrayEntity> EXPECTED = List.of(
      new IntArrayEntity(new int[] {1, 2, 3}),
      new IntArrayEntity(new int[] {}),
      new IntArrayEntity(new int[] {11, 2, 42}));

  @Test
  void importArraysShouldSkipInvalidLines() throws ArrayException {
    // given
    ArrayDataReader reader = path -> LINES;
    ArrayImportService service = new ArrayImportServiceImpl(reader,
        new RegexArrayLineValidator(), new RegexArrayLineParser(), new IntArrayFactory());
    // when
    List<IntArrayEntity> actual = service.importArrays(PATH);
    // then
    assertEquals(EXPECTED, actual);
  }

  @Test
  void importArraysShouldPassReaderException() {
    // given
    ArrayDataReader reader = path -> {
      throw new ArrayException("Cannot read");
    };
    ArrayImportService service = new ArrayImportServiceImpl(reader,
        new RegexArrayLineValidator(), new RegexArrayLineParser(), new IntArrayFactory());
    // when and then
    assertThrows(ArrayException.class, () -> service.importArrays(PATH));
  }
}
