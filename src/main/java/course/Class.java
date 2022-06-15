package course;

import java.util.List;

public class Class implements java.io.Serializable{
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getWeekBegin() {
        return weekBegin;
    }

    public void setWeekBegin(int weekBegin) {
        this.weekBegin = weekBegin;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }

    public int getWeekData() {
        return weekData;
    }

    public void setWeekData(int weekData) {
        this.weekData = weekData;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getClassLoc() {
        return classLoc;
    }

    public void setClassLoc(String classLoc) {
        this.classLoc = classLoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClassBeginTime() {
        return classBeginTime;
    }

    public void setClassBeginTime(int classBeginTime) {
        this.classBeginTime = classBeginTime;
    }

    public int getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(int classEndTime) {
        this.classEndTime = classEndTime;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    private int locationId;
    private String className;
    private int classBeginTime;
    private int classEndTime;
    private int weekBegin;
    private int weekEnd;
    private int weekData;
    private String classTeacher;
    private String classLoc;
    private String status;
    private String classNumber;
    public List<String> studentsId;
}