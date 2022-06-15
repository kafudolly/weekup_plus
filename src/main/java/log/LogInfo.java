package log;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;

import Middleware.FileTools;
import Middleware.Time.*;
import org.apache.commons.io.IOUtils;

public class LogInfo extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        File logFile = new File("weekup_plus/logFile.txt");
        resp.setHeader("content-disposition","attachment;filename="
                + URLEncoder.encode(logFile.getName(),"utf-8"));
        FileInputStream is = new FileInputStream(logFile);
        ServletOutputStream os = resp.getOutputStream();
        IOUtils.copy(is,os);
        is.close();
        os.close();
    }


    public static void addLogInfo(String logInfo) throws IOException {
        File logFile = new File("weekup_plus/logFile.txt");
        if(!logFile.exists())
            logFile.createNewFile();
        FileWriter fw = new FileWriter(logFile, true);
        String time = "";
        time += "week: " + TimeTools.currentWeek;
        time += " weekDay: " + TimeTools.currentWeekday;
        time += " time: " + TimeTools.currentTime/10000 + ":" + TimeTools.currentTime%10000/100 + ":" +TimeTools.currentTime%100;
        fw.write(time + " Info: " + logInfo + "\n");
        fw.close();
    }
}
