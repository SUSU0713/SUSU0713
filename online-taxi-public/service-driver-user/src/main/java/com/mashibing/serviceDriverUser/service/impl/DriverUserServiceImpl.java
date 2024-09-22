package com.mashibing.serviceDriverUser.service.impl;

import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.dto.ResponseResult;
import com.mashibing.dto.DriverUser;
import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import com.mashibing.serviceDriverUser.service.IDriverUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-09-22
 */
@Service
public class DriverUserServiceImpl extends ServiceImpl<DriverUserMapper, DriverUser> implements IDriverUserService {

    @Autowired
    DriverUserMapper driverUserMapper;

    @Override
    public ResponseResult insertDriverInfo(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        return driverUserMapper.insert(driverUser) != 0 ? ResponseResult.success() :
                ResponseResult.fail(CommonStatusEnum.DRIVER_INFO_INSERT_ERROR.getCode(), CommonStatusEnum.DRIVER_INFO_INSERT_ERROR.getValue());
    }

    @Override
    public ResponseResult updateDriverInfo(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }

}
