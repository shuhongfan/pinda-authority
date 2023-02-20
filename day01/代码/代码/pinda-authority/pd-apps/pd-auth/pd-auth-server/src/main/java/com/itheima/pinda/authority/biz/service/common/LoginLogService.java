package com.itheima.pinda.authority.biz.service.common;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.authority.entity.common.LoginLog;
import java.util.List;
import java.util.Map;
/**
 * 业务接口
 * 登录日志
 */
public interface LoginLogService extends IService<LoginLog> {
    /**
     * 记录登录日志
     * @param account     账号
     * @param ua          浏览器
     * @param ip          客户端IP
     * @param location    客户端地址
     * @param description 登陆描述消息
     * @return
     */
    LoginLog save(String account, String ua, String ip, String location, String description);

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount();

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Long findTodayIp();

    /**
     * 获取系统近十天来的访问记录
     *
     * @param account 账号
     * @return 系统近十天来的访问记录
     */
    List<Map<String, Object>> findLastTenDaysVisitCount(String account);

    /**
     * 按浏览器来统计数量
     *
     * @return
     */
    List<Map<String, Object>> findByBrowser();

    /**
     * 按造作系统内统计数量
     *
     * @return
     */
    List<Map<String, Object>> findByOperatingSystem();
}
