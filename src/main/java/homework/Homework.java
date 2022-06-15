package homework;

public class Homework implements java.io.Serializable{
    private String classNumber;
    private int id;
    private int type;
    private int weekBegin;
    private int beginDay;
    private int beginTime;
    private int weekEnd;
    private int endDay;
    private String description;
    private int endTime;
    private int score = 0;


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWeekBegin() {
        return weekBegin;
    }

    public void setWeekBegin(int weekBegin) {
        this.weekBegin = weekBegin;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(int beginDay) {
        this.beginDay = beginDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
