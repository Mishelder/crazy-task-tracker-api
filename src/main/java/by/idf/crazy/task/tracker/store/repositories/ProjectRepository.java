package by.idf.crazy.task.tracker.store.repositories;

import by.idf.crazy.task.tracker.store.entities.ProjectEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

  Optional<ProjectEntity> findByName(String name);
}
