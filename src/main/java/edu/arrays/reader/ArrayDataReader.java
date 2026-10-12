package edu.arrays.reader;

import edu.arrays.exception.ArrayException;
import java.util.List;

public interface ArrayDataReader {

  List<String> readLines(String path) throws ArrayException;
}
