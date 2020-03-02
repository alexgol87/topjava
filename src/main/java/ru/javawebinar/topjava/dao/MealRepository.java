package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    void add(LocalDateTime dateTime, String description, int calories);

    void delete(long id);

    void update(long id, LocalDateTime dateTime, String description, int calories);

    List<Meal> getAll();

    Meal getById(long id);

}
