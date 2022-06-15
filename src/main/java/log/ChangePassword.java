package log;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ChangePassword extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        if(!id.equals("")){
            try {
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                File file = new File("weekup_plus/users/"+id+"/"+id+".prop");
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                User user = (User) objectInputStream.readObject();
                if(user.getPassword().equals(oldPassword)){
                    user.setPassword(newPassword);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(user);
                    resp.getWriter().write("success");
                }
                else {
                    resp.getWriter().write("fail");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
