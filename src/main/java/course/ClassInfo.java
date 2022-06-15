package course;

import com.google.gson.Gson;
import log.LogInfo;
import log.Login;
import Middleware.StringTools;
import log.User;
import map.Point;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ClassInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin")) {
            Gson gson = new Gson();
            String info = req.getParameter("info");
            Class newClass = gson.fromJson(info, Class.class);
            newClass.setLocationId(Point.getPointNum(newClass.getClassLoc()));
            if(newClass.getLocationId() <= 0){
                resp.getWriter().write("location Illegal");
                return;
            }
            newClass.setClassNumber(StringTools.RandomNumber());
            String path = "weekup_plus/class/" + newClass.getClassNumber() + "/info.json";
            File classFile = new File(path);
            System.out.println(info);
            if (!classFile.exists()) {
                classFile.getParentFile().mkdirs();
                classFile.createNewFile();
                FileWriter fileWriter = new FileWriter(classFile);
                fileWriter.write(gson.toJson(newClass));
                fileWriter.close();
                User.addClass(newClass);
                System.out.println("successful");
                resp.getWriter().println("success");
                LogInfo.addLogInfo("/class/add, success");
                return;
            }
            else {
                resp.getWriter().write("already exist!");
            }
        } else {
            resp.getWriter().write("failure");
        }
        LogInfo.addLogInfo("/class/add, failure");
    }
}
