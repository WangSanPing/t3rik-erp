package com.t3rik.mes.wm.service;

import com.t3rik.mes.wm.domain.tx.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IStorageCoreService {

    /**
     * 处理物料入库单
     * @param lines
     */
    public void processItemRecpt(List<ItemRecptTxBean> lines);

    /**
     * 处理供应商退货单
     * @param lines
     */
    public void processRtVendor(List<RtVendorTxBean> lines);

    /**
     * 处理生产领料
     * @param lines
     */
    public void processIssue(List<IssueTxBean> lines);

    /**
     * 处理外协领料
     * @param lines
     */
    public void processOutsourceIssue(List<OutsourceIssueTxBean> lines);

    /**
     * 处理外协入库
     * @param lines
     */
    public void processOutsourceRecpt(List<OutsourceRecptTxBean> lines);

    /**
     * 处理生产退料
     * @param lines
     */
    public void processRtIssue(List<RtIssueTxBean> lines);

    /**
     * 处理生产消耗
     * @param lines
     */
    public void processItemConsume(List<ItemConsumeTxBean> lines);

    /**
     * 处理产品产出
     * @param lines
     */
    public void processProductProduce(List<ProductProductTxBean> lines);

    /**
     * 处理产品入库
     * @param lines
     */
    public void processProductRecpt(List<ProductRecptTxBean> lines);

    /**
     * 处理产品销售出库
     * @param lines
     */
    public void processProductSalse(List<ProductSalseTxBean> lines);

    /**
     * 处理销售退货
     * @param lines
     */
    public void processRtSalse(List<RtSalseTxBean> lines);

    /**
     * 处理转移单
     * @param lines
     */
    public void processTransfer(List<TransferTxBean> lines);

    /**
     * 执行废料信息
     * @param beans
     */
    void processWmWaste(List<WmWasteTxBean> beans);
}
