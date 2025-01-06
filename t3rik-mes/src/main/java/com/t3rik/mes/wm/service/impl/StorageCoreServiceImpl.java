package com.t3rik.mes.wm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.enums.mes.DefaultDataEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.bean.BeanUtils;
import com.t3rik.mes.wm.domain.*;
import com.t3rik.mes.wm.domain.tx.*;
import com.t3rik.mes.wm.mapper.WmMaterialStockMapper;
import com.t3rik.mes.wm.service.*;
import org.simpleframework.xml.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class StorageCoreServiceImpl implements IStorageCoreService {

    @Autowired
    private IWmTransactionService wmTransactionService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    /**
     * 采购入库
     *
     * @param lines
     */
    @Override
    public void processItemRecpt(List<ItemRecptTxBean> lines) {
        String transactionType = UserConstants.TRANSACTION_TYPE_ITEM_RECPT;
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的入库单行");
        }

        for (int i = 0; i < lines.size(); i++) {
            ItemRecptTxBean line = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, line);
            transaction.setTransactionFlag(1); // 库存增加
            transaction.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction);
        }

    }

    /**
     * 供应商退货
     *
     * @param lines
     */
    @Override
    public void processRtVendor(List<RtVendorTxBean> lines) {
        String transactionType = UserConstants.TRANSACTION_TYPE_ITEM_RTV;
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的退货单行");
        }

        for (int i = 0; i < lines.size(); i++) {
            RtVendorTxBean line = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, line);
            transaction.setTransactionFlag(-1); // 库存减少
            transaction.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction);
        }

    }

    /**
     * 生产领料
     *
     * @param lines
     */
    @Override
    public void processIssue(List<IssueTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的领料单行");
        }

        String transactionType_out = UserConstants.TRANSACTION_TYPE_ITEM_ISSUE_OUT;
        String transactionType_in = UserConstants.TRANSACTION_TYPE_ITEM_ISSUE_IN;
        for (int i = 0; i < lines.size(); i++) {
            IssueTxBean line = lines.get(i);
            // 这里先构造一条原库存减少的事务
            WmTransaction transaction_out = new WmTransaction();
            transaction_out.setTransactionType(transactionType_out);
            BeanUtils.copyBeanProp(transaction_out, line);
            transaction_out.setTransactionFlag(-1);// 库存减少
            transaction_out.setStorageCheckFlag(Boolean.TRUE);//是否校验库存量
            transaction_out.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction_out);

            // 再构造一条目的库存增加的事务
            WmTransaction transaction_in = new WmTransaction();
            transaction_in.setTransactionType(transactionType_in);
            BeanUtils.copyBeanProp(transaction_in, line);
            transaction_in.setTransactionFlag(1);// 库存增加
            transaction_in.setStorageCheckFlag(Boolean.TRUE);//是否校验库存量

            // 由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
            transaction_in.setMaterialStockId(null);

            // 使用出库事务的供应商初始化入库事务的供应商
            transaction_in.setVendorId(transaction_out.getVendorId());
            transaction_in.setVendorCode(transaction_out.getVendorCode());
            transaction_in.setVendorName(transaction_out.getVendorName());
            transaction_in.setVendorNick(transaction_out.getVendorNick());

            // 这里使用系统默认生成的线边库初始化对应的入库仓库、库区、库位
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            transaction_in.setWarehouseId(warehouse.getWarehouseId());
            transaction_in.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_in.setWarehouseName(warehouse.getWarehouseName());
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            transaction_in.setLocationId(location.getLocationId());
            transaction_in.setLocationCode(location.getLocationCode());
            transaction_in.setLocationName(location.getLocationName());
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
            transaction_in.setAreaId(area.getAreaId());
            transaction_in.setAreaCode(area.getAreaCode());
            transaction_in.setAreaName(area.getAreaName());
            // 设置入库相关联的出库事务ID
            transaction_in.setRelatedTransactionId(transaction_out.getTransactionId());
            wmTransactionService.processTransaction(transaction_in);
        }
    }

    @Override
    public void processOutsourceIssue(List<OutsourceIssueTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的外协发货单行");
        }

        String transactionType_out = UserConstants.TRANSACTION_TYPE_ITEM_ISSUE_OUT;
        for (int i = 0; i < lines.size(); i++) {
            OutsourceIssueTxBean line = lines.get(i);
            // 构造一条库存减少的事务
            WmTransaction transaction_out = new WmTransaction();
            transaction_out.setTransactionType(transactionType_out);
            BeanUtils.copyBeanProp(transaction_out, line);
            transaction_out.setTransactionFlag(-1);// 库存减少
            transaction_out.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction_out);
        }
    }

    @Override
    public void processOutsourceRecpt(List<OutsourceRecptTxBean> lines) {
        String transactionType = UserConstants.TRANSACTION_TYPE_OUTSOURCE_RECPT_IN;
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的外协入库单行");
        }

        for (int i = 0; i < lines.size(); i++) {
            OutsourceRecptTxBean line = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, line);
            transaction.setTransactionFlag(1); // 库存增加
            transaction.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction);
        }
    }


    /**
     * 生产退料
     *
     * @param lines
     */
    @Override
    public void processRtIssue(List<RtIssueTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的退料单行");
        }

        //生产退料-出库事务
        String transactionType_out = UserConstants.TRANSACTION_TYPE_ITEM_RT_ISSUE_OUT;
        //生产退料-入库事务
        String transactionType_in = UserConstants.TRANSACTION_TYPE_ITEM_RT_ISSUE_IN;
        for (int i = 0; i < lines.size(); i++) {
            RtIssueTxBean line = lines.get(i);

            // 构造一条目的库存减少的事务
            WmTransaction transaction_out = new WmTransaction();
            //设置事务类型为出库
            transaction_out.setTransactionType(transactionType_out);
            BeanUtils.copyBeanProp(transaction_out, line);

            // 这里的出库事务默认从线边库出库到实际仓库
            //查询仓库-虚拟库存
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            transaction_out.setWarehouseId(warehouse.getWarehouseId());
            transaction_out.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_out.setWarehouseName(warehouse.getWarehouseName());
            //查询虚拟库区
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            transaction_out.setLocationId(location.getLocationId());
            transaction_out.setLocationCode(location.getLocationCode());
            transaction_out.setLocationName(location.getLocationName());
            //虚拟库位
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
            transaction_out.setAreaId(area.getAreaId());
            transaction_out.setAreaCode(area.getAreaCode());
            transaction_out.setAreaName(area.getAreaName());

            /** 库存方向 */
            transaction_out.setTransactionFlag(-1);// 库存减少
            //检查库存不能为负数
            transaction_out.setStorageCheckFlag(Boolean.TRUE);//是否校验库存量
            wmTransactionService.processTransaction(transaction_out);

            // 构造一条目的库存增加的事务
            WmTransaction transaction_in = new WmTransaction();
            transaction_in.setTransactionType(transactionType_in);
            BeanUtils.copyBeanProp(transaction_in, line);
            transaction_in.setTransactionFlag(1);// 库存增加
            transaction_in.setTransactionDate(new Date());
            // 由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
            transaction_in.setMaterialStockId(null);
            transaction_in.setStorageCheckFlag(Boolean.TRUE);
            // 设置入库相关联的出库事务ID
            transaction_in.setRelatedTransactionId(transaction_out.getTransactionId());

            wmTransactionService.processTransaction(transaction_in);
        }
    }

    /**
     * 库存消耗
     */
    public void processItemConsume(List<ItemConsumeTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的原料消耗单行");
        }
        String transactionType = UserConstants.TRANSACTION_TYPE_ITEM_CONSUME;
        for (int i = 0; i < lines.size(); i++) {
            ItemConsumeTxBean line = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, line);
            transaction.setTransactionFlag(-1); // 库存减少
            transaction.setStorageCheckFlag(false);// 库存可以为负
            transaction.setTransactionDate(new Date());

            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            transaction.setWarehouseId(warehouse.getWarehouseId());
            transaction.setWarehouseCode(warehouse.getWarehouseCode());
            transaction.setWarehouseName(warehouse.getWarehouseName());
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            transaction.setLocationId(location.getLocationId());
            transaction.setLocationCode(location.getLocationCode());
            transaction.setLocationName(location.getLocationName());
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
            transaction.setAreaId(area.getAreaId());
            transaction.setAreaCode(area.getAreaCode());
            transaction.setAreaName(area.getAreaName());

            wmTransactionService.processTransaction(transaction);
        }
    }

    /**
     * 产品产出
     */
    public void processProductProduce(List<ProductProductTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的产品产出单行");
        }
        String transactionType = UserConstants.TRANSACTION_TYPE_PRODUCT_PRODUCE;
        for (int i = 0; i < lines.size(); i++) {
            ProductProductTxBean line = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, line);
            transaction.setTransactionFlag(1); // 库存增加
            transaction.setTransactionDate(new Date());

            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            transaction.setWarehouseId(warehouse.getWarehouseId());
            transaction.setWarehouseCode(warehouse.getWarehouseCode());
            transaction.setWarehouseName(warehouse.getWarehouseName());
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            transaction.setLocationId(location.getLocationId());
            transaction.setLocationCode(location.getLocationCode());
            transaction.setLocationName(location.getLocationName());
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
            transaction.setAreaId(area.getAreaId());
            transaction.setAreaCode(area.getAreaCode());
            transaction.setAreaName(area.getAreaName());

            wmTransactionService.processTransaction(transaction);
        }
    }


    /**
     * 产品入库
     *
     * @param lines
     */
    @Override
    public void processProductRecpt(List<ProductRecptTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的产品入库单行");
        }
        String transactionType_out = UserConstants.TRANSACTION_TYPE_PRODUCT_RECPT_OUT;
        String transactionType_in = UserConstants.TRANSACTION_TYPE_PRODUCT_RECPT_IN;

        for (int i = 0; i < lines.size(); i++) {
            ProductRecptTxBean line = lines.get(i);

            // 构造一条目的库存减少的事务
            WmTransaction transaction_out = new WmTransaction();
            transaction_out.setTransactionType(transactionType_out);
            BeanUtils.copyBeanProp(transaction_out, line);

            // 这里的产品入库是从线边库入到实际的仓库，出库事务对应的仓库是线边库
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            transaction_out.setWarehouseId(warehouse.getWarehouseId());
            transaction_out.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_out.setWarehouseName(warehouse.getWarehouseName());
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            transaction_out.setLocationId(location.getLocationId());
            transaction_out.setLocationCode(location.getLocationCode());
            transaction_out.setLocationName(location.getLocationName());
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
            transaction_out.setAreaId(area.getAreaId());
            transaction_out.setAreaCode(area.getAreaCode());
            transaction_out.setAreaName(area.getAreaName());

            transaction_out.setTransactionFlag(-1);// 库存减少
            transaction_out.setStorageCheckFlag(false); // 针对未及时报工的情况，允许线边库的库存临时为负
            wmTransactionService.processTransaction(transaction_out);

            // 构造一条目的库存增加的事务
            WmTransaction transaction_in = new WmTransaction();
            transaction_in.setTransactionType(transactionType_in);
            BeanUtils.copyBeanProp(transaction_in, line);
            transaction_in.setTransactionFlag(1);// 库存增加
            transaction_in.setTransactionDate(new Date());
            // 由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
            transaction_in.setMaterialStockId(null);
            // 设置入库相关联的出库事务ID
            transaction_in.setRelatedTransactionId(transaction_out.getTransactionId());

            wmTransactionService.processTransaction(transaction_in);
        }


    }

    /**
     * 销售出库
     *
     * @param lines
     */
    @Override
    public void processProductSalse(List<ProductSalseTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的产品销售出库单行");
        }

        String transactionType = UserConstants.TRANSACTION_TYPE_PRODUCT_ISSUE;
        for (int i = 0; i < lines.size(); i++) {
            ProductSalseTxBean bean = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, bean);
            transaction.setTransactionFlag(-1); // 库存减少
            transaction.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction);
        }
    }

    @Override
    public void processRtSalse(List<RtSalseTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的原料消耗单行");
        }
        String transactionType = UserConstants.TRANSACTION_TYPE_PRODUCT_RS;
        for (int i = 0; i < lines.size(); i++) {
            RtSalseTxBean bean = lines.get(i);
            WmTransaction transaction = new WmTransaction();
            transaction.setTransactionType(transactionType);
            BeanUtils.copyBeanProp(transaction, bean);
            transaction.setTransactionFlag(1); // 库存增加
            transaction.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction);
        }
    }

    @Override
    public void processTransfer(List<TransferTxBean> lines) {
        if (CollUtil.isEmpty(lines)) {
            throw new BusinessException("没有需要处理的原料消耗单行");
        }
        String transactionType_out = UserConstants.TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT;
        String transactionType_in = UserConstants.TRANSACTION_TYPE_WAREHOUSE_TRANS_IN;

        for (int i = 0; i < lines.size(); i++) {
            TransferTxBean line = lines.get(i);
            // 先执行出库
            WmTransaction transaction_out = new WmTransaction();
            transaction_out.setTransactionType(transactionType_out);
            BeanUtils.copyBeanProp(transaction_out, line);
            transaction_out.setWarehouseId(line.getFromWarehouseId());
            transaction_out.setWarehouseCode(line.getFromWarehouseCode());
            transaction_out.setWarehouseName(line.getFromWarehouseName());
            transaction_out.setLocationId(line.getFromLocationId());
            transaction_out.setLocationCode(line.getFromLocationCode());
            transaction_out.setLocationName(line.getFromLocationName());
            transaction_out.setAreaId(line.getFromAreaId());
            transaction_out.setAreaCode(line.getFromAreaCode());
            transaction_out.setAreaName(line.getFromAreaName());
            transaction_out.setTransactionFlag(-1);// 库存减少
            wmTransactionService.processTransaction(transaction_out);
            // 再执行入库
            WmTransaction transaction_in = new WmTransaction();
            transaction_in.setTransactionType(transactionType_in);
            BeanUtils.copyBeanProp(transaction_in, line);
            transaction_in.setWarehouseId(line.getToWarehouseId());
            transaction_in.setWarehouseCode(line.getToWarehouseCode());
            transaction_in.setWarehouseName(line.getToWarehouseName());
            transaction_in.setLocationId(line.getToLocationId());
            transaction_in.setLocationCode(line.getToLocationCode());
            transaction_in.setLocationName(line.getToLocationName());
            transaction_in.setAreaId(line.getToAreaId());
            transaction_in.setAreaCode(line.getToAreaCode());
            transaction_in.setAreaName(line.getToAreaName());

            transaction_in.setTransactionFlag(1);// 库存增加
            transaction_in.setTransactionDate(new Date());
            // 由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
            transaction_in.setMaterialStockId(null);
            // 设置入库相关联的出库事务ID
            transaction_in.setRelatedTransactionId(transaction_out.getTransactionId());
            wmTransactionService.processTransaction(transaction_in);
        }

    }
    @Autowired
    private WmMaterialStockMapper wmMaterialStockMapper;
    /**
     * 执行废料信息
     * @param beans
     */

    @Override
    public void processWmWaste(List<WmWasteTxBean> beans) {
        //校验数据是否为空
        if (CollUtil.isEmpty(beans))
            throw new BusinessException("没有需要处理废料的单行！");

        //废料退料-入库事务
        String transactionType_in = DefaultDataEnum.TRANSACTION_TYPE_ITEM_WM_WASTE_IN.getCode();
        //生产退料-出库事务
        String transactionType_out = DefaultDataEnum.TRANSACTION_TYPE_ITEM_RT_ISSUE_OUT.getCode();
        beans.forEach(a -> {
            //构造信息 将信息存入线边库
            //校验 取出来的数量不超过领料数量
            //线边库-虚拟库存出库（退料）取出数据放到线边库-废料库存入库（废料）
            WmTransaction transaction_out = new WmTransaction();
            transaction_out.setTransactionType(transactionType_out);
            BeanUtils.copyBeanProp(transaction_out, a);
            //设置出库事务类型
            transaction_out.setTransactionFlag(-1);// 库存减少
            transaction_out.setStorageCheckFlag(Boolean.TRUE);//是否校验库存量
            transaction_out.setTransactionDate(new Date());
            //添加生产工单id 以及编号

            //查询仓库-虚拟库存
             WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(DefaultDataEnum.VIRTUAL_WH.getCode());
            transaction_out.setWarehouseId(warehouse.getWarehouseId());
            transaction_out.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_out.setWarehouseName(warehouse.getWarehouseName());
            //查询虚拟库区
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationCode(DefaultDataEnum.VIRTUAL_WS.getCode());
            transaction_out.setLocationId(location.getLocationId());
            transaction_out.setLocationCode(location.getLocationCode());
            transaction_out.setLocationName(location.getLocationName());
            //虚拟库位
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaCode(DefaultDataEnum.VIRTUAL_WA.getCode());
            transaction_out.setAreaId(area.getAreaId());
            transaction_out.setAreaCode(area.getAreaCode());
            transaction_out.setAreaName(area.getAreaName());


            //添加事务
            wmTransactionService.processTransaction(transaction_out);

            //构造信息 将信息存入线边库-废料库存
            //线边库新增虚拟物料数量
            //构造一个虚拟入库事务
            WmTransaction transaction_in = new WmTransaction();
            //拷贝数据
            BeanUtils.copyBeanProp(transaction_in,a);
            //查询虚拟废料虚拟线边库
            //查询仓库-虚拟库存
            WmWarehouse warehouseWaste = wmWarehouseService.selectWmWarehouseByWarehouseCode(DefaultDataEnum.WASTE_VIRTUAL_WH.getCode());
            transaction_in.setWarehouseId(warehouseWaste.getWarehouseId());
            transaction_in.setWarehouseCode(warehouseWaste.getWarehouseCode());
            transaction_in.setWarehouseName(warehouseWaste.getWarehouseName());
            //查询虚拟库区
            WmStorageLocation locationWaste = wmStorageLocationService.selectWmStorageLocationByLocationCode(DefaultDataEnum.WASTE_VIRTUAL_WS.getCode());
            transaction_in.setLocationId(locationWaste.getLocationId());
            transaction_in.setLocationCode(locationWaste.getLocationCode());
            transaction_in.setLocationName(locationWaste.getLocationName());
            //虚拟库位
            WmStorageArea areaWaste = wmStorageAreaService.selectWmStorageAreaByAreaCode(DefaultDataEnum.WASTE_VIRTUAL_WA.getCode());
            transaction_in.setAreaId(areaWaste.getAreaId());
            transaction_in.setAreaCode(areaWaste.getAreaCode());
            transaction_in.setAreaName(areaWaste.getAreaName());

            transaction_in.setTransactionFlag(1);//新增
            transaction_in.setTransactionType(transactionType_in);
            transaction_in.setMaterialStockId(null);
            transaction_in.setStorageCheckFlag(Boolean.TRUE);//是否校验库存量
            transaction_in.setRelatedTransactionId(transaction_out.getTransactionId());
            //查询线边库信息
            WmMaterialStock st = wmMaterialStockMapper.selectWmMaterialStockByMaterialStockId(a.getMaterialStockId());

            transaction_in.setWorkorderId(st.getWorkorderId());//生产单id
            transaction_in.setWorkorderCode(st.getWorkorderCode());//生产单编号
            wmTransactionService.processTransaction(transaction_in);//添加事务以及虚拟库信息
        });

    }
    
}
