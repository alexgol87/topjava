package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/mealForm.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static final InMemoryMealRepository repository = new InMemoryMealRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = LIST_MEAL;
        String action = request.getParameter("action");
        request.setAttribute("mealToList", MealsUtil.getFilteredWithExcess(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));

        if (action != null) {
            if (action.equalsIgnoreCase("delete") && request.getParameter("id") != null) {
                long id = Long.parseLong(request.getParameter("id"));
                repository.delete(id);
                log.debug("DELETE meal id={}", id);
                log.debug("redirect to meals");
                response.sendRedirect("meals");
            } else if (action.equalsIgnoreCase("edit") && request.getParameter("id") != null) {
                forward = INSERT_OR_EDIT;
                long id = Long.parseLong(request.getParameter("id"));
                Meal meal = repository.getById(id);
                request.setAttribute("meal", meal);
                log.debug("forward to EDIT");
            } else if (action.equalsIgnoreCase("add")) {
                forward = INSERT_OR_EDIT;
                log.debug("forward to INSERT");
            }
        } else {
            log.debug("forward to meals");
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        if (!response.isCommitted()) view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            repository.add(LocalDateTime.parse(request.getParameter("dateTime"), formatter), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        } else {
            repository.update(Long.parseLong(request.getParameter("id")), LocalDateTime.parse(request.getParameter("dateTime"), formatter), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        }

        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("mealToList", MealsUtil.getFilteredWithExcess(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}
