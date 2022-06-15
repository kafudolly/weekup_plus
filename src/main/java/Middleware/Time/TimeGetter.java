package Middleware.Time;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static Middleware.Time.TimeTools.*;

public class TimeGetter extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        HashMap<String,Integer> time = new HashMap<>();
        time.put("currentTime", currentTime);
        time.put("currentWeekday", currentWeekday);
        time.put("currentWeek", currentWeek);
        time.put("acc", 10/TIME_COUNT);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(time));
//        String times = req.getParameter("times");
//        if(!times.equals("")){
//            TIME_COUNT = 10/Integer.parseInt(times);
//        }
    }

}
