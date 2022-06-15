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
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DeleteExam extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("GBK");
        String id = Login.authentication(req);
        if (!id.equals("admin")) {
            resp.getWriter().write("Permission denied!");
            return;
        }
        String classNumber = req.getParameter("classNumber");
        String examId = req.getParameter("id");
        Gson gson = new Gson();
        File examFile = new File("weekup_plus/class/" + classNumber + "/exam.json");
        String allExam = StringTools.FileToString(examFile);
        List<Exam> examList = gson.fromJson(allExam, new TypeToken<List<Exam>>(){}.getType());
        if(examList.size() >= Integer.parseInt(examId)){
            examList.remove(Integer.parseInt(examId) - 1 );  //
        }
        Exam.refreshId(examList);
        FileWriter fw = new FileWriter(examFile);
        fw.write(gson.toJson(examList));
        fw.close();
        resp.getWriter().write("success");
        LogInfo.addLogInfo("/exam/delete, success");
    }
}
