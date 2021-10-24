package by.idf.crazy.task.tracker.api.controllers;

import by.idf.crazy.task.tracker.api.dto.ProjectDto;
import by.idf.crazy.task.tracker.api.exceptions.BadRequestException;
import by.idf.crazy.task.tracker.api.factories.ProjectDtoFactory;
import by.idf.crazy.task.tracker.store.entities.ProjectEntity;
import by.idf.crazy.task.tracker.store.repositories.ProjectRepository;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
public class ProjectController {

  ProjectDtoFactory projectDtoFactory;
  ProjectRepository projectRepository;

  private static final String CREATE_PROJECT = "/api/projects";

  @PostMapping(CREATE_PROJECT)
  public ProjectDto createProject(@RequestParam String name) {
    projectRepository
        .findByName(name)
        .ifPresent(pjt -> {
          throw new BadRequestException("Project with name " + name + " exists.");
        });
    ProjectEntity project = projectRepository
        .saveAndFlush(ProjectEntity.builder()
            .name(name)
            .build());
    return projectDtoFactory.makeProjectDto(project);
  }
}

