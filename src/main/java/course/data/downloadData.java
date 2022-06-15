package course.data;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class downloadData extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        Gson gson = new Gson();
        String dataNum = req.getParameter("dataNum");
        String classNumber = req.getParameter("classNumber");
        String filePath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        String fileName = "";
        List<addData.Data> fileMap;
        File dataFile = new File(filePath + "/data.json");
        if(dataFile.exists()){
            fileMap = gson.fromJson(StringTools.FileToString(dataFile), new TypeToken<List<addData.Data>>() {}.getType());
            fileName = fileMap.get(Integer.parseInt(dataNum)).name;
        }
        if(!fileName.equals("")){
            FileTools.downloadFile(resp, new File(filePath+"/"+fileName));
            LogInfo.addLogInfo("/class/dataDownload, success");
        }
    }

    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        String classNumber = req.getParameter("classNumber");
        String dataNum = req.getParameter("dataNum");
        String filePath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        List<String> fileMap;
        File dataFile = new File(filePath + "/homeworkFile.json");
        if(dataFile.exists()){
            Gson gson = new Gson();
            fileMap = gson.fromJson(StringTools.FileToString(dataFile), new TypeToken<List<String>>() {}.getType());
            String fileName = fileMap.get(Integer.parseInt(dataNum));
            resp.addHeader("fileName", fileName);
        }
    }
}
