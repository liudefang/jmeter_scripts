package com.pertest;

import com.pertest.HttpClientRequest;
import com.pertest.MD5;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.codehaus.jettison.json.JSONObject;

// 1. 继承Jmeter内置的类：AbstractJavaSamplerClient
public class TestJmeterLogin extends AbstractJavaSamplerClient {
    private String mobile;
    private String password;
    private String IP;
    private String PORT;
    private String URL;

    // 2.此方法为参数化内容，可以体现在GUI模式中，如果返回为null，则不显示
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("mobile","");     //参数化名字自定义
        params.addArgument("password","");
        params.addArgument("IP","");
        params.addArgument("PORT","");
        params.addArgument("URL","");


        return params;
    }

    // 3.执行初始化内容，每个线程执行一次，通常建议在该方法中获取参数值，而不是RunTest方法中使用，以便尽可能减少测试开销
    public void setupTest(JavaSamplerContext context){
        mobile = context.getParameter("mobile");
        password = context.getParameter("password");
        IP = context.getParameter("IP");
        PORT = context.getParameter("PORT");
        URL = context.getParameter("URL");

    }

    // 4.在测试运行结束时进行本次测试所需的清理工作，也是一个线程执行一次
    public void teardownTest(JavaSamplerContext context) {

    }
    // 5.每次执行的内容部分
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sr = new SampleResult();
        sr.setSampleLabel("JmeterLoginTest");   //查看结果树中线程名称

        sr.sampleStart();
        try {
            // 调用md5类里面的getmd5code这个方法
            // 定义登录URL地址
            String login_url = "http://"+IP+":"+PORT+""+URL+"";
            // 定义请求的参数
            String login_data = "{\"mobile\":\""+mobile+"\",\"password\":\""+password+"\"}";
            // 调用HttpClientRequest类里面的sendpost方法
            String PostResult = HttpClientRequest.sendPost(login_url, login_data);
            System.out.println("登录返回:"+ PostResult);
            //JSONObject jsonobj = new JSONObject(PostResult);
           // int status = (int) jsonobj.getInt("code");
           // System.out.println("code：" +status);
            // 获取token值
           // String token = (String) jsonobj.getJSONObject("data").get("token");
            //System.out.println("token：" +token);
            sr.setSuccessful(true);
        } catch (Throwable e){
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            sr.sampleEnd();
        }

        return sr;


    }
}
