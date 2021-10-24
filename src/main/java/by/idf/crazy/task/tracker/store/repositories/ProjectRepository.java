package by.idf.crazy.task.tracker.store.repositories;

import by.idf.crazy.task.tracker.store.entities.ProjectEntity;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

  Optional<ProjectEntity> findByName(String name);

  Stream<ProjectEntity> streamAllBy();

  Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String name);
}
