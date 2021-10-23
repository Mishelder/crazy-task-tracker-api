package by.idf.crazy.task.tracker.api.factories;

import by.idf.crazy.task.tracker.api.dto.ProjectDto;
import by.idf.crazy.task.tracker.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

  public ProjectDto makeProjectDto(ProjectEntity entity){
    return ProjectDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
