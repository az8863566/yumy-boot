package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysDictTypeConverter;
import com.de.food.admin.dto.SysDictTypeCreateDTO;
import com.de.food.admin.dto.SysDictTypeQueryDTO;
import com.de.food.admin.dto.SysDictTypeUpdateDTO;
import com.de.food.admin.entity.SysDictType;
import com.de.food.admin.mapper.SysDictTypeMapper;
import com.de.food.admin.service.SysDictTypeService;
import com.de.food.admin.vo.SysDictTypeVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 字典类型 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictTypeConverter sysDictTypeConverter;

    @Override
    public IPage<SysDictTypeVO> page(SysDictTypeQueryDTO queryDTO) {
        Page<SysDictType> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<SysDictType>()
                .like(StringUtils.hasText(queryDTO.getDictName()), SysDictType::getDictName, queryDTO.getDictName())
                .like(StringUtils.hasText(queryDTO.getDictType()), SysDictType::getDictType, queryDTO.getDictType())
                .eq(queryDTO.getStatus() != null, SysDictType::getStatus, queryDTO.getStatus())
                .orderByDesc(SysDictType::getCreateTime);

        IPage<SysDictType> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(sysDictTypeConverter::toVO);
    }

    @Override
    public SysDictTypeVO getDetail(Long dictId) {
        SysDictType entity = baseMapper.selectById(dictId);
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "字典类型不存在");
        }
        return sysDictTypeConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDictType(SysDictTypeCreateDTO dto) {
        SysDictType entity = sysDictTypeConverter.toEntity(dto);
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(SysDictTypeUpdateDTO dto) {
        SysDictType entity = baseMapper.selectById(dto.getDictId());
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "字典类型不存在");
        }
        sysDictTypeConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(Long dictId) {
        baseMapper.deleteById(dictId);
    }
}
