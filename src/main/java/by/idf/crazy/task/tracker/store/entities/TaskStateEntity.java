package by.idf.crazy.task.tracker.store.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Table(name = "task_state")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TaskStateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(unique = true)
  String name;
  LocalDateTime createdAt = LocalDateTime.now();
  Long ordinal;
  @OneToMany
  List<TaskEntity> taskEntities = new ArrayList<>();
}
