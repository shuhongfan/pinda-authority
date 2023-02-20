package com.itheima.pinda.authority.biz.service.core;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.authority.dto.core.StationPageDTO;
import com.itheima.pinda.authority.entity.core.Station;
/**
 * 业务接口
 * 岗位
 */
public interface StationService extends IService<Station> {
    /**
     * 分页
     */
    IPage<Station> findStationPage(Page page, StationPageDTO data);
}
