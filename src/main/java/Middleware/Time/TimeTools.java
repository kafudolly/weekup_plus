package Middleware.Time;

import Middleware.Sample;
import homework.schedules.DDL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;


public class TimeTools
extends HttpServlet
implements ServletContextListener {
    public static int TIME_COUNT = 10;
    public static int currentWeek = 0;
    public static int currentWeekday = 1;
    public static int currentTime = 0;
    public static int temp = 0;
    public static final int[] schoolBusSchedule = {6, 8, 11, 13, 16, 20};
    public static Map<String, List<DDL>> ddlBuffer = new HashMap<>();

    private Timer timer = null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true);
        timer.schedule(new MyTask(), 0, 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        timer.cancel();
    }


//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        HashMap<String,Integer> time = new HashMap<>();
//        time.put("currentTime", currentTime);
//        time.put("currentDay", currentWeekday);
//        time.put("currentWeek", currentWeek);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(time));
//        String times = req.getParameter("times");
//        if(!times.equals("")){
//            TIME_COUNT = 10/Integer.parseInt(times);
//        }
//    }
}

