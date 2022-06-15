package activity;

import Middleware.StringTools;
import com.google.gson.Gson;
import log.LogInfo;
import log.Login;
import map.Point;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ActivityInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        if (!id.equals("")) {
            Gson gson = new Gson();
            String info = req.getParameter("info");
            Activity newActivity = gson.fromJson(info, Activity.class);
            newActivity.setLocationId(Point.getPointNum(newActivity.getActivityLoc()));
            if(newActivity.getLocationId() <= 0){
                resp.getWriter().write("location Illegal");
                return;
            }
            newActivity.setActivityNumber(StringTools.RandomNumber());
            String path = "weekup_plus/users/" + id + "/" + "activity/" + newActivity.getActivityNumber() + ".json";
            File activityFile = new File(path);
            System.out.println(req.getParameter("info"));
            if (!activityFile.exists()) {
                activityFile.getParentFile().mkdirs();
                activityFile.createNewFile();
                FileWriter fileWriter = new FileWriter(activityFile);
                fileWriter.write(gson.toJson(newActivity));
                fileWriter.close();
                System.out.println("successful");
                resp.getWriter().println("success");
                LogInfo.addLogInfo("/activity/add, success");
                return;
            }
            //类写入文件，文件名定义为活动名
            else {
                resp.getWriter().write("already exist!");
            }
        } else {
            resp.getWriter().write("failure");
        }
        LogInfo.addLogInfo("/activity/add, failure");
    }
}
