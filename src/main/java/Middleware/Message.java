package Middleware;

import Middleware.Time.TimeTools;
import com.aliyun.credentials.models.Config;
import homework.schedules.DDL;
import homework.schedules.GetDDL;
import log.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Message {
    public static void sendMessage() throws Exception {
        Map<String,List<DDL>> currentDDL = GetDDL.getMessageDDL();
        for(Map.Entry<String, List<DDL>> entry : currentDDL.entrySet()) {
            if(TimeTools.ddlBuffer.containsKey(entry.getKey())) {
                for (DDL temp : entry.getValue()) {
                    if (!TimeTools.ddlBuffer.get(entry.getKey()).contains(temp)) {
                        TimeTools.ddlBuffer.get(entry.getKey()).add(temp);
                    }
                }
                List<DDL> temp = new ArrayList<>();
                for (DDL ddl : TimeTools.ddlBuffer.get(entry.getKey())) {
                    if (!currentDDL.get(entry.getKey()).contains(ddl)) {
                        //发送短信
                        Sample.send(entry.getKey(), ddl);
                        temp.add(ddl);
                    }
                }
                TimeTools.ddlBuffer.get(entry.getKey()).removeAll(temp);
                temp.clear();
            }
            else {
                TimeTools.ddlBuffer.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void test() throws Exception {
        sendMessage();
    }


}
