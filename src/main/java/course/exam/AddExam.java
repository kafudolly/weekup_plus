package course.exam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import log.LogInfo;
import log.Login;
import Middleware.StringTools;
import map.Point;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddExam extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (!id.equals("admin")){
            resp.getWriter().write("Permission denied");
        }
        {
            Gson gson = new Gson();
            String info = req.getParameter("info");
            Exam newExam = gson.fromJson(info, Exam.class);
            newExam.setLocationId(Point.getPointNum(newExam.getLocation()));
            if(newExam.getLocationId() <= 0){
                resp.getWriter().write("location Illegal");
                return;
            }
            File examFile = new File("weekup_plus/class/" + newExam.getClassNumber() + "/exam.json");
            if(!examFile.exists()){
                examFile.createNewFile();
            }
            String allExam = StringTools.FileToString(examFile);
            List<Exam> examList;
            if(allExam.length() != 0){
                examList = gson.fromJson(allExam, new TypeToken<List<Exam>>(){}.getType());
            }
            else{
                examList = new ArrayList<>();
            }
            newExam.setId(examList.size() + 1);
            examList.add(newExam);
            FileWriter fileWriter = new FileWriter(examFile);
            fileWriter.write(gson.toJson(examList));
            fileWriter.close();
            resp.getWriter().write("success");
            LogInfo.addLogInfo("/exam/add, success");
        }
    }
}
