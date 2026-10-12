package edu.arrays.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexArrayLineParser implements ArrayLineParser {

  private static final String NUMBER_REGEX = "\\d+";
  private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

  @Override
  public int[] parse(String line) {
    List<Integer> numbers = new ArrayList<>();
    Matcher matcher = NUMBER_PATTERN.matcher(line);
    while (matcher.find()) {
      String token = matcher.group();
      int number = Integer.parseInt(token);
      numbers.add(number);
    }
    int[] elements = new int[numbers.size()];
    for (int i = 0; i < elements.length; i++) {
      elements[i] = numbers.get(i);
    }
    return elements;
  }
}
