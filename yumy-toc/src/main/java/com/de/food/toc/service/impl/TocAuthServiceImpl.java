package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.common.entity.UserType;
import com.de.food.framework.util.JwtUtil;
import com.de.food.toc.converter.TocUserConverter;
import com.de.food.toc.dto.TocAuthLoginDTO;
import com.de.food.toc.dto.TocAuthRegisterDTO;
import com.de.food.business.entity.TocUser;
import com.de.food.business.mapper.TocUserMapper;
import com.de.food.toc.service.TocAuthService;
import com.de.food.toc.vo.TocAuthVO;
import com.de.food.toc.vo.TocUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * C端认证 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocAuthServiceImpl implements TocAuthService {

    private final TocUserMapper tocUserMapper;
    private final TocUserConverter tocUserConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public TocAuthVO register(TocAuthRegisterDTO dto) {
        // 校验用户名唯一
        TocUser exists = tocUserMapper.selectOne(new LambdaQueryWrapper<TocUser>()
                .eq(TocUser::getUsername, dto.getUsername()));
        if (exists != null) {
            throw new BizException(ErrorCode.TOC_USER_EXISTS);
        }

        TocUser entity = tocUserConverter.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        tocUserMapper.insert(entity);

        return buildAuthVO(entity);
    }

    @Override
    public TocAuthVO login(TocAuthLoginDTO dto) {
        TocUser user = tocUserMapper.selectOne(new LambdaQueryWrapper<TocUser>()
                .eq(TocUser::getUsername, dto.getUsername()));
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException(ErrorCode.USER_PASSWORD_ERROR);
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException(ErrorCode.USER_DISABLED);
        }

        return buildAuthVO(user);
    }

    private TocAuthVO buildAuthVO(TocUser user) {
        String token = jwtUtil.generateToken(
                user.getUserId(),
                user.getUsername(),
                user.getNickname() != null ? user.getNickname() : user.getUsername(),
                UserType.TOC_USER,
                List.of("toc_user")
        );

        TocAuthVO vo = new TocAuthVO();
        vo.setToken(token);
        vo.setUser(tocUserConverter.toVO(user));
        return vo;
    }
}
