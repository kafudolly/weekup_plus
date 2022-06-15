package homework.schedules;

import activity.Activity;
import activity.SearchActivity;
import com.google.gson.Gson;
import course.exam.Exam;
import course.exam.SearchExam;
import homework.Homework;
import log.LogInfo;
import log.Login;
import log.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static homework.GetHomeworkInfo.*;

public class GetDDL extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setCharacterEncoding("GBK");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        Gson gson = new Gson();
        List<DDL> ddlList = getDDLList(id);
        String info = "{\"id\":\""+id+"\",\"DDL\":"+gson.toJson(ddlList)+"}";
        resp.getWriter().write(info);
        LogInfo.addLogInfo("/ddl/get, success");
    }

    static public void SortDDL(List<DDL> examList){
        for(int i = 0; i < examList.size() - 1; i ++){
            for(int j = 0; j < (examList.size()- i- 1); j ++){
                DDL ddl1 = examList.get(j);
                DDL ddl2 = examList.get(j + 1);
                if(Integer.parseInt(ddl1.getLeft()) > Integer.parseInt(ddl2.getLeft())){
                    examList.set(j, ddl2);
                    examList.set(j + 1, ddl1);
                }
            }
        }
    }

    static public List<DDL> getDDLList(String id) {
        List<DDL> ddlList = new ArrayList<>();
        DDL tempDDL;
        List<Homework> homeworkList;
        homeworkList = GetAllHomework(id);
        for (Homework homework : homeworkList) {
            if (homework.getType() == 0) {
                tempDDL = DDL.HomeworkToDDL(homework, id);
                int left = Integer.parseInt(tempDDL.getLeft());
                if (left > 0) {
                    tempDDL.setLeft(String.valueOf(left / 60));
                    ddlList.add(tempDDL);
                }
            }
        }
        List<Activity> activityList;
        activityList = SearchActivity.getActivityList(id, "*", "1");
        for (Activity activity : activityList) {
            tempDDL = DDL.ActivityToDDL(activity);
            int left = Integer.parseInt(tempDDL.getLeft());
            if (left > 0) {
                tempDDL.setLeft(String.valueOf(left / 60));
                ddlList.add(tempDDL);
            }
        }
        List<Exam> examList;
        examList = SearchExam.getExamList(id, "*");
        for (Exam exam : examList) {
            tempDDL = DDL.ExamToDDL(id, exam);
            int left = Integer.parseInt(tempDDL.getLeft());
            if (left > 0) {
                tempDDL.setLeft(String.valueOf(left / 60));
                ddlList.add(tempDDL);
            }
        }
        SortDDL(ddlList);
        return ddlList;
    }

    public static Map<String, List<DDL>> getMessageDDL() throws IOException {
        String dirPath = "weekup_plus/users/";
        File file = new File(dirPath);
        Map<String, List<DDL>> ddlList = new HashMap<>();
        File[] fileList = file.listFiles();
        for(File f : fileList) {
            if (f.isDirectory()) {
                String path = "weekup_plus/users/"+f.getName()+"/"+f.getName()+".prop";
                File loginFile = new File(path);
                FileInputStream loginFileStream = new FileInputStream(loginFile);
                ObjectInputStream loginObjectStream = new ObjectInputStream(loginFileStream);
                try {
                    User loginUser = (User) loginObjectStream.readObject();
                    ddlList.put(loginUser.getPhoneNum(), getDDLList(f.getName()));
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        List<DDL> tempList = new ArrayList<>();
        for(Map.Entry<String, List<DDL>> entry : ddlList.entrySet()) {
            for (DDL ddl : entry.getValue()) {
                if (Integer.parseInt(ddl.getLeft()) != 1) {
                    tempList.add(ddl);
                }
            }
            ddlList.get(entry.getKey()).removeAll(tempList);
            tempList.clear();
        }
        return ddlList;
    }
}
