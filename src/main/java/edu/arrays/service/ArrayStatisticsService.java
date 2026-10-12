package edu.arrays.service;

import edu.arrays.entity.IntArrayEntity;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public interface ArrayStatisticsService {

  OptionalInt findMin(IntArrayEntity entity);

  OptionalInt findMax(IntArrayEntity entity);

  OptionalLong findSum(IntArrayEntity entity);

  OptionalDouble findAverage(IntArrayEntity entity);
}
