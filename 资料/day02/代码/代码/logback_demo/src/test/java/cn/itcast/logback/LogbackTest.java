package cn.itcast.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试logback日志组件的使用
 */
public class LogbackTest {
    //简单使用
    @Test
    public void test1(){
        //通过工厂对象获得一个logger日志记录器对象
        Logger logger = LoggerFactory.getLogger("cn.itcast.test");
        //当前logger对象的日志输出级别为debug，从root logger继承来的
        //使用trace级别记录日志
        logger.trace("trace...");
        logger.debug("debug...");
        logger.info("info...");
        logger.warn("warn...");
        logger.error("error...");
    }

    //打印日志内部状态
    @Test
    public void test2(){
        //通过工厂对象获得一个logger日志记录器对象
        Logger logger = LoggerFactory.getLogger("cn.itcast.test");
        //当前logger对象的日志输出级别为debug，从root logger继承来的
        //使用trace级别记录日志
        logger.trace("trace...");
        logger.debug("debug...");
        logger.info("info...");
        logger.warn("warn...");
        logger.error("error...");

        // 打印内部的状态
        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }

    /*
     * 日志输出级别：ERROR > WARN > INFO > DEBUG > TRACE
     * */

    //测试默认的日志输出级别
    @Test
    public void test3(){
        Logger logger = LoggerFactory.getLogger("cn.itcast.logback.HelloWorld");
        logger.error("error ...");
        logger.warn("warn ...");
        logger.info("info ...");
        logger.debug("debug ...");
        //因为默认的输出级别为debug，所以这一条日志不会输出
        logger.trace("trace ...");
    }

    //设置日志输出等级
    @Test
    public void test4(){
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("cn.itcast.logback.HelloWorld");
        logger.setLevel(Level.DEBUG);//设置当前日志记录器对象的日志输出级别

        logger.error("error ...");
        logger.warn("warn ...");
        logger.info("info ...");
        logger.debug("debug ...");
        //因为默认的输出级别为debug，所以这一条日志不会输出
        logger.trace("trace ...");
    }

    //测试Logger的继承
    @Test
    public void test5(){
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("cn.itcast");
        logger.setLevel(Level.INFO);//设置当前日志记录器对象的日志输出级别

        //当前记录器对象是上面记录器对象的子级，从父级继承输出等级---INFO
        Logger logger1 = LoggerFactory.getLogger("cn.itcast.test");
        logger1.error("error ...");
        logger1.warn("warn ...");
        logger1.info("info ...");
        logger1.debug("debug ...");
        logger1.trace("trace ...");
    }

    //Logger获取，根据同一个名称获得的logger都是同一个实例
    @Test
    public void test6(){
        Logger logger1 = LoggerFactory.getLogger("cn.itcast.test");
        Logger logger2 = LoggerFactory.getLogger("cn.itcast.test");
        System.out.println(logger1 == logger2);
    }

    //参数化日志
    @Test
    public void test7(){
        Logger logger = LoggerFactory.getLogger("cn.itcast");
        String value = "world";
        logger.info("参数值为:" + value);
        logger.debug("hello {}","world");
    }
}
