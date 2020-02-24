package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealDaoMemory implements MealDaoInterface {

    private static MealDaoMemory instance;
    private static Map<Long, Meal> mealsMap;
    private static long index = 7;

// TODO переделать логику счетчика, добавить подделку многопоточности
    private MealDaoMemory() {
        mealsMap = MealsUtil.meals.stream()
                .collect(Collectors.toMap(Meal::getId, meal -> meal));
    }

    public static MealDaoMemory getInstance() {
        if (instance == null) {
            instance = new MealDaoMemory();
        }
        return instance;
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        mealsMap.put(index+1, new Meal(index+1, dateTime, description, calories));
    }

    @Override
    public void delete(long id) {
        if (mealsMap.containsKey(id)) {
            mealsMap.remove(id);
        }
    }

    @Override
    public void update(long id, LocalDateTime dateTime, String description, int calories) {
        mealsMap.put(id, new Meal(id, dateTime, description, calories));
    }

    @Override
    public List<Meal> getAll() {
        return mealsMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Meal getById(long id) {
        return mealsMap.get(id);
    }
}