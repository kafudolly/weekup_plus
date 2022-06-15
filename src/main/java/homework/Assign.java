package homework;

import com.google.gson.Gson;
import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Assign extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        List<Homework> homeworkList = null;
        Gson gson = new Gson();
        String id = Login.authentication(req);
        String classNumber = req.getParameter("classNumber");
        if (id.equals("admin"))
            id = req.getParameter("id");
        String homeworkId = req.getParameter("homeworkId");
        String score = req.getParameter("score");
        homeworkList = GetHomeworkInfo.GetClassHomework(id, classNumber);
        homeworkList.get(Integer.parseInt(homeworkId)).setScore(Integer.parseInt(score));
        File homeworkFile = new File("weekup_plus/users/" + id + "/" + "class/" + classNumber + "/homework.json");
        FileWriter fileWriter = new FileWriter(homeworkFile);
        fileWriter.write(gson.toJson(homeworkList));
        fileWriter.close();
        resp.getWriter().write("success");
        LogInfo.addLogInfo("/homework/assign, success");
    }

}
