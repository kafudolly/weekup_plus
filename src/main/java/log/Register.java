package log;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Register extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("GBK");
        req.setCharacterEncoding("utf-8");  //设置编码
        User user = new User();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String id = req.getParameter("id");
        String phoneNum = req.getParameter("phoneNum");
//        user.setAuthority("user");
        user.setUsername(username);
        user.setPassword(password);     
        user.setId(id);
        user.setPhoneNum(phoneNum);    //写入用户信息
        String path;
//        path = this.getServletContext().getRealPath("/WEB-INF/classes/users/"+id+"/"+id+".prop");
        path = "weekup_plus/users/"+id+"/"+id+".prop";
        File userFile = new File(path);
        if(!userFile.exists()){
            userFile.getParentFile().mkdirs();
            userFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(userFile);
            System.out.println(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            fileOutputStream.close();
            System.out.println("successful");
            resp.getWriter().println("success");
            LogInfo.addLogInfo("/reg, success");
            return;
        }       //类写入文件，文件名定义为账号
        else{
            resp.getWriter().println("already register");
        }
        LogInfo.addLogInfo("/reg, failure");
    }
}
