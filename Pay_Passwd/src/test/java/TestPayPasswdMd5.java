
import com.pertest.HttpClientRequest;
import com.pertest.MD5;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class TestPayPasswdMd5 {

    public static void main(String[] args) throws JSONException {
        // 定义登录URL地址
        String login_url = "http://192.168.226.128:8080/mobile/api/user/login";
        // 定义请求的参数
        String login_data = "{\"mobile\":\"15989576517\",\"password\":\"123456\"}";
        // 调用HttpClientRequest类里面的sendpost方法
        String PostResult = HttpClientRequest.sendPost(login_url, login_data);
        System.out.println("登录返回:"+ PostResult);
        JSONObject jsonobj = new JSONObject(PostResult);
        int status = (int) jsonobj.getInt("code");
        System.out.println("code：" +status);
        // 获取token值
        String token = (String) jsonobj.getJSONObject("data").get("token");
        System.out.println("token：" +token);

        String modifypaypwd_url = "http://192.168.226.128:8080/mobile/api/user/resetpaypwd";
        // 调用md5类里面的getmd5code这个方法
        String newPasswod = MD5.GetMD5Code("123456");
        System.out.println("newPasswod：" +newPasswod);
        String modifypaypwd_data = "{\"token\":\""+token+"\",\"password\":\""+newPasswod+"\"}";
        String PostMd5Result = HttpClientRequest.sendPost(modifypaypwd_url, modifypaypwd_data);
        System.out.println("PostMd5Result:" + PostMd5Result);

    }
}
