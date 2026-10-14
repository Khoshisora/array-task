package edu.arrays.repository;

import edu.arrays.entity.IntArrayEntity;
import edu.arrays.observer.ArraySubject;
import edu.arrays.specification.ArrayAggregate;
import edu.arrays.specification.Specification;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ArrayRepository extends ArraySubject {

  boolean add(IntArrayEntity entity);

  boolean remove(IntArrayEntity entity);

  List<IntArrayEntity> findAll();

  Optional<IntArrayEntity> findById(UUID id);

  List<IntArrayEntity> findBySpecification(Specification<ArrayAggregate> specification);

  List<IntArrayEntity> sortBy(Comparator<IntArrayEntity> comparator);
}
