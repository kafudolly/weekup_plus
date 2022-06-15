package homework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Middleware.FileTools;
import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import log.LogInfo;
import log.Login;

@WebServlet("/UploadServlet")
public class UploadHomework extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        Gson gson = new Gson();
        String classNumber = req.getParameter("classNumber");
        String homeworkId = req.getParameter("id");
        String uploadPath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        HashMap<String, String>  fileMap = new HashMap<>();
        String fileName = FileTools.UploadFile(req, uploadPath);
        if(fileName==null){
            resp.getWriter().write("failure");
            return;
        }
        File homeworkFile = new File(uploadPath + "/homeworkFile.json");
        if(!homeworkFile.exists()){
            homeworkFile.createNewFile();
            fileMap.put(homeworkId, fileName);
        }
        else {
            fileMap = gson.fromJson(StringTools.FileToString(homeworkFile), new TypeToken<HashMap>() {}.getType());
            File file = new File(uploadPath + "/" +fileMap.get(homeworkId));
            if(file.exists()&& !file.getName().equals(fileName))
                file.delete();
            fileMap.put(homeworkId, fileName);
        }
        String info = gson.toJson(fileMap);
        FileWriter fileWriter =  new FileWriter(homeworkFile);
        fileWriter.write(info);
        fileWriter.close();
        resp.getWriter().write("success");
        LogInfo.addLogInfo("/homework/upload, success");
    }
}