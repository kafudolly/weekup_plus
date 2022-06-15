package homework;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import course.Class;
import log.LogInfo;
import log.Login;
import Middleware.StringTools;
import log.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddHomework extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin")) {
            Gson gson = new Gson();
            String info = req.getParameter("info");
            Homework newHomework = gson.fromJson(info, Homework.class);
            Class aClass = gson.fromJson(
                    StringTools.FileToString(
                    new File("weekup_plus/class/" + newHomework.getClassNumber() + "/info.json")),
                    Class.class);
            User.addHomework(aClass, newHomework);
            resp.getWriter().write("success");
            LogInfo.addLogInfo("/homework/add, success");
            return;
        }
        else{
            resp.getWriter().write("Permission denied");
        }
        LogInfo.addLogInfo("/homework/add, failure");
    }
}
