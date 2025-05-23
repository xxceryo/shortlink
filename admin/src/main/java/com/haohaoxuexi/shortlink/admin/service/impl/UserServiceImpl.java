package com.haohaoxuexi.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaoxuexi.shortlink.admin.common.convention.exception.ClientException;
import com.haohaoxuexi.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.haohaoxuexi.shortlink.admin.dao.entity.UserDO;
import com.haohaoxuexi.shortlink.admin.dao.mapper.UserMapper;
import com.haohaoxuexi.shortlink.admin.dto.resp.UserRespDTO;
import com.haohaoxuexi.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        UserRespDTO result = new UserRespDTO();
        if (userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        BeanUtils.copyProperties(userDO, result);
        return result;
    }
}
