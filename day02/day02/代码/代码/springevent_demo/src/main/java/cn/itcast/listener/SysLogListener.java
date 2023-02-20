package cn.itcast.listener;

import cn.itcast.dto.OptLogDTO;
import cn.itcast.event.SysLogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/**
 * 事件监听器，监听日志事件
 */
@Component
public class SysLogListener {
    //@Async//异步处理
    @EventListener(SysLogEvent.class)
    public void saveLog(SysLogEvent event){
        OptLogDTO optLogDTO = (OptLogDTO) event.getSource();
        long id = Thread.currentThread().getId();
        System.out.println("监听到日志操作事件：" + optLogDTO + " 线程id：" + id);
        //将日志信息保存到数据库...
    }
}