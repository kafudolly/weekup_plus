package log;

import Middleware.Time.TimeTools;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class admin extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setCharacterEncoding("GBK");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if(id.equals("")){
            return;
        }
        if(id.equals("admin")) {
            Gson gson = new Gson();
            resp.setHeader("Access-Control-Allow-Origin", "*");
            List<User> userList = new ArrayList<>();
            String dirPath = "weekup_plus/users/";
            File file = new File(dirPath);
            File[] fileList = file.listFiles();
            for(File f : fileList){
                if(f.isDirectory()){
                    String userId = f.getName();
                    String path = "weekup_plus/users/"+userId+"/"+userId+".prop";
                    File loginFile = new File(path);
                    FileInputStream loginFileStream = new FileInputStream(loginFile);
                    ObjectInputStream loginObjectStream = new ObjectInputStream(loginFileStream);
                    try {
                        User loginUser = (User) loginObjectStream.readObject();
                        userList.add(loginUser);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            resp.getWriter().write("{\"id\":\"admin\",\"users\":"+ gson.toJson(userList) + "}");
        }
        else{
            resp.getWriter().write("Permission denied");
        }
    }


}
