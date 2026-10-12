package edu.arrays.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class RegexArrayLineParserTest {

  private static final int[] ONE_TWO_THREE = {1, 2, 3};
  private static final int[] NO_ELEMENTS = {};
  private static final int[] WITH_TRAILING_DASH = {11, 2, 42};

  @Test
  void parseShouldSplitByCommas() {
    // given
    ArrayLineParser parser = new RegexArrayLineParser();
    // when
    int[] actual = parser.parse("1, 2, 3");
    // then
    assertArrayEquals(ONE_TWO_THREE, actual);
  }

  @Test
  void parseShouldSplitBySemicolons() {
    // given
    ArrayLineParser parser = new RegexArrayLineParser();
    // when
    int[] actual = parser.parse("1; 2; 3");
    // then
    assertArrayEquals(ONE_TWO_THREE, actual);
  }

  @Test
  void parseShouldSplitByDashes() {
    // given
    ArrayLineParser parser = new RegexArrayLineParser();
    // when
    int[] actual = parser.parse("1 – 2 - 3");
    // then
    assertArrayEquals(ONE_TWO_THREE, actual);
  }

  @Test
  void parseShouldSplitBySpaces() {
    // given
    ArrayLineParser parser = new RegexArrayLineParser();
    // when
    int[] actual = parser.parse("1 2 3");
    // then
    assertArrayEquals(ONE_TWO_THREE, actual);
  }

  @Test
  void parseShouldIgnoreTrailingSeparator() {
    // given
    ArrayLineParser parser = new RegexArrayLineParser();
    // when
    int[] actual = parser.parse("11- 2 – 42-");
    // then
    assertArrayEquals(WITH_TRAILING_DASH, actual);
  }

  @Test
  void parseShouldReturnEmptyArrayForBlankLine() {
    // given
    ArrayLineParser parser = new RegexArrayLineParser();
    // when
    int[] actual = parser.parse("  ");
    // then
    assertArrayEquals(NO_ELEMENTS, actual);
  }
}
