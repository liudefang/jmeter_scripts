package com.pertest;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

// 1. 继承Jmeter内置的类：AbstractJavaSamplerClient
public class TestJmeterPayPasswdMd5 extends AbstractJavaSamplerClient {
    private String pay_passwd;

    // 2.此方法为参数化内容，可以体现在GUI模式中，如果返回为null，则不显示
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("pay_passwd","");     //参数化名字自定义

        return params;
    }

    // 3.执行初始化内容，每个线程执行一次，通常建议在该方法中获取参数值，而不是RunTest方法中使用，以便尽可能减少测试开销
    public void setupTest(JavaSamplerContext context){
        pay_passwd = context.getParameter("pay_passwd");

    }

    // 4.在测试运行结束时进行本次测试所需的清理工作，也是一个线程执行一次
    public void teardownTest(JavaSamplerContext context) {

    }
    // 5.每次执行的内容部分
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sr = new SampleResult();
        sr.setSampleLabel("JmeterPayPasswdTest");   //查看结果树中线程名称

        sr.sampleStart();
        try {
            // 调用md5类里面的getmd5code这个方法
            String newPasswod = MD5.GetMD5Code(pay_passwd);
            sr.setResponseData("newPasswod:"+newPasswod,null);
            sr.setDataType(SampleResult.TEXT);
            System.out.println("newPasswod:" +newPasswod);
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
