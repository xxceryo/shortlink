package com.haohaoxuexi.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        // TODO
        // 当数据库不存在查询结果为空时此处会报错
        BeanUtils.copyProperties(userDO, result);
        return result;
    }
}
