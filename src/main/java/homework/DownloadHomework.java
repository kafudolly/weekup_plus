package homework;

import Middleware.FileTools;
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
import java.util.HashMap;

public class DownloadHomework extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        Gson gson = new Gson();
        String classNumber = req.getParameter("classNumber");
        String homeworkId = req.getParameter("homeworkId");
        String filePath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        String fileName = "";
        HashMap<String, String>  fileMap = new HashMap<>();
        File homeworkFile = new File(filePath + "/homeworkFile.json");
        if(homeworkFile.exists()){
            fileMap = gson.fromJson(StringTools.FileToString(homeworkFile), new TypeToken<HashMap>() {}.getType());
            fileName = fileMap.get(homeworkId);
        }
        if(!fileName.equals("")){
            FileTools.downloadFile(resp, new File(filePath+"/"+fileName));
            LogInfo.addLogInfo("/homework/download, success");
        }
    }

    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        String classNumber = req.getParameter("classNumber");
        String homeworkId = req.getParameter("homeworkId");
        String filePath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        HashMap<String, String>  fileMap = new HashMap<>();
        File homeworkFile = new File(filePath + "/homeworkFile.json");
        if(homeworkFile.exists()){
            Gson gson = new Gson();
            fileMap = gson.fromJson(StringTools.FileToString(homeworkFile), new TypeToken<HashMap>() {}.getType());
            String fileName = fileMap.get(homeworkId);
            resp.addHeader("fileName", fileName);
        }
    }

}
