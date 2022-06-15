package map;

import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Point extends HttpServlet {
    public int id;
    public String name;
    public double lng;
    public double lat;
    public String location;
    public HashMap<Integer, Edge> neighborPoint;

    Point(){
        id = 0;
        name = "";
        lng = 0.0;
        lat = 0.0;
        location = "";
        neighborPoint = new HashMap<>();
    }


    public static double calcDistance(Point point1, Point point2){
        double radLat1 = (point1.lat * Math.PI) / 180.0;
        double radLat2 = (point2.lat * Math.PI) / 180.0;
        double a = radLat1 - radLat2;
        double b = (point1.lng * Math.PI) / 180.0 - (point2.lng * Math.PI) / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137 * 1000; // EARTH_RADIUS;
        return s;
    }

    public static HashMap<Integer, Point> getPointList() {
        Gson gson = new Gson();
        File pointFile = new File("weekup_plus/points.json");
        List<Point> pointList = gson.fromJson(StringTools.FileToString(pointFile), new TypeToken<List<Point>>(){}.getType());
        HashMap<Integer, Point> pointMap = new HashMap<>();
        for(Point point : pointList){
            pointMap.put(point.id, point);
        }
        return pointMap;
    }

    public static int getPointNum(String location){
        int temp = 0;
        HashMap<Integer, Point> pointMap = getPointList();
        for(Map.Entry<Integer, Point> entry : pointMap.entrySet()){
            String locationString;
            if(entry.getValue().location.equals("shahe")){
                locationString = "沙河校区" + entry.getValue().name;
            }
            else{
                locationString = "西土城校区" + entry.getValue().name;
            }
            if(StringTools.matchString(location, locationString) && temp == 0){
                temp = entry.getKey();
            }
        }
        return temp;
    }

    static public void main(String[] args) throws IOException {
        HashMap<Integer, Point> pointMap = getPointList();
        Gson gson = new Gson();
        int a = 0, b = 0, bike = 0;
        double crowding = 0;
        boolean test = true;
        Scanner scanner = new Scanner(System.in);
        while (test){
            a = Integer.parseInt(scanner.next());
            b = Integer.parseInt(scanner.next());
            bike = Integer.parseInt(scanner.next());
            crowding = Double.parseDouble(scanner.next());
            if(a == -1 || b == -1){
                test = false;
            }
            else{
                Edge edge = new Edge();
                edge.length = calcDistance(pointMap.get(a), pointMap.get(b));
                edge.bikePermission = bike == 1;
                edge.crowding = crowding;
                pointMap.get(a).neighborPoint.put(b, edge);
                pointMap.get(b).neighborPoint.put(a, edge);
                System.out.println("添加节点"+a+"到节点"+b+"的边");
            }
        }
        File file = new File("D:/Study/DataStruct/test/map.json");
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        fw.write(gson.toJson(pointMap));
        fw.close();
    }
}
