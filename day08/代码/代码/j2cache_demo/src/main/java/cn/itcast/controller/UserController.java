package cn.itcast.controller;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用j2catch提供的CacheChannel对象操作缓存数据
 */
@RestController
@RequestMapping("/cache")
public class UserController {
    private String key = "myKey";
    private String region="rx";
    @Autowired
    private CacheChannel cacheChannel;

    @GetMapping("/getInfos")
    public List<String> getInfos(){
        //从缓存中获取数据，需要指定区域region和key
        CacheObject cacheObject = cacheChannel.get(region, key);
        if(cacheObject.getValue() == null){
            //缓存中没有找到，查询数据库获得
            List<String> data = new ArrayList<String>();
            data.add("info1");
            data.add("info2");
            //放入缓存
            cacheChannel.set(region,key,data);
            return data;
        }
        return (List<String>) cacheObject.getValue();
    }

    //清理指定缓存
    @GetMapping("/evict")
    public String evict(){
        cacheChannel.evict(region,key);
        return "evict success";
    }

    //清理指定区域中的所有缓存
    @GetMapping("/clear")
    public String clear(){
        cacheChannel.clear(region);
        return "clear success";
    }

    //检查指定的缓存数据是否存在
    @GetMapping("/exists")
    public boolean exists(){
        boolean exists = cacheChannel.exists(region, key);
        return exists;
    }

    //检查指定的缓存数据是从哪一级缓存获取到的
    @GetMapping("/check")
    public String check(){
        int level = cacheChannel.check(region, key);
        return "level:" + level;
    }
}
