package by.idf.crazy.task.tracker.api.factories;

import by.idf.crazy.task.tracker.api.dto.TaskStateDto;
import by.idf.crazy.task.tracker.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {

  public TaskStateDto makeProjectDto(TaskStateEntity entity) {
    return TaskStateDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .createdAt(entity.getCreatedAt())
        .ordinal(entity.getOrdinal())
        .build();
  }
}
