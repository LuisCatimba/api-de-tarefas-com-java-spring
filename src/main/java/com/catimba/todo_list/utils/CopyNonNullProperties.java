package com.catimba.todo_list.utils;

import com.catimba.todo_list.task.TaskModel;

import java.lang.reflect.Field;

public class CopyNonNullProperties {
    public static TaskModel execute(TaskModel source, TaskModel target){
        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields){
            field.setAccessible(true);

            try{
                Object value = field.get(source);

                if(value != null){
                    field.set(target, value);
                }
            } catch(IllegalArgumentException | IllegalAccessException e){
                System.out.println(e.getMessage());
            }
        }

        return target;
    }
}
