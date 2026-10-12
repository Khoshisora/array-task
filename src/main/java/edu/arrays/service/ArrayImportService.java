package edu.arrays.service;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.exception.ArrayException;
import java.util.List;

public interface ArrayImportService {

  List<IntArrayEntity> importArrays(String path) throws ArrayException;
}
