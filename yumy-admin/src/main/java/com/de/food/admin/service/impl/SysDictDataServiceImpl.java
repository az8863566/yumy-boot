package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysDictDataConverter;
import com.de.food.admin.dto.SysDictDataCreateDTO;
import com.de.food.admin.dto.SysDictDataQueryDTO;
import com.de.food.admin.dto.SysDictDataUpdateDTO;
import com.de.food.admin.entity.SysDictData;
import com.de.food.admin.mapper.SysDictDataMapper;
import com.de.food.admin.service.SysDictDataService;
import com.de.food.admin.vo.SysDictDataVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 字典数据 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final SysDictDataConverter sysDictDataConverter;

    @Override
    public IPage<SysDictDataVO> page(SysDictDataQueryDTO queryDTO) {
        Page<SysDictData> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<SysDictData>()
                .eq(StringUtils.hasText(queryDTO.getDictType()), SysDictData::getDictType, queryDTO.getDictType())
                .like(StringUtils.hasText(queryDTO.getDictLabel()), SysDictData::getDictLabel, queryDTO.getDictLabel())
                .eq(queryDTO.getStatus() != null, SysDictData::getStatus, queryDTO.getStatus())
                .orderByAsc(SysDictData::getDictSort);

        IPage<SysDictData> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(sysDictDataConverter::toVO);
    }

    @Override
    public List<SysDictDataVO> listByDictType(String dictType) {
        List<SysDictData> list = baseMapper.selectList(new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getDictType, dictType)
                .eq(SysDictData::getStatus, 1)
                .orderByAsc(SysDictData::getDictSort));
        return list.stream().map(sysDictDataConverter::toVO).toList();
    }

    @Override
    public SysDictDataVO getDetail(Long dictCode) {
        SysDictData entity = baseMapper.selectById(dictCode);
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "字典数据不存在");
        }
        return sysDictDataConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDictData(SysDictDataCreateDTO dto) {
        SysDictData entity = sysDictDataConverter.toEntity(dto);
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictData(SysDictDataUpdateDTO dto) {
        SysDictData entity = baseMapper.selectById(dto.getDictCode());
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "字典数据不存在");
        }
        sysDictDataConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictData(Long dictCode) {
        baseMapper.deleteById(dictCode);
    }
}
