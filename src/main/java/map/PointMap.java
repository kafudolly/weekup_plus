package map;

import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import log.Login;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

public class PointMap extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        File logFile = new File("weekup_plus/map.json");
        resp.setHeader("content-disposition","attachment;filename="
                + URLEncoder.encode(logFile.getName(),"utf-8"));
        FileInputStream is = new FileInputStream(logFile);
        ServletOutputStream os = resp.getOutputStream();
        IOUtils.copy(is,os);
        is.close();
        os.close();
    }
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.addHeader("fileName","map.json");
    }
}
