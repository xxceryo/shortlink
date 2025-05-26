package com.haohaoxuexi.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaoxuexi.shortlink.admin.common.convention.exception.ClientException;
import com.haohaoxuexi.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.haohaoxuexi.shortlink.admin.dao.entity.UserDO;
import com.haohaoxuexi.shortlink.admin.dao.mapper.UserMapper;
import com.haohaoxuexi.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.haohaoxuexi.shortlink.admin.dto.resp.UserRespDTO;
import com.haohaoxuexi.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

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

    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (hasUsername(requestParam.getUsername())) {
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
        if (inserted != 1) {
            throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROE);
        }
        userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
    }
}
