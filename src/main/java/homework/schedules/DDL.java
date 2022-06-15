package homework.schedules;

import Middleware.StringTools;
import Middleware.Time.TimeTools;
import activity.Activity;
import com.google.gson.Gson;
import course.exam.Exam;
import homework.Homework;
import course.Class;

import java.io.File;

public class DDL {
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMan() {
        return man;
    }

    public String getLeft() {
        return left;
    }

    public String getType() {
        return type;
    }

    private String id;
    private String name;
    private String man;
    private String left;
    private String type;//(1 个人活动 2集体活动 3作业)

    public boolean equals(DDL ddl){
        return id.equals(ddl.id)
                && name.equals(ddl.name)
                && man.equals(ddl.man)
                && type.equals(ddl.type);
    }

    static public DDL HomeworkToDDL(Homework homework, String id){
        DDL ddl = new DDL();
        Gson gson = new Gson();
        String path = "weekup_plus/class/" + homework.getClassNumber() + "/info.json";
        File classFile = new File(path);
        Class aclass;
        aclass = gson.fromJson(StringTools.FileToString(classFile), Class.class);
        ddl.id = homework.getClassNumber();
        ddl.man = aclass.getClassTeacher();
        ddl.name = String.valueOf(homework.getId());
        ddl.left =  String.valueOf(7*24*60*(homework.getWeekEnd()- TimeTools.currentWeek)+24*60*(homework.getEndDay()-TimeTools.currentWeekday)+
                60*(homework.getEndTime()/100-TimeTools.currentTime/10000) + homework.getEndTime()%100-(TimeTools.currentTime%10000)/100);
        ddl.type = "3";
        return ddl;
    }

    static public DDL ActivityToDDL(Activity activity){
        DDL ddl = new DDL();
        ddl.id = activity.getActivityNumber();
        ddl.man = activity.getActivityMan();
        ddl.name = activity.getActivityName();
        ddl.left =  String.valueOf(7*24*60*(activity.getWeekEnd()- TimeTools.currentWeek)
                +24*60*(activity.getWeekData()-TimeTools.currentWeekday)
                + 60*(activity.getBeginTime()/100-TimeTools.currentTime/10000) + (activity.getBeginTime()%100) - (TimeTools.currentTime%10000)/100);
        ddl.type = "1";
        return ddl;
    }

    static public DDL ExamToDDL(String id, Exam exam){
        DDL ddl = new DDL();
        Gson gson = new Gson();
        String path = "weekup_plus/class/" + exam.getClassNumber() + "/info.json";
        File classFile = new File(path);
        Class aclass;
        aclass = gson.fromJson(StringTools.FileToString(classFile), Class.class);
        ddl.id = aclass.getClassNumber();
        ddl.man = aclass.getClassTeacher();
        ddl.name = exam.getName();
        ddl.left =  String.valueOf(7*24*60*(exam.getWeek()- TimeTools.currentWeek)+24*60*(exam.getDay()-TimeTools.currentWeekday)+
                60*(exam.getBeginTime()/100-TimeTools.currentTime/10000) + (exam.getBeginTime()%100) - (TimeTools.currentTime%10000)/100);
        ddl.type = "2";
        return ddl;
    }
}
