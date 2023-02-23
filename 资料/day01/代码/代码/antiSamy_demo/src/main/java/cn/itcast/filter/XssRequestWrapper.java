package cn.itcast.filter;

import org.owasp.validator.html.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssRequestWrapper extends HttpServletRequestWrapper {
    //获得antisamy框架所需的决策文件路径
    private static String antisayPath = XssRequestWrapper.class.getClassLoader().getResource("antisamy-test.xml").getFile();
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    public static Policy policy = null;
    static {
        try {
            //过滤策略对象
            policy = Policy.getInstance(antisayPath);
        } catch (PolicyException e) {
            e.printStackTrace();
        }
    }

    //通过AntiSamy框架过滤字符
    public String cleanXss(String text){
        AntiSamy antiSamy = new AntiSamy();
        try {
            CleanResults cleanResults = antiSamy.scan(text, policy);
            text = cleanResults.getCleanHTML();
        } catch (ScanException e) {
            e.printStackTrace();
        } catch (PolicyException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if(parameterValues == null){
            return null;
        }

        int length = parameterValues.length;
        String[] newArray = new String[length];

        for(int i=0;i<length;i++){
            String parameterValue = parameterValues[i];//<script></script>
            parameterValue = cleanXss(parameterValue);
            newArray[i] = parameterValue;
        }
        return newArray;
    }
}
