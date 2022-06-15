package course;

import Middleware.FileTools;
import Middleware.StringTools;
import com.google.gson.Gson;
import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeleteClass extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("GBK");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String keyword = req.getParameter("keyWord");
        Gson gson = new Gson();
        File file = new File("weekup_plus/class/" + keyword + "" +"/info.json");
        Class aClass = gson.fromJson(StringTools.FileToString(file), Class.class);
        if(aClass.studentsId.remove(id)){
            File userFile = new File("weekup_plus/users/" + id + "/" + "class/" + aClass.getClassNumber());
            FileTools.DirectoryDelete(userFile);
            resp.getWriter().write("success");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(aClass));
            fileWriter.close();
            LogInfo.addLogInfo("/class/delete, success");
            return;
        }
        else{
            resp.getWriter().write("activity not exist");
        }
        LogInfo.addLogInfo("/class/delete, failure");
    }

}
