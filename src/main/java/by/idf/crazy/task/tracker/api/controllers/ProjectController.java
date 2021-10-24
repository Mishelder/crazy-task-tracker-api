package by.idf.crazy.task.tracker.api.controllers;

import by.idf.crazy.task.tracker.api.dto.AckDto;
import by.idf.crazy.task.tracker.api.dto.ProjectDto;
import by.idf.crazy.task.tracker.api.exceptions.BadRequestException;
import by.idf.crazy.task.tracker.api.exceptions.NotFoundException;
import by.idf.crazy.task.tracker.api.factories.ProjectDtoFactory;
import by.idf.crazy.task.tracker.store.entities.ProjectEntity;
import by.idf.crazy.task.tracker.store.repositories.ProjectRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
public class ProjectController {

  ProjectDtoFactory projectDtoFactory;
  ProjectRepository projectRepository;

  private static final String CREATE_PROJECT = "/api/projects/";
  private static final String EDIT_PROJECT = "/api/projects/{project_id}";
  private static final String FETCH_PROJECTS = "/api/projects";
  private static final String DELETE_PROJECT = "/api/projects/{project_id}";
  private static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";

  @PostMapping(CREATE_PROJECT)
  public ProjectDto createProject(@RequestParam String projectName) {

    if (projectName.trim().isEmpty()) {
      throw new BadRequestException("Project name can't be empty");
    }

    projectRepository
        .findByName(projectName)
        .ifPresent(pjt -> {
          throw new BadRequestException("Project with name " + projectName + " exists.");
        });
    ProjectEntity project = projectRepository
        .saveAndFlush(ProjectEntity.builder()
            .name(projectName)
            .build());
    return projectDtoFactory.makeProjectDto(project);
  }

  @PatchMapping(EDIT_PROJECT)
  public ProjectDto editProject(@PathVariable("project_id") Long projectId, @RequestParam String projectName) {

    if (projectName.trim().isEmpty()) {
      throw new BadRequestException("Name can't be empty");
    }

    ProjectEntity projectEntity = getProjectEntityOrThrowException(projectId);

    projectRepository.findByName(projectName)
        .filter(otherProjectEntity -> !Objects.equals(otherProjectEntity.getId(), projectId))
        .ifPresent(otherProjectEntity -> {
          throw new BadRequestException("Project with name " + projectName + " exists.");
        });

    projectEntity.setName(projectName);
    projectEntity = projectRepository.saveAndFlush(projectEntity);
    return projectDtoFactory.makeProjectDto(projectEntity);
  }

  @GetMapping(FETCH_PROJECTS)
  public List<ProjectDto> fetchProjects(
      @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

    optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
    Stream<ProjectEntity> projectEntityStream = optionalPrefixName.map(
            projectRepository::streamAllByNameStartsWithIgnoreCase)
        .orElseGet(projectRepository::streamAllBy);

    return projectEntityStream
        .map(projectDtoFactory::makeProjectDto)
        .collect(Collectors.toList());
  }

  @DeleteMapping(DELETE_PROJECT)
  public AckDto deleteProject(@PathVariable("project_id") Long projectId) {
    getProjectEntityOrThrowException(projectId);

    projectRepository.deleteById(projectId);

    return AckDto.makeDefault(true);
  }

  @PutMapping(CREATE_OR_UPDATE_PROJECT)
  public ProjectDto createOrUpdateProject(
      @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
      @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName) {

    optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

    boolean isCreate = !optionalProjectId.isPresent();

    if (isCreate && !optionalProjectName.isPresent()) {
      throw new BadRequestException("Project name can't be empty.");
    }

    final ProjectEntity project = optionalProjectId
        .map(this::getProjectEntityOrThrowException)
        .orElseGet(() -> ProjectEntity.builder().build());

    optionalProjectName
        .ifPresent(projectName -> {

          projectRepository
              .findByName(projectName)
              .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
              .ifPresent(anotherProject -> {
                throw new BadRequestException(
                    String.format("Project \"%s\" already exists.", projectName)
                );
              });

          project.setName(projectName);
        });

    final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

    return projectDtoFactory.makeProjectDto(savedProject);
  }

  private ProjectEntity getProjectEntityOrThrowException(Long projectId) {
    return projectRepository
        .findById(projectId)
        .orElseThrow(() -> new NotFoundException("Project with id " + projectId + " not found"));
  }
}

