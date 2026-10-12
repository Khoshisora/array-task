package edu.arrays.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RegexArrayLineValidatorTest {

  private static final String COMMAS = "1, 2, 3";
  private static final String SEMICOLONS = "1; 2; 3";
  private static final String DASHES = "1 – 2 - 3";
  private static final String SPACES = "3 4 7";
  private static final String TRAILING_DASH = "11- 2 – 42-";
  private static final String BLANK = " ";
  private static final String EMPTY = "";
  private static final String LETTER_INSIDE_NUMBER = "1y1 21 32";
  private static final String LETTER_TOKEN = "1, 2, x3, 6..5, 77";
  private static final String TOO_LONG_NUMBER = "99999999999 1";
  private static final String DOTS = "6..5";

  @ParameterizedTest
  @ValueSource(strings = {COMMAS, SEMICOLONS, DASHES, SPACES, TRAILING_DASH, BLANK, EMPTY})
  void isValidShouldReturnTrueForCorrectLine(String line) {
    // given
    ArrayLineValidator validator = new RegexArrayLineValidator();
    // when
    boolean actual = validator.isValid(line);
    // then
    assertTrue(actual);
  }

  @ParameterizedTest
  @ValueSource(strings = {LETTER_INSIDE_NUMBER, LETTER_TOKEN, TOO_LONG_NUMBER, DOTS})
  @NullSource
  void isValidShouldReturnFalseForIncorrectLine(String line) {
    // given
    ArrayLineValidator validator = new RegexArrayLineValidator();
    // when
    boolean actual = validator.isValid(line);
    // then
    assertFalse(actual);
  }
}
