package by.idf.crazy.task.tracker.api.factories;

import by.idf.crazy.task.tracker.api.dto.TaskDto;
import by.idf.crazy.task.tracker.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

  public TaskDto makeProjectDto(TaskEntity entity) {
    return TaskDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .createdAt(entity.getCreatedAt())
        .description(entity.getDescription())
        .build();
  }
}
