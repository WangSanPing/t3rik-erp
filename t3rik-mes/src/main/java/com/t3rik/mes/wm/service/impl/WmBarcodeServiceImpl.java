package com.t3rik.mes.wm.service.impl;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.barcode.BarcodeUtil;
import com.t3rik.common.utils.file.FileUtils;
import com.t3rik.mes.wm.domain.WmBarcode;
import com.t3rik.mes.wm.mapper.WmBarcodeMapper;
import com.t3rik.mes.wm.service.IWmBarcodeService;
import com.t3rik.service.MinIOService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 条码清单Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-08-01
 */
@Service
@Slf4j
public class WmBarcodeServiceImpl implements IWmBarcodeService {
    @Resource
    private WmBarcodeMapper wmBarcodeMapper;
    @Resource
    private MinIOService minIOService;

    /**
     * 查询条码清单
     *
     * @param barcodeId 条码清单主键
     * @return 条码清单
     */
    @Override
    public WmBarcode selectWmBarcodeByBarcodeId(Long barcodeId) {
        return wmBarcodeMapper.selectWmBarcodeByBarcodeId(barcodeId);
    }

    /**
     * 查询条码清单列表
     *
     * @param wmBarcode 条码清单
     * @return 条码清单
     */
    @Override
    public List<WmBarcode> selectWmBarcodeList(WmBarcode wmBarcode) {
        return wmBarcodeMapper.selectWmBarcodeList(wmBarcode);
    }

    @Override
    public String checkBarcodeUnique(WmBarcode wmBarcode) {
        WmBarcode barcode = wmBarcodeMapper.checkBarcodeUnique(wmBarcode);
        Long barcodeId = wmBarcode.getBarcodeId() == null ? -1L : wmBarcode.getBarcodeId();
        if (StringUtils.isNotNull(barcode) && barcode.getBarcodeId().longValue() != barcodeId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增条码清单
     *
     * @param wmBarcode 条码清单
     * @return 结果
     */
    @Override
    public int insertWmBarcode(WmBarcode wmBarcode) {
        wmBarcode.setCreateTime(DateUtils.getNowDate());
        return wmBarcodeMapper.insertWmBarcode(wmBarcode);
    }

    /**
     * 修改条码清单
     *
     * @param wmBarcode 条码清单
     * @return 结果
     */
    @Override
    public int updateWmBarcode(WmBarcode wmBarcode) {
        wmBarcode.setUpdateTime(DateUtils.getNowDate());
        return wmBarcodeMapper.updateWmBarcode(wmBarcode);
    }

    /**
     * 批量删除条码清单
     *
     * @param barcodeIds 需要删除的条码清单主键
     * @return 结果
     */
    @Override
    public int deleteWmBarcodeByBarcodeIds(Long[] barcodeIds) {
        return wmBarcodeMapper.deleteWmBarcodeByBarcodeIds(barcodeIds);
    }

    /**
     * 删除条码清单信息
     *
     * @param barcodeId 条码清单主键
     * @return 结果
     */
    @Override
    public int deleteWmBarcodeByBarcodeId(Long barcodeId) {
        return wmBarcodeMapper.deleteWmBarcodeByBarcodeId(barcodeId);
    }

    @Override
    public String generateBarcode(WmBarcode wmBarcode) {
        File file = BarcodeUtil.generateBarCode(wmBarcode.getBarcodeContent(), wmBarcode.getBarcodeFormart(),
                "./tmp/barcode/" + wmBarcode.getBarcodeContent() + ".png");
        String fileName = null;
        try {
            fileName = minIOService.uploadFile(file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            // 删除掉临时文件
            if (file != null && file.exists()) {
                FileUtils.deleteFile(file.getAbsolutePath());
            }
            return fileName;
        }

    }


}
