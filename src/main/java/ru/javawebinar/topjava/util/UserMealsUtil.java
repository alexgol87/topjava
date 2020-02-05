package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByLoops(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        System.out.println("Realization with cycles:");
        mealsTo.forEach(System.out::println);

        System.out.println("\nRealization with streams:");
        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

    }

    public static List<UserMealWithExcess> filteredByLoops(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> filteredListWithExcess = new ArrayList<>();
        Map<LocalDate, Integer> daysCalories = new HashMap<>();

        meals.forEach(userMeal -> daysCalories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum));

        meals.forEach(userMeal -> {
            if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredListWithExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), daysCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        });

        return filteredListWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> filteredListWithExcess;
        Map<LocalDate, Integer> daysCalories;

        daysCalories = meals.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        filteredListWithExcess = meals
                .stream()
                .filter(userMeal -> TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), daysCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());

        return filteredListWithExcess;
    }
}
