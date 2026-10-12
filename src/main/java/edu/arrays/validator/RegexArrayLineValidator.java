package edu.arrays.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexArrayLineValidator implements ArrayLineValidator {

  private static final String SEPARATOR_REGEX = "[\\s,;]";
  private static final String NUMBER_REGEX = "\\d{1,9}";
  private static final String BLANK_LINE_REGEX = "\\s*";
  private static final String NUMBERS_LINE_REGEX = SEPARATOR_REGEX + "*" + NUMBER_REGEX
      + "(?:" + SEPARATOR_REGEX + "+" + NUMBER_REGEX + ")*" + SEPARATOR_REGEX + "*";
  private static final Pattern VALID_LINE_PATTERN =
      Pattern.compile(BLANK_LINE_REGEX + "|" + NUMBERS_LINE_REGEX);

  @Override
  public boolean isValid(String line) {
    boolean valid = false;
    if (line != null) {
      Matcher matcher = VALID_LINE_PATTERN.matcher(line);
      valid = matcher.matches();
    }
    return valid;
  }
}
