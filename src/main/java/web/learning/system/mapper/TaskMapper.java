package web.learning.system.mapper;

import web.learning.system.domain.Task;
import web.learning.system.dto.TaskDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    public static TaskDto toDto(Task task) {

        LocalDateTime date = task.getDate();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        String newFormatDate = day + "/" +  month + "/" + year;
        return new TaskDto(task.getId(), task.getTitle(),
                task.getDescription(), task.getMap(), newFormatDate,
                task.getIsPublished(), UserMapper.toDto(task.getAuthor()));
    }

    public static List<TaskDto> toDtoList(List<Task> tasks) {
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (Task task: tasks) {
            taskDtoList.add(toDto(task));
        }
        return taskDtoList;
    }
}
