package activity;

public class Activity implements java.io.Serializable{
    private String activityNumber;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    private int locationId;
    private String activityName;
    private int activityType;
    private String activityMan;
    private int weekBegin;
    private int weekEnd;
    private int weekData;
    private int beginTime;
    private int endTime;
    private String activityLoc;
    private String status;
    private String description;

    public String getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(String activityNumber) {
        this.activityNumber = activityNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getActivityMan() {
        return activityMan;
    }

    public void setActivityMan(String activityMan) {
        this.activityMan = activityMan;
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

    public String getActivityLoc() {
        return activityLoc;
    }

    public void setActivityLoc(String activityLoc) {
        this.activityLoc = activityLoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }



}
