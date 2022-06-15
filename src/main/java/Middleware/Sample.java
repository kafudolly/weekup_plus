// This file is auto-generated, don't edit it. Thanks.
package Middleware;

import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.models.*;
import homework.schedules.DDL;

public class Sample {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void send(String phoneNum, DDL ddl) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = Sample.createClient("LTAI5tGYJrrLZ8U8btJqDpHo", "nUrlXDCOdtn0LqcTUXkpMCcxL6H5Dy");
        String string = String.valueOf(Integer.parseInt(ddl.getId().substring(10)));
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("16692120732")
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setTemplateParam("{\"code\":\"" + string + "\"}");
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}
