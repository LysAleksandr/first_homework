package com.mipt.lysaleksandr.hometask_3.mapper;

import com.mipt.lysaleksandr.hometask_3.dto.TaskCreateDto;
import com.mipt.lysaleksandr.hometask_3.dto.TaskResponseDto;
import com.mipt.lysaleksandr.hometask_3.dto.TaskUpdateDto;
import com.mipt.lysaleksandr.hometask_3.model.Tag;
import com.mipt.lysaleksandr.hometask_3.model.Task;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", constant = "false")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "tags", source = "tags", qualifiedByName = "stringSetToTagSet")
    Task toEntity(TaskCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tags", source = "tags", qualifiedByName = "stringSetToTagSet")
    void updateEntity(TaskUpdateDto dto, @MappingTarget Task task);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagSetToStringSet")
    TaskResponseDto toResponseDto(Task task);

    @Named("stringSetToTagSet")
    default Set<Tag> stringSetToTagSet(Set<String> strings) {
        if (strings == null) {
            return null;
        }
        return strings.stream().map(Tag::new).collect(Collectors.toSet());
    }

    @Named("tagSetToStringSet")
    default Set<String> tagSetToStringSet(Set<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }
}