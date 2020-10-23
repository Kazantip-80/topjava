package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        List<Meal> meals = MealsUtil.meals;
        int caloriesPerDay = MealsUtil.DEFAULT_CALORIES_PER_DAY;

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        List<MealTo> mealsTo =  meals.stream()
                .map(meal -> new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());


        request.setAttribute("mealsList",mealsTo);
       request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
