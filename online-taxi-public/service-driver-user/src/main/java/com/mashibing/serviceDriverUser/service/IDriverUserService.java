package com.mashibing.serviceDriverUser.service;

import com.mashibing.dto.ResponseResult;
import com.mashibing.dto.DriverUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2024-09-22
 */
public interface IDriverUserService extends IService<DriverUser> {
    public ResponseResult insertDriverInfo(DriverUser driverUser);

    public ResponseResult updateDriverInfo(DriverUser driverUser);

}
