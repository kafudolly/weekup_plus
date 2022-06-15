package activity;

import Middleware.StringTools;
import com.google.gson.Gson;
import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("GBK");
        Gson gson = new Gson();
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String searchType = req.getParameter("searchType");
        String keyword = req.getParameter("keyWord");
        List<Activity> resultList = getActivityList(id,keyword, searchType);
        String info = "{\"id\":\""+id+"\",\"activity\":"+gson.toJson(resultList)+"}";
        resp.getWriter().write(info);
        LogInfo.addLogInfo("/activity/search, success");
    }

    public static List<Activity>  getActivityList(String id, String keyword, String type){
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id+"/activity";
        Activity activity;
        List<Activity> resultList = new ArrayList<>();
        File activityFile = new File(path);
        File[] classes = activityFile.listFiles();
        if(classes != null) {
            for (File f : classes) {
                activity = gson.fromJson(StringTools.FileToString(f), Activity.class);
                switch (type) {
                    case "1":
                        if (StringTools.matchString(activity.getActivityName(), keyword) || keyword.equals("*")) {
                            resultList.add(activity);
                        }
                        break;
                    case "2":
                        if (StringTools.matchString(activity.getActivityLoc(), keyword) || keyword.equals("*")) {
                            resultList.add(activity);
                        }
                        break;
                    case "3":
                        if (StringTools.matchString(activity.getActivityMan(), keyword) || keyword.equals("*")) {
                            resultList.add(activity);
                        }
                        break;
                    case "4":
                        int activityType = 0;
                        switch (keyword) {
                            case "集体活动":
                                activityType = 1;
                                break;
                            case "个人活动":
                                activityType = 2;
                                break;
                        }
                        if (activity.getActivityType() == activityType) {
                            resultList.add(activity);
                        }
                }
            }
        }
        return resultList;
    }
}
