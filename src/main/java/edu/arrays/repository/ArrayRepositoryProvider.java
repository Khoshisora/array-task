package edu.arrays.repository;


public final class ArrayRepositoryProvider {

  private static InMemoryArrayRepository repository;

  private ArrayRepositoryProvider() {
  }

  public static ArrayRepository getRepository() {
    if (repository == null) {
      repository = new InMemoryArrayRepository();
    }
    return repository;
  }
}
