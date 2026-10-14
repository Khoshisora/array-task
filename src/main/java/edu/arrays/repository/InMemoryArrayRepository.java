package edu.arrays.repository;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.observer.AbstractArraySubject;
import edu.arrays.observer.ArrayObserver;
import edu.arrays.specification.ArrayAggregate;
import edu.arrays.specification.Specification;
import edu.arrays.warehouse.Warehouse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InMemoryArrayRepository extends AbstractArraySubject implements ArrayRepository {

  private static final Logger LOGGER = LogManager.getLogger();
  private static final int NO_INDEX = -1;

  private final List<IntArrayEntity> entities = new ArrayList<>();

  @Override
  public boolean add(IntArrayEntity entity) {
    boolean added = false;
    if (entity != null) {
      entity.setId(UUID.randomUUID());
      subscribe(entity);
      entities.add(entity);
      LOGGER.info("Array with id {} added, repository size {}", entity.getId(), entities.size());
      notifyArrayAdded(entity);
      added = true;
    }
    return added;
  }

  @Override
  public boolean remove(IntArrayEntity entity) {
    int index = indexOf(entity);
    boolean removed = index != NO_INDEX;
    if (removed) {
      IntArrayEntity stored = entities.remove(index);
      unsubscribe(stored);
      LOGGER.info("Array with id {} removed, repository size {}", stored.getId(), entities.size());
      notifyArrayRemoved(stored);
    }
    return removed;
  }

  @Override
  public List<IntArrayEntity> findAll() {
    return List.copyOf(entities);
  }

  @Override
  public Optional<IntArrayEntity> findById(UUID id) {
    int index = indexOf(id);
    IntArrayEntity found = null;
    if (index != NO_INDEX) {
      found = entities.get(index);
    }
    return Optional.ofNullable(found);
  }

  @Override
  public List<IntArrayEntity> findBySpecification(Specification<ArrayAggregate> specification) {
    List<IntArrayEntity> matches = new ArrayList<>();
    for (IntArrayEntity entity : entities) {
      ArrayAggregate aggregate = buildAggregate(entity);
      if (specification.isSatisfiedBy(aggregate)) {
        matches.add(entity);
      }
    }
    LOGGER.info("Specification search returned {} arrays", matches.size());
    return matches;
  }

  @Override
  public List<IntArrayEntity> sortBy(Comparator<IntArrayEntity> comparator) {
    List<IntArrayEntity> sorted = new ArrayList<>(entities);
    sorted.sort(comparator);
    return sorted;
  }

  /**
   * Subscribes the new observer to the repository and to every stored array, so it receives both
   * the membership events and the changes of the elements.
   */
  @Override
  protected void onObserverAdded(ArrayObserver observer) {
    for (IntArrayEntity entity : entities) {
      entity.addObserver(observer);
    }
  }

  @Override
  protected void onObserverRemoved(ArrayObserver observer) {
    for (IntArrayEntity entity : entities) {
      entity.removeObserver(observer);
    }
  }

  private void subscribe(IntArrayEntity entity) {
    for (ArrayObserver observer : subscribedObservers()) {
      entity.addObserver(observer);
    }
  }

  /**
   * Finds the stored position of the array, the repository owns the identifiers.
   */
  private int indexOf(IntArrayEntity entity) {
    int index = NO_INDEX;
    if (entity != null) {
      UUID id = entity.getId();
      index = indexOf(id);
    }
    return index;
  }

  private int indexOf(UUID id) {
    int index = NO_INDEX;
    if (id != null) {
      for (int i = 0; i < entities.size(); i++) {
        IntArrayEntity stored = entities.get(i);
        UUID storedId = stored.getId();
        if (id.equals(storedId)) {
          index = i;
          break;
        }
      }
    }
    return index;
  }

  private void unsubscribe(IntArrayEntity entity) {
    for (ArrayObserver observer : subscribedObservers()) {
      entity.removeObserver(observer);
    }
  }

  /**
   * Pairs the array with the values the warehouse keeps for it.
   */
  private ArrayAggregate buildAggregate(IntArrayEntity entity) {
    Warehouse warehouse = Warehouse.getInstance();
    OptionalLong sum = warehouse.findSum(entity);
    OptionalDouble average = warehouse.findAverage(entity);
    OptionalInt min = warehouse.findMin(entity);
    OptionalInt max = warehouse.findMax(entity);
    return new ArrayAggregate(entity, sum, average, min, max);
  }
}
