package homework;

import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import course.Class;
import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetHomeworkInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setCharacterEncoding("GBK");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        Gson gson = new Gson();
        if (!id.equals("")) {
            List<Homework> homeworkList = null;
            String Type = req.getParameter("searchType");
            String keyword = req.getParameter("keyWord");
            switch (Type){
                case "1":
                    homeworkList = GetAllHomework(id);
                    break;
                case "2":
                    if(keyword.equals("*"))
                        homeworkList = GetAllHomework(id);
                    else
                        homeworkList = GetClassHomework(id, keyword);
                    break;
                case "3":
                    homeworkList = GetFromName(id, keyword);
                    break;
            }
            String info = "{\"id\":\""+id+"\",\"homework\":"+gson.toJson(homeworkList)+"}";
            resp.getWriter().write(info);
            LogInfo.addLogInfo("/homework/get, success");
        }
    }

    public static List<Homework> GetClassHomework(String id, String keyword) {
        Gson gson = new Gson();
        File file = new File("weekup_plus/users/" + id + "/" + "class/" + keyword + "/homework.json");
        return gson.fromJson(StringTools.FileToString(file), new TypeToken<List<Homework>>(){}.getType());
    }

    public static List<Homework> GetAllHomework(String id) {
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id + "/class";
        List<Homework> resultList = new ArrayList<>();
        List<Homework> classList;
        File classFile = new File(path);
        File[] classes = classFile.listFiles();
        if(classes != null) {
            for (File f : classes) {
                File infoFile = new File(f.getAbsolutePath() + "/homework.json");
                if (infoFile.exists()) {
                    classList = gson.fromJson(StringTools.FileToString(infoFile), new TypeToken<List<Homework>>() {
                    }.getType());
                    resultList.addAll(classList);
                }
            }
        }
        return resultList;
    }

    public static List<Homework> GetFromName(String id, String keyword) {
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id;
        Class aClass;
        List<Homework> resultList = new ArrayList<>();
        File classFile = new File(path + "/class");
        File[] classList = classFile.listFiles();
        for(File f : classList){
            File infoFile = new File("weekup_plus/class/"+ f.getName() +"/info.json");
            aClass = gson.fromJson(StringTools.FileToString(infoFile), Class.class);
            if (StringTools.matchString(aClass.getClassName(), keyword) || keyword.equals("*")){
                File homeworkFile = new File(f.getAbsolutePath()+"/homework.json");
                if(homeworkFile.exists()) {
                    List<Homework> classHomework = gson.fromJson(StringTools.FileToString(homeworkFile), new TypeToken<List<Homework>>() {}.getType());
                    resultList.addAll(classHomework);
                }
            }
        }
        return resultList;
    }
}
