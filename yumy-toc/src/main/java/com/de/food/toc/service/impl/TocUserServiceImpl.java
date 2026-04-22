package com.de.food.toc.service.impl;

import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.framework.util.SecurityUtils;
import com.de.food.toc.converter.TocUserConverter;
import com.de.food.toc.dto.TocUserUpdateDTO;
import com.de.food.business.entity.TocUser;
import com.de.food.business.mapper.TocUserMapper;
import com.de.food.toc.service.TocUserService;
import com.de.food.toc.vo.TocUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * C端用户 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocUserServiceImpl implements TocUserService {

    private final TocUserMapper tocUserMapper;
    private final TocUserConverter tocUserConverter;

    @Override
    public TocUserVO getCurrentUser() {
        Long userId = SecurityUtils.requireUserId();
        TocUser user = tocUserMapper.selectById(userId);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return tocUserConverter.toVO(user);
    }

    @Override
    public TocUserVO updateUser(TocUserUpdateDTO dto) {
        Long userId = SecurityUtils.requireUserId();
        TocUser user = tocUserMapper.selectById(userId);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        tocUserConverter.updateEntity(dto, user);
        tocUserMapper.updateById(user);
        return tocUserConverter.toVO(user);
    }
}
