package map.getLocation;

import Middleware.StringTools;
import com.google.gson.Gson;
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

public class NaviSearchClass extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String searchType = req.getParameter("searchType");
        String keyword = req.getParameter("keyWord");
        List resultClass;
        switch(searchType){
            case "1":
                resultClass = SearchFromName(id, keyword);
                break;
            case "2":
                resultClass = SearchFromTeacher(id, keyword);
                break;
            case "3":
                resultClass = SearchFromLoc(id, keyword);
                break;
            case "4":
                resultClass = SearchFromID(id, keyword);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + searchType);
        }
        String info = "{\"id\":\""+id+"\",\"class\":"+gson.toJson(resultClass)+"}";
        resp.getWriter().write(info);
        LogInfo.addLogInfo("/class/search, success");
    }

    private List<Class> SearchFromID(String id, String keyword) {
        List<Class> resultList = new ArrayList<>();
        Gson gson = new Gson();
        if(keyword.equals("*")){
            resultList = SearchFromLoc(id, keyword);
        }
        else {
            File classFile = new File("weekup_plus/class/" + keyword + "/info.json");
            Class resultClass = gson.fromJson(StringTools.FileToString(classFile), Class.class);
            resultList.add(resultClass);
        }
        return resultList;
    }

    private List<Class> SearchFromLoc(String id, String keyword) {
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id;
        Class aClass;
        List<Class> resultList = new ArrayList<>();
        File classFile = new File(path + "/class");
        File[] classes = classFile.listFiles();
        for(File f : classes){
            File infoFile = new File("weekup_plus/class/" + f.getName() + "/info.json");
            aClass = gson.fromJson(StringTools.FileToString(infoFile), Class.class);
            if(StringTools.matchString(aClass.getClassLoc(), keyword) || keyword.equals("*")){
                resultList.add(aClass);
            }
        }
        return resultList;
    }

    private List<Class> SearchFromTeacher(String id, String keyword) {
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id;
        Class aClass;
        List<Class> resultList = new ArrayList<>();
        File classFile = new File(path + "/class");
        File[] classes = classFile.listFiles();
        for(File f : classes){
            File infoFile = new File("weekup_plus/class/" + f.getName() + "/info.json");
            aClass = gson.fromJson(StringTools.FileToString(infoFile), Class.class);
            if (StringTools.matchString(aClass.getClassTeacher(), keyword) || keyword.equals("*")){
                resultList.add(aClass);
            }
        }
        return resultList;
    }

    private List<Class> SearchFromName(String id, String keyword) {
        Gson gson = new Gson();
        String path = "weekup_plus/users/" + id;
        Class aClass;
        List<Class> resultList = new ArrayList<>();
        File classFile = new File(path + "/class");
        File[] classes = classFile.listFiles();
        for(File f : classes){
            File infoFile = new File("weekup_plus/class/" + f.getName() + "/info.json");
            aClass = gson.fromJson(StringTools.FileToString(infoFile), Class.class);
            if (StringTools.matchString(aClass.getClassName(), keyword) || keyword.equals("*")){
                resultList.add(aClass);
            }
        }
        return resultList;
    }
}
