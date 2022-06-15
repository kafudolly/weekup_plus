package course.data;

import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class getDataInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("GBK");
        Gson gson = new Gson();
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String classNumber = req.getParameter("classNumber");
        String uploadPath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        File classDataFile = new File(uploadPath + "/data.json");
        if(classDataFile.exists()){
            resp.getWriter().write("{\"id\":\""+ id +"\",\"dataName\":"+StringTools.FileToString(classDataFile) + "}");
            LogInfo.addLogInfo("/class/getData, success");
        }
        else{
            resp.getWriter().write("no data exists");
            LogInfo.addLogInfo("/class/getData, failure");
        }

    }
}
