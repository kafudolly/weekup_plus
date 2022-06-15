package log;

import Middleware.Message;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class Login extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        //test
//        try {
//            Message.test();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


        resp.setHeader("Access-Control-Allow-Origin", "*");
        String id = authentication(req);
        if(!id.equals("")){
            resp.getWriter().println("success");
            LogInfo.addLogInfo("/login");
        }
        else{
            doPost(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("GBK");
        req.setCharacterEncoding("utf-8");      //定义编码
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String path;
        PrintWriter respwWriter = resp.getWriter();
        if(id.equals("admin") && password.equals("admin")){
            Cookie idCookie = new Cookie("id", id);
            Cookie pwCookie = new Cookie("password", password);
            Cookie authority = new Cookie("authority", "1");
            idCookie.setMaxAge(60*60);
            pwCookie.setMaxAge(60*60);
            authority.setMaxAge(60*60);
            resp.addCookie(idCookie);
            resp.addCookie(pwCookie);
            resp.addCookie(authority);
            resp.getWriter().write("admin");
            LogInfo.addLogInfo("/login, admin");
            return;
        }
//        path = this.getServletContext().getRealPath("/WEB-INF/classes/users/"+id+"/"+id+".prop");
        path = "weekup_plus/users/"+id+"/"+id+".prop";
        // System.out.println(path);
        File userFile = new File(path);
        //登录信息获取
        if(userFile.exists()){
            try {
                FileInputStream userFileStream = new FileInputStream(userFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(userFileStream);
                User user;
                user = (User) objectInputStream.readObject();
                userFileStream.close();
                if(password.equals(user.getPassword())){
                    Cookie idCookie = new Cookie("id", id);
                    Cookie pwCookie = new Cookie("password", user.getPasswordMD5());
                    Cookie authority = new Cookie("authority", "0");
                    idCookie.setMaxAge(60*60);
                    pwCookie.setMaxAge(60*60);
                    authority.setMaxAge(60*60);
                    resp.addCookie(idCookie);
                    resp.addCookie(pwCookie);
                    resp.addCookie(authority);
                    respwWriter.println("success");
                    System.out.println("id:"+id+" login successfully");
                    LogInfo.addLogInfo("/login");
                }
                else{
                    resp.getWriter().println("failure");
                }
                //登录实现
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("failure");
            resp.getWriter().println("failure");
        }
    }

    public static String authentication(HttpServletRequest req) throws IOException{
        Cookie[] cookies = req.getCookies();
        String id = "";
        String password = "";
        boolean test = false;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().compareTo("id") == 0){
                    id = cookie.getValue();
                }
                else if(cookie.getName().compareTo("password") == 0) {
                    password = cookie.getValue();
                }
            }
        }
        if(id.equals("admin") && password.equals("admin")){
            return id;
        }
//        String path = context.getRealPath("/WEB-INF/classes/users/"+id+"/"+id+".prop");
        String path = "weekup_plus/users/"+id+"/"+id+".prop";
        File loginFile = new File(path);
        if(loginFile.exists()){
            FileInputStream loginFileStream = new FileInputStream(loginFile);
            ObjectInputStream loginObjectStream = new ObjectInputStream(loginFileStream);
            try {
                User loginUser = (User) loginObjectStream.readObject();
                loginFileStream.close();
                if(password.equals(loginUser.getPasswordMD5())){
                    test = true;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(test){
            return id;
        }
        else{
            return "";
        }
    }
}
