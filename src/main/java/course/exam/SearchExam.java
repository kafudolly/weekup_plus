package course.exam;

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
import java.util.ArrayList;
import java.util.List;

public class SearchExam extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("GBK");
        Gson gson = new Gson();
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String keyword = req.getParameter("keyWord");
        List<Exam> resultList = getExamList(id,keyword);
        String info = "{\"id\":\""+id+"\",\"exam\":"+gson.toJson(resultList)+"}";
        resp.getWriter().write(info);
        LogInfo.addLogInfo("/exam/get, success");
    }

    public static List<Exam> getExamList(String id, String keyword){
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id + "/class";
        Exam exam;
        List<Exam> resultList = new ArrayList<>();
        File activityFile = new File(path);
        File[] classes = activityFile.listFiles();
        if(classes != null) {
            for (File f : classes) {
                File infoFile = new File("weekup_plus/class/" + f.getName() + "/exam.json");
                if (infoFile.exists()) {
                    List<Exam> tempList = gson.fromJson(StringTools.FileToString(infoFile), new TypeToken<List<Exam>>() {
                    }.getType());
                    resultList.addAll(tempList);
                    tempList.clear();
                }
            }
        }
        return resultList;
    }
}
