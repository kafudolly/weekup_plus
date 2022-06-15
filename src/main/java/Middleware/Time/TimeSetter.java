package Middleware.Time;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static Middleware.Time.TimeTools.*;

public class TimeSetter extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        currentTime = Integer.parseInt(req.getParameter("currentTime"));
        currentWeek = Integer.parseInt(req.getParameter("currentWeek"));
        currentWeekday = Integer.parseInt(req.getParameter("currentWeekday"));
        TIME_COUNT = 10/Integer.parseInt(req.getParameter("acc"));
        resp.getWriter().write("success");
    }
}
