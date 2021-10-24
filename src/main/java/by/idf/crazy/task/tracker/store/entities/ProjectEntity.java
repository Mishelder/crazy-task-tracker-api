package by.idf.crazy.task.tracker.store.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Table(name = "project")
@Getter
@Setter
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(unique = true)
  String name;
  @Builder.Default
  LocalDateTime createdAt = LocalDateTime.now();
  @Builder.Default
  @OneToMany
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  List<TaskStateEntity> taskStateEntities = new ArrayList<>();
}
