package course.data;

import Middleware.FileTools;
import Middleware.StringTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import log.LogInfo;
import log.Login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class addData extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("utf-8");
        String id = Login.authentication(req);
        if (id.equals("admin"))
            id = req.getParameter("id");
        Gson gson = new Gson();
        String classNumber = req.getParameter("classNumber");
        String uploadPath = "weekup_plus/users/" + id + "/" + "class/" + classNumber;
        List<Data> fileNameMap = new ArrayList<>();
        String fileName = FileTools.UploadFile(req, uploadPath);
        Data data = new Data();
        data.name = fileName;
        if (fileName == null) {
            resp.getWriter().write("failure");
            return;
        }
        File classDataFile = new File(uploadPath + "/data.json");
        if (!classDataFile.exists()) {
            classDataFile.createNewFile();
            data.id = "0";
            fileNameMap.add(data);
        } else {
            fileNameMap = gson.fromJson(StringTools.FileToString(classDataFile), new TypeToken<List<Data>>() {
            }.getType());
            if(!fileNameMap.contains(data)) {
                data.id = String.valueOf(fileNameMap.size());
                fileNameMap.add(data);
            }
        }
        String info = gson.toJson(fileNameMap);
        FileWriter fileWriter = new FileWriter(classDataFile);
        fileWriter.write(info);
        fileWriter.close();
        resp.getWriter().write("success");
        LogInfo.addLogInfo("/class/addData, success");
    }

    class Data implements Serializable {
        public String id;
        public String name;

        public boolean equals(Data data){
            return id.equals(data.id) && name.equals(data.name);
        }
    }
}