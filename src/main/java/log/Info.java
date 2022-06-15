package log;

import Middleware.Time.TimeTools;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Info extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("GBK");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        if(!id.equals("")){
            String path = "weekup_plus/users/"+id+"/"+id+".prop";
            File loginFile = new File(path);
            FileInputStream loginFileStream = new FileInputStream(loginFile);
            ObjectInputStream loginObjectStream = new ObjectInputStream(loginFileStream);
            try {
                User loginUser = (User) loginObjectStream.readObject();
                String userInfo = "{\"currentWeek\":" + TimeTools.currentWeek + ",\"username\":\""+loginUser.getUsername()+"\",\"id\":\""+loginUser.getId()+"\",\"phoneNum\":\""+loginUser.getPhoneNum()+"\"}";
                resp.getWriter().write(userInfo);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            resp.getWriter().println("failure");
        }
    }
}
