package cn.gyw.backend.resource.houseinfo.service;

import cn.gyw.backend.resource.houseinfo.dao.mapper.HouseInfoMapper;
import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.components.web.base.mgb.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseInfoServiceImpl extends BaseService<HouseInfo> {

    @Autowired
    private HouseInfoMapper houseInfoMapper;
}
