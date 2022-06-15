package course;

import log.LogInfo;
import log.Login;
import Middleware.StringTools;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class GetClassInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("GBK");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        String info = "{\"id\":\""+id+"\",";
        if (!id.equals("")) {
            String path = "weekup_plus/users/" + id;
            File classFile = new File(path + "/class");
            info = info + "\"class\":[";
            File[] classes = classFile.listFiles();
            for (File f : classes) {
                info += StringTools.FileToString(new File("weekup_plus/class/" + f.getName() + "/info.json")) + ",";
            }
            info = info.substring(0,info.length()-1);
            info += "]}";
        }
        resp.getWriter().write(info);
        LogInfo.addLogInfo("/class/get, success");
    }
}
