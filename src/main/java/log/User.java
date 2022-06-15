package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import course.Class;
import homework.Homework;

public class User implements java.io.Serializable {
    private String username;    //用户名
    private String password;    //密码
    private String id;          //用户id
    private String phoneNum;    //手机号码
//    private String authority;

//    public String getAuthority() {
//        return authority;
//    }
//
//    public void setAuthority(String authority) {
//        this.authority = authority;
//    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }

    public String getPasswordMD5(){
        StringBuilder result = new StringBuilder();
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] digest = md5.digest(password.getBytes());
                for (byte b : digest) {
                    result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        return result.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username + '\'' +
                ", password=" + password + '\'' +
                '}';
    }

    public static void addClass(Class aClass){
        for(String studentId : aClass.studentsId){
            File userFile = new File("weekup_plus/users/"+studentId);
            if(userFile.exists()) {
                String path = "weekup_plus/users/" + studentId + "/" + "class/" + aClass.getClassNumber();
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
    }

    public static void addHomework(Class aClass, Homework newHomework) throws IOException {
        for(String id : aClass.studentsId){
            if(!new File("weekup_plus/users/" + id ).exists()){
                continue;
            }
            File homeworkFile = new File("weekup_plus/users/" + id + "/" + "class/" + newHomework.getClassNumber() + "/homework.json");
            if(!homeworkFile.exists()){
                homeworkFile.createNewFile();
            }
            String allHomework = StringTools.FileToString(homeworkFile);
            List<Homework> homeworkList;
            Gson gson = new Gson();
            if(allHomework.length() != 0){
                homeworkList = gson.fromJson(allHomework, new TypeToken<List<Homework>>(){}.getType());
            }
            else{
                homeworkList = new ArrayList<>();
            }
            newHomework.setId(homeworkList.size() + 1);
            homeworkList.add(newHomework);
            FileWriter fileWriter = new FileWriter(homeworkFile);
            fileWriter.write(gson.toJson(homeworkList));
            fileWriter.close();
        }
    }

    // public static void main(String[] args){
    //     User user = new User();
    //     user.setPassword("password");
    //     System.out.println(user.passwordToMD5());;
    // }


}
