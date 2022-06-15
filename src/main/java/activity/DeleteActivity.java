package activity;

import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteActivity extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("GBK");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String keyword = req.getParameter("keyWord");
        File activityFile = new File("weekup_plus/users/" + id + "/" + "activity/" + keyword + ".json");
        if(activityFile.exists()){
            activityFile.delete();
            resp.getWriter().write("success");
            LogInfo.addLogInfo("/activity/delete, success");
            return;
        }
        else{
            resp.getWriter().write("activity not exist");
        }
        LogInfo.addLogInfo("/activity/delete, failure");
    }

}
