package com.mipt.lysaleksandr.hometask_2.mapper;

import com.mipt.lysaleksandr.hometask_2.dto.TaskCreateDto;
import com.mipt.lysaleksandr.hometask_2.dto.TaskUpdateDto;
import com.mipt.lysaleksandr.hometask_2.dto.TaskResponseDto;
import com.mipt.lysaleksandr.hometask_2.model.Task;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", constant = "false")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Task toEntity(TaskCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(TaskUpdateDto dto, @MappingTarget Task task);

    TaskResponseDto toResponseDto(Task task);
}