package by.idf.crazy.task.tracker.store.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Table(name = "task")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TaskEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(unique = true)
  String name;
  LocalDateTime createdAt = LocalDateTime.now();
  String description;
}
