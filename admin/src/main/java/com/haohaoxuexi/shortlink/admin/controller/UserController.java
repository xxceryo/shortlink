package com.haohaoxuexi.shortlink.admin.controller;

import com.haohaoxuexi.shortlink.admin.common.convention.exception.ClientException;
import com.haohaoxuexi.shortlink.admin.common.convention.result.Result;
import com.haohaoxuexi.shortlink.admin.common.convention.result.Results;
import com.haohaoxuexi.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.haohaoxuexi.shortlink.admin.dto.resp.UserRespDTO;
import com.haohaoxuexi.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }
}
