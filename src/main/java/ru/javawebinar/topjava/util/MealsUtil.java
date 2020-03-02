package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<MealTo> getFilteredWithExcess(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> daysCalories;
        daysCalories = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate,
                        Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime))
                .map(meal ->
                        new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                daysCalories.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
