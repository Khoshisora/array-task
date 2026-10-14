package edu.arrays.comparator;

import edu.arrays.entity.IntArrayEntity;
import java.util.Comparator;
import java.util.UUID;

public final class IntArrayComparators {

  private static final Comparator<UUID> NATURAL_ORDER = Comparator.naturalOrder();
  private static final Comparator<UUID> ID_ORDER = Comparator.nullsFirst(NATURAL_ORDER);

  public static final Comparator<IntArrayEntity> BY_ID =
      Comparator.comparing(IntArrayEntity::getId, ID_ORDER);

  public static final Comparator<IntArrayEntity> BY_SIZE =
      Comparator.comparingInt(IntArrayEntity::getSize);

  public static final Comparator<IntArrayEntity> BY_FIRST_ELEMENT =
      (first, second) -> Integer.compare(firstElement(first), firstElement(second));

  private static final int EMPTY_ARRAY_RANK = Integer.MIN_VALUE;

  private IntArrayComparators() {
  }

  private static int firstElement(IntArrayEntity entity) {
    int[] elements = entity.getElements();
    int result = EMPTY_ARRAY_RANK;
    if (elements.length > 0) {
      result = elements[0];
    }
    return result;
  }
}
