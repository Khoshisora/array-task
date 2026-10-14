package edu.arrays.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.arrays.exception.ArrayException;
import edu.arrays.observer.RecordingArrayObserver;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class IntArrayEntityTest {

  private static final int[] ELEMENTS = {3, 1, 2};
  private static final int[] OTHER_ELEMENTS = {7, 8};
  private static final String EXPECTED_STRING = "IntArrayEntity[3, 1, 2]";
  private static final UUID ASSIGNED_ID = UUID.fromString("00000000-0000-0000-0000-000000000042");
  private static final int FIRST_INDEX = 0;
  private static final int OUT_OF_BOUNDS_INDEX = 5;
  private static final int MISSING_INDEX = -1;
  private static final int NEW_VALUE = 100;
  private static final int STORED_VALUE = 3;
  private static final int MISSING_VALUE = 77;

  @Test
  void getElementsShouldReturnCopyOfElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    int[] expected = {3, 1, 2};
    int[] returned = entity.getElements();
    returned[0] = 100;
    // when
    int[] actual = entity.getElements();
    // then
    assertArrayEquals(expected, actual);
  }

  @Test
  void setElementsShouldReplaceElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    entity.setElements(OTHER_ELEMENTS);
    // when
    int[] actual = entity.getElements();
    // then
    assertArrayEquals(OTHER_ELEMENTS, actual);
  }

  @Test
  void getSizeShouldReturnNumberOfElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    int actual = entity.getSize();
    // then
    assertEquals(3, actual);
  }

  @Test
  void equalsShouldBeTrueForSameElements() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(ELEMENTS);
    // when
    boolean actual = first.equals(second);
    // then
    assertTrue(actual);
  }

  @Test
  void equalsShouldBeFalseForDifferentElements() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(OTHER_ELEMENTS);
    // then
    assertNotEquals(first, second);
  }

  @Test
  void hashCodeShouldBeEqualForSameElements() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    IntArrayEntity second = new IntArrayEntity(ELEMENTS);
    // when
    int actual = first.hashCode();
    // then
    assertEquals(second.hashCode(), actual);
  }

  @Test
  void getIdShouldReturnNullBeforeStoring() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    UUID actual = entity.getId();
    // then
    assertNull(actual);
  }

  @Test
  void setIdShouldAssignIdentifier() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    entity.setId(ASSIGNED_ID);
    // when
    UUID actual = entity.getId();
    // then
    assertEquals(ASSIGNED_ID, actual);
  }

  @Test
  void setElementShouldReplaceSingleElement() throws ArrayException {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    int[] actual = entity.getElements();
    // then
    assertArrayEquals(new int[] {NEW_VALUE, 1, 2}, actual);
  }

  @Test
  void setElementShouldThrowForIndexAboveTheSize() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when and then
    assertThrows(ArrayException.class, () -> entity.setElement(OUT_OF_BOUNDS_INDEX, NEW_VALUE));
  }

  @Test
  void setElementShouldThrowForNegativeIndex() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when and then
    assertThrows(ArrayException.class, () -> entity.setElement(MISSING_INDEX, NEW_VALUE));
  }

  @Test
  void containsElementShouldBeTrueForStoredElement() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    boolean actual = entity.containsElement(STORED_VALUE);
    // then
    assertTrue(actual);
  }

  @Test
  void containsElementShouldBeFalseForMissingElement() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    boolean actual = entity.containsElement(MISSING_VALUE);
    // then
    assertFalse(actual);
  }

  @Test
  void setElementShouldNotifyObservers() throws ArrayException {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    entity.addObserver(observer);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void setElementShouldNotNotifyObserversForTheSameValue() throws ArrayException {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    entity.addObserver(observer);
    entity.setElement(FIRST_INDEX, STORED_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(), actual);
  }

  @Test
  void setElementsShouldNotifyObservers() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    entity.addObserver(observer);
    entity.setElements(OTHER_ELEMENTS);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void setElementsShouldNotNotifyObserversForTheSameElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    entity.addObserver(observer);
    entity.setElements(ELEMENTS);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(), actual);
  }

  @Test
  void addObserverShouldIgnoreNullObserver() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when and then
    assertDoesNotThrow(() -> entity.addObserver(null));
  }

  @Test
  void addObserverShouldNotSubscribeTheSameObserverTwice() throws ArrayException {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    entity.addObserver(observer);
    entity.addObserver(observer);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(entity), actual);
  }

  @Test
  void removeObserverShouldStopNotifications() throws ArrayException {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    RecordingArrayObserver observer = new RecordingArrayObserver();
    entity.addObserver(observer);
    entity.removeObserver(observer);
    entity.setElement(FIRST_INDEX, NEW_VALUE);
    // when
    List<IntArrayEntity> actual = observer.getChangedEntities();
    // then
    assertEquals(List.of(), actual);
  }

  @Test
  void equalsShouldBeTrueForTheSameElementsWithDifferentIdentifiers() {
    // given
    IntArrayEntity first = new IntArrayEntity(ELEMENTS);
    first.setId(ASSIGNED_ID);
    IntArrayEntity second = new IntArrayEntity(ELEMENTS);
    // then
    assertEquals(first, second);
  }

  @Test
  void toStringShouldContainElements() {
    // given
    IntArrayEntity entity = new IntArrayEntity(ELEMENTS);
    // when
    String actual = entity.toString();
    // then
    assertEquals(EXPECTED_STRING, actual);
  }
}
