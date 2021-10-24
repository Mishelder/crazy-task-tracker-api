package by.idf.crazy.task.tracker.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AckDto {

  Boolean answer;

  public static AckDto makeDefault(Boolean answer) {
    return AckDto.builder()
        .answer(answer)
        .build();
  }
}
