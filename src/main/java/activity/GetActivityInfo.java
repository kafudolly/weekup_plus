package activity;

import com.google.gson.Gson;
import log.LogInfo;
import log.Login;
import Middleware.StringTools;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class GetActivityInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("GBK");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        Gson gson = new Gson();
        String info = "{\"id\":\""+id+"\",";
        if (!id.equals("")) {
            String path = "weekup_plus/users/" + id;
            File activityFile = new File(path + "/activity");
            info = info + "\"activity\":[";
            File[] activities = activityFile.listFiles();
            if(activities != null) {
                for (File f : activities) {
//                    Activity activity = new Activity();
//                    FileInputStream fileInputStream = new FileInputStream(f);
//                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//                    activity = (Activity) objectInputStream.readObject();
//                    info += gson.toJson(activity)+",";
                    info += StringTools.FileToString(f) + ",";
                }
                info = info.substring(0, info.length() - 1);
            }
        }
        info += "]}";
        resp.getWriter().write(info);
        LogInfo.addLogInfo("/activity/get, success");
    }
}
