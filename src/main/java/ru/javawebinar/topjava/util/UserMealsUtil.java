package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

       // List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        List<UserMealWithExcess> listUserMealWithExcess = new ArrayList<>();
        int allCalories = 0;
        boolean signExcess  = false;

        for (UserMeal userMeal:meals) {
            LocalTime mealLocalTime  = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenInclusive(mealLocalTime,startTime,endTime)) {
                allCalories = allCalories + userMeal.getCalories();
            }
        }

        /* Кака нужно
        Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        for (UserMeal userMeal:meals) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            caloriesSumByDate.put(mealDate,caloriesSumByDate.getOrDefault(mealDate,0) + userMeal.getCalories());
        }
*/
        if (allCalories>caloriesPerDay) {

            signExcess = true;
        }

        for (UserMeal userMeal:meals) {
            LocalTime mealLocalTime  = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenInclusive(mealLocalTime,startTime,endTime)){
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),signExcess);
                listUserMealWithExcess.add(userMealWithExcess);
              //  allCalories =+userMeal.getCalories();
            }

        }




        return listUserMealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        int allCalories = meals
                .stream()
                .filter((s)->TimeUtil.isBetweenInclusive(s.getDateTime().toLocalTime(),startTime,endTime))
                .mapToInt(UserMeal::getCalories).sum();

        boolean signExcess  = false;
        List<UserMealWithExcess> listUserMealWithExcess = null;

        if (allCalories>caloriesPerDay) {
           listUserMealWithExcess = meals
                    .stream()
                    .filter((s)->TimeUtil.isBetweenInclusive(s.getDateTime().toLocalTime(),startTime,endTime))
                    .map((s)->new UserMealWithExcess(s.getDateTime(),s.getDescription(),s.getCalories(),true))
                    .collect(Collectors.toList());
        }
        else {
            listUserMealWithExcess = meals
                    .stream()
                    .filter((s)->TimeUtil.isBetweenInclusive(s.getDateTime().toLocalTime(),startTime,endTime))
                    .map((s)->new UserMealWithExcess(s.getDateTime(),s.getDescription(),s.getCalories(),false))
                    .collect(Collectors.toList());
        }
/*
     Правильное решение

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(um -> um.getCalories())));
        listUserMealWithExcess = meals
                .stream()
                .filter((um)->TimeUtil.isBetweenInclusive(um.getDateTime().toLocalTime(),startTime,endTime))
                .map((um)->new UserMealWithExcess(um.getDateTime(),um.getDescription(),um.getCalories(),CaloriesSumByDate.get(um.getDateTime().toLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());

*/
        return listUserMealWithExcess;
    }
}
