package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryRepository implements MealRepository {

    private static final ConcurrentMap<Long, Meal> mealsMap;
    private static final AtomicInteger counter = new AtomicInteger(7);
    private static final List<Meal> meals = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    static {
        mealsMap = meals.stream()
                .collect(Collectors.toConcurrentMap(Meal::getId, meal -> meal));
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        long index = counter.incrementAndGet();
        mealsMap.put(index, new Meal(index, dateTime, description, calories));
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