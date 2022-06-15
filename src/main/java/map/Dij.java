package map;

import Middleware.StringTools;
import Middleware.Time.TimeTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dij extends HttpServlet {
    //步行速度，90m/min
    final Double WALK_SPEED = 90.0;
    //自行车速度，250m/min
    final Double BIKE_SPEED = 250.0;
    final Double INFINITY = 650536.0;
    HashMap<Integer, Point> map;
    public double[] timeLength;
    int[] flag;
    int[] parent;

    public Dij (){
        Gson gson = new Gson();
        map = gson.fromJson(StringTools.FileToString(new File("weekup_plus/u-map.json")),
                                                     new TypeToken<HashMap<Integer, Point>>(){}.getType());
        timeLength = new double[300];
        flag = new int[300];
        parent = new int[300];
        for(int i = 0; i < 300; i ++){
            timeLength[i] = INFINITY;
            flag[i] = 0;
            parent[i] = 0;
        }
    }

    //寻路算法，type为寻路方式，1代表最短距离，2代表最短时间，3代表交通工具最短时间。
    public List<Integer> dijkstra(int startPointId, int endPointId, int type){
        Point currentPoint = map.get(startPointId);
        timeLength[startPointId] = 0;
        flag[startPointId] = 1;
        double currentTime = 0.0, newTime = 0.0;
        while(flag[endPointId] != 1){
            for(Map.Entry<Integer, Edge> entry : currentPoint.neighborPoint.entrySet()){
                switch (type){
                    case 1:
                        newTime = timeLength[currentPoint.id] + entry.getValue().length / WALK_SPEED;
                        currentTime = timeLength[entry.getKey()];
                        break;
                    case 2:
                        newTime = timeLength[currentPoint.id] + entry.getValue().length / entry.getValue().crowding / WALK_SPEED;
                        currentTime = timeLength[entry.getKey()];
                        break;
                    case 3:
                        if (entry.getValue().bikePermission){
                            newTime = timeLength[currentPoint.id] + entry.getValue().length / entry.getValue().crowding / BIKE_SPEED;
                            currentTime = timeLength[entry.getKey()];
                        }
                        else {
                            newTime = timeLength[currentPoint.id] + entry.getValue().length / entry.getValue().crowding / WALK_SPEED;
                            currentTime = timeLength[entry.getKey()];
                        }

                }
                if(newTime < currentTime){
                    timeLength[entry.getKey()] = newTime;
                    parent[entry.getKey()] = currentPoint.id;
                }
            }
            int newId = 0;
            double minLength = INFINITY;
            for(int j = 1; j < timeLength.length; j ++){
                if(minLength > timeLength[j] && flag[j] == 0){
                    newId = j;
                    minLength = timeLength[j];
                }
            }
            flag[newId] = 1;
            currentPoint = map.get(newId);
        }
        List<Integer> resultPath = new ArrayList<>();
        int temp = endPointId;
        while(temp != startPointId){
            resultPath.add(temp);
            temp = parent[temp];
        }
        resultPath.add(startPointId);
        return resultPath;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Dij dij = new Dij();
        Gson gson = new Gson();
        int startPointId = Integer.parseInt(req.getParameter("startPoint"));
        int endPointId = Integer.parseInt(req.getParameter("endPoint"));
        int naviType = Integer.parseInt(req.getParameter("naviType"));
        int currentTime = (TimeTools.currentTime%10000)/100 + 60*(TimeTools.currentTime/10000);
        List<Integer> resultRoad= new ArrayList<>();
        Point startPoint = dij.map.get(startPointId);
        Point endPoint = dij.map.get(endPointId);
        if(startPoint.location.equals(endPoint.location)){
            resultRoad.addAll(dij.dijkstra(endPointId, startPointId, naviType));
            currentTime = (int)dij.timeLength[startPointId];
            resp.getWriter().write("{\"time\":\""+currentTime+"\",\"road\":" + gson.toJson(resultRoad)+"}");
            return;
        }
        else if(startPoint.location.equals("shahe") && endPoint.location.equals("xitucheng")){
            resultRoad.addAll(dij.dijkstra(1, startPointId, naviType));
            Trans trans = new Trans(currentTime + (int)dij.timeLength[startPointId]);
            resultRoad.addAll(dij.dijkstra(endPointId, 151, naviType));
            currentTime = trans.timeLength + (int)dij.timeLength[startPointId] + (int)dij.timeLength[151];
            resp.getWriter().write("{\"trans\":\""+ trans.trans + "\",\"time\":\""+currentTime+"\",\"road\":" + gson.toJson(resultRoad)+"}");
            return;
        }
        else if(startPoint.location.equals("xitucheng") && endPoint.location.equals("shahe")){
            resultRoad.addAll(dij.dijkstra(151, startPointId, naviType));
            Trans trans = new Trans(currentTime + (int)dij.timeLength[startPointId]);
            resultRoad.addAll(dij.dijkstra(endPointId, 1, naviType));
            currentTime = trans.timeLength + (int)dij.timeLength[startPointId] + (int)dij.timeLength[1];
            resp.getWriter().write("{\"trans\":\""+ trans.trans + "\",\"time\":\""+currentTime+"\",\"road\":" + gson.toJson(resultRoad)+"}");
            return;
        }
        resp.getWriter().write("System Error");


    }

    class Trans {
        int trans;
        int timeLength;

        Trans(int currentTime){
            int schoolBusTime = 65536;
            int busTime = 65536;
            for(int i = 0; i < TimeTools.schoolBusSchedule.length; i ++){
                if(currentTime <= TimeTools.schoolBusSchedule[i]*60 ){
                    schoolBusTime = TimeTools.schoolBusSchedule[i]*60 - currentTime + 40;
                    break;
                }
            }
            busTime = 60 - currentTime%60 + 90;
            if(schoolBusTime <= currentTime){
                trans = 1;
                timeLength = schoolBusTime;
            }
            else {
                trans = 2;
                timeLength = busTime;
            }
        }
    }




}
