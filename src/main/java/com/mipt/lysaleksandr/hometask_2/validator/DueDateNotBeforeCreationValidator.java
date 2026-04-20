package com.mipt.lysaleksandr.hometask_2.validator;

import com.mipt.lysaleksandr.hometask_2.model.Task;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DueDateNotBeforeCreationValidator implements
    ConstraintValidator<DueDateNotBeforeCreation, Task> {

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext context) {
        if (task.getDueDate() == null || task.getCreatedAt() == null) {
            return true;
        }
        return !task.getDueDate().isBefore(task.getCreatedAt().toLocalDate());
    }
}