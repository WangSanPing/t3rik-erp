package com.t3rik.print.service.impl;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.dv.domain.DvMachinery;
import com.t3rik.mes.dv.service.IDvMachineryService;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.service.IMdItemService;
import com.t3rik.mes.pro.mapper.ProRouteMapper;
import com.t3rik.mes.pro.mapper.ProRouteProductMapper;
import com.t3rik.mes.pro.service.IProRouteProcessService;
import com.t3rik.mes.wm.domain.WmBarcode;
import com.t3rik.mes.wm.service.IWmBarcodeService;
import com.t3rik.print.domain.PrintBarcodeModel;
import com.t3rik.print.domain.PrintPrinterConfig;
import com.t3rik.print.protocol.PrintMessageProto;
import com.t3rik.print.service.IPrintPrinterConfigService;
import com.t3rik.print.service.IPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 标签打印业务处理类
 *
 * @author skyyan
 */
@Service
public class PrintServiceImpl implements IPrintService {

    @Autowired
    private IPrintPrinterConfigService printPrinterConfigService;
    @Autowired
    private IMdItemService iMdItemService;
    @Autowired
    private IWmBarcodeService wmBarcodeService;
    @Autowired
    private IDvMachineryService iDvMachineryService;
//    @Autowired
//    private IProCardService iProCardService;
    @Autowired
    private ProRouteProductMapper proRouteProductMapper;
    @Autowired
    private ProRouteMapper proRouteMapper;
    @Autowired
    private IProRouteProcessService iProRouteProcessService;

    @Override
    public Map<String,Object> printLabel(PrintBarcodeModel printBarcodeModel) {
        Map<String,Object> result = new HashMap<>();
        result.put("msg","ERROR");
        String printerCode = printBarcodeModel.getPrinterCode();
        String businessType = printBarcodeModel.getBusinessType();
        Map<String, String> params = printBarcodeModel.getParams();
        Long businessId = printBarcodeModel.getBusinessId();
        String businessCode = printBarcodeModel.getBusinessCode();
        PrintPrinterConfig printPrinterConfig = printPrinterConfigService.selectPrintPrinterConfigByPrinterCode(printerCode);
        if (printPrinterConfig == null) {
            result.put("data","打印机：" + printerCode + "不存在");
            return result;
        }
        String ip = printPrinterConfig.getPrinterIp();
        String printPort = printPrinterConfig.getPrinterPort().toString();
        String printName = printPrinterConfig.getPrinterName();
        PrintMessageProto.Printer.DataType dataType = PrintMessageProto.Printer.DataType.IQC_PrintMessage;
        //根据打印机编码id获取打印机你信息
//        String clientIp = printPrinterConfig.getClientIp();
//        if (StringUtils.isEmpty(clientIp)) {
//            result.put("data","请检查打印机客户端信息配置！");
//            return result;
//        }
        PrintMessageProto.Printer msg = null;
        PrintMessageProto.Printer.PrintInfo printInfo = PrintMessageProto.Printer.PrintInfo.newBuilder().setIp(ip).setCode(printerCode).setName(printName).setPort(printPort).build();

        switch (businessType) {
            case UserConstants.BARCODE_TYPE_ITEM:
                //物料标签打印
                //封装模板数据
                MdItem item = null;
                if (Optional.ofNullable(businessId).isPresent()) {
                    item = iMdItemService.selectMdItemById(businessId);
                    if (item == null) {
                        result.put("data","条码内容数据为空！" + "(" + businessId + ")");
                        return result;
                    }
                } else if (StringUtils.isNotEmpty(businessCode)) {
                    MdItem itemParam = new MdItem();
                    itemParam.setItemCode(businessCode);
                    List<MdItem> itemList = iMdItemService.selectMdItemList(itemParam);
                    if (CollectionUtils.isEmpty(itemList)) {
                        result.put("data","条码内容数据为空！" + "(" + businessCode + ")");
                        return result;
                    }
                    item = itemList.get(0);
                } else {
                    result.put("data","缺少业务参数！");
                    return result;
                }
                //二维码信息查询
                WmBarcode bacode = new WmBarcode();
                bacode.setBussinessId(item.getItemId());
                bacode.setBussinessCode(item.getItemCode());
                bacode.setBarcodeType(UserConstants.BARCODE_TYPE_ITEM);
                List<WmBarcode> wmBarcodes = wmBarcodeService.selectWmBarcodeList(bacode);
                if (CollectionUtils.isEmpty(wmBarcodes)) {
                    result.put("data","未查询到二维码信息！" + "(" + businessCode + ")");
                    return result;
                }
                WmBarcode wmBarcode = wmBarcodes.get(0);
                String materialCode = item.getItemCode();//物料编码
                String materialName = item.getItemName();//物料名称
                String specificationAndModel = item.getSpecification();//物料规格类型

                String param = wmBarcode.getBarcodeUrl();//物料二维码
                dataType = PrintMessageProto.Printer.DataType.Material_Products;
                PrintMessageProto.Printer.MaterialProducts materialProducts = PrintMessageProto.Printer.MaterialProducts.newBuilder().setMaterialCode(materialCode).setMaterialName(materialName).setSpecificationAndModel(specificationAndModel).setParam(param).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setMaterialProducts(materialProducts).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_PACKAGE:
//                String materialCode = params.get("materialCode");
//                String materialName = params.get("materialName");
//                String specificationAndModel = params.get("specificationAndModel");
//                String param = params.get("param");
//                PrintMessageProto.Printer.DataType dataType = PrintMessageProto.Printer.DataType.Material_Products;
//                PrintMessageProto.Printer.MaterialProducts materialProducts = PrintMessageProto.Printer.MaterialProducts.newBuilder().setMaterialCode(materialCode).setMaterialName(materialName).setSpecificationAndModel(specificationAndModel).setParam(param).build();
//                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setMaterialProducts(materialProducts).setDataType(dataType).setPrintInfo(printInfo).build();
//                break;
            case UserConstants.BARCODE_TYPE_STOCK:
                //仓库标签打印
                //封装模板数据
//                String warehouseCode = params.get("warehouseCode");
//                String warehouseName = params.get("warehouseName");
//                String personInCharge = params.get("personInCharge");
//                dataType = PrintMessageProto.Printer.DataType.Warehouse_;
//                PrintMessageProto.Printer.Warehouse warehouse = PrintMessageProto.Printer.Warehouse.newBuilder().setWarehouseCode(warehouseCode).setWarehouseName(warehouseName).setPersonInCharge(personInCharge).build();
//                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setWarehouse(warehouse).setDataType(dataType).setPrintInfo(printInfo).build();
//                break;
            case UserConstants.BARCODE_TYPE_MACHINERY:
                //设备标签打印
                //封装模板数据
                DvMachinery dvMachinery = null;
                if (Optional.ofNullable(businessId).isPresent()) {
                    dvMachinery = iDvMachineryService.selectDvMachineryByMachineryId(businessId);
                    if (dvMachinery == null) {
                        result.put("data","条码内容数据为空！" + "(" + businessId + ")");
                        return result;
                    }
                } else if (StringUtils.isNotEmpty(businessCode)) {
                    DvMachinery mParam = new DvMachinery();
                    mParam.setMachineryCode(businessCode);
                    List<DvMachinery> mList = iDvMachineryService.selectDvMachineryList(mParam);
                    if (CollectionUtils.isEmpty(mList)) {
                        result.put("data","条码内容数据为空！" + "(" + businessId + ")");
                        return result;
                    }
                    dvMachinery = mList.get(0);
                } else {
                    result.put("data","缺少业务参数！");
                    return result;
                }
                //二维码信息查询
                WmBarcode mcode = new WmBarcode();
                mcode.setBussinessId(dvMachinery.getMachineryId());
                mcode.setBussinessCode(dvMachinery.getMachineryCode());
                mcode.setBarcodeType(UserConstants.BARCODE_TYPE_MACHINERY);
                List<WmBarcode> mBarcodes = wmBarcodeService.selectWmBarcodeList(mcode);
                if (CollectionUtils.isEmpty(mBarcodes)) {
                    result.put("data","未查询到二维码信息！" + "(" + businessCode + ")");
                    return result;
                }
                dataType = PrintMessageProto.Printer.DataType.Equipment_;
                PrintMessageProto.Printer.Equipment equipment = PrintMessageProto.Printer.Equipment.newBuilder().
                        setEquipmentCode(dvMachinery.getMachineryCode()).
                        setEquipmentName(dvMachinery.getMachineryName()).
                        setSpecificationAndModel(dvMachinery.getMachinerySpec()).
                        setParam(mBarcodes.get(0).getBarcodeUrl()).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setEquipment(equipment).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_WORKSTATION:
                //工作站标签打印
                //封装模板数据
                String workstationCode = params.get("workstationCode");
                String workstationName = params.get("workstationName");
                String belongingProcess = params.get("belongingProcess");
                String param2 = params.get("param");
                dataType = PrintMessageProto.Printer.DataType.Workstation_;
                PrintMessageProto.Printer.Workstation workstation = PrintMessageProto.Printer.Workstation.newBuilder().setWorkstationCode(workstationCode).setWorkstationName(workstationName).setBelongingProcess(belongingProcess).setParam(param2).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setWorkstation(workstation).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_PROCARD:
                //流转卡标签打印
                //封装模板数据
//                ProCard proCard = null;
//                if (Optional.ofNullable(businessId).isPresent()) {
//                    proCard = iProCardService.selectProCardByCardId(businessId);
//                    if (proCard == null) {
//                        result.put("data","条码内容数据为空！" + "(" + businessId + ")");
//                        return result;
//                    }
//                } else if (StringUtils.isNotEmpty(businessCode)) {
//                    ProCard pcParam = new ProCard();
//                    pcParam.setCardCode(businessCode);
//                    List<ProCard> mList = iProCardService.selectProCardList(pcParam);
//                    if (CollectionUtils.isEmpty(mList)) {
//                        result.put("data","条码内容数据为空！" + "(" + businessCode + ")");
//                        return result;
//                    }
//                    proCard = mList.get(0);
//                } else {
//                    result.put("data","缺少业务参数！");
//                    return result;
//                }
//                //二维码信息查询
//                WmBarcode pcBarcode = new WmBarcode();
//                pcBarcode.setBussinessId(proCard.getCardId());
//                pcBarcode.setBussinessCode(proCard.getCardCode());
//                pcBarcode.setBarcodeType(UserConstants.BARCODE_TYPE_PROCARD);
//                List<WmBarcode> pcBarcodes = wmBarcodeService.selectWmBarcodeList(pcBarcode);
//                if (CollectionUtils.isEmpty(pcBarcodes)) {
//                    result.put("data","未查询到二维码信息！" + "(" + businessCode + ")");
//                    return result;
//                }
//                Long routeId = -1L, processId = -1L;
//                ProRouteProduct proRouteProduct = new ProRouteProduct();
//                proRouteProduct.setItemId(proCard.getItemId());
//                List<ProRouteProduct> products = proRouteProductMapper.selectProRouteProductList(proRouteProduct);
//                if (CollectionUtil.isNotEmpty(products)) {
//                    products = products.stream().filter(i -> proRouteMapper.selectProRouteByRouteId(i.getRouteId()).getEnableFlag().equals(UserConstants.YES)).collect(Collectors.toList());
//                    if (CollectionUtil.isNotEmpty(products)) {
//                        routeId = products.get(0).getRouteId();
//                    }
//                }
//                ProRouteProcess proRouteProcess = new ProRouteProcess();
//                proRouteProcess.setRouteId(routeId);
//                String processingProcedure = "";
//                List<ProRouteProcess> proList = iProRouteProcessService.selectProRouteProcessList(proRouteProcess);
//                for (ProRouteProcess process : proList) {
//                    processingProcedure = processingProcedure + "," + process.getProcessName();
//                }
//                dataType = PrintMessageProto.Printer.DataType.Printing_OfCirculation;
//                PrintMessageProto.Printer.PrintingOfCirculation printingOfCirculation = PrintMessageProto.Printer.PrintingOfCirculation.newBuilder().
//                        setWorkOrderNumber(proCard.getWorkorderCode()).
//                        setMaterialCode(proCard.getItemCode()).
//                        setMaterialName(proCard.getItemName()).
//                        setSpecificationAndModel(proCard.getSpecification() == null ? "" : proCard.getSpecification()).
//                        setProcessingProcedure(processingProcedure).
//                        setParam(pcBarcodes.get(0).getBarcodeUrl()).build();
//                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setPrintingOfCirculation(printingOfCirculation).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_WAREHOUSE:
                // 仓库标签打印
                //封装模板数据
                String warehouseCode = params.get("warehouseCode");
                String warehouseName = params.get("warehouseName");
                String personInCharge = params.get("personInCharge");
                String param3 = params.get("param");
                dataType = PrintMessageProto.Printer.DataType.Warehouse_;
                PrintMessageProto.Printer.Warehouse warehouse = PrintMessageProto.Printer.Warehouse.newBuilder().setWarehouseCode(warehouseCode).setWarehouseName(warehouseName).setPersonInCharge(personInCharge).setParam(param3).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setWarehouse(warehouse).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_STORAGELOCATION:
                // 库区标签打印
                //封装模板数据
                String warehouseLocationCode = params.get("warehouseLocationCode");
                String warehouseLocationName = params.get("warehouseLocationName");
                String position = params.get("position");
                String param4 = params.get("param");
                dataType = PrintMessageProto.Printer.DataType.Warehouse_Location;
                PrintMessageProto.Printer.WarehouseLocation location = PrintMessageProto.Printer.WarehouseLocation.newBuilder().setWarehouseLocationCode(warehouseLocationCode).setWarehouseLocationName(warehouseLocationName).setPosition(position).setParam(param4).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setWarehouseLocation(location).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_STORAGEAREA:
                // 库位标签打印
                //封装模板数据
                String warehouseAreaCode = params.get("warehouseAreaCode");
                String warehouseAreaName = params.get("warehouseAreaName");
                dataType = PrintMessageProto.Printer.DataType.Warehouse_Area;
                String param5 = params.get("param");
                PrintMessageProto.Printer.WarehouseArea warehouseArea = PrintMessageProto.Printer.WarehouseArea.newBuilder().setWarehouseAreaCode(warehouseAreaCode).setWarehouseAreaName(warehouseAreaName).setParam(param5).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setWarehouseArea(warehouseArea).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            case UserConstants.BARCODE_TYPE_TRANSORDER:
                // 流转单标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.BARCODE_TYPE_CLIENT:
                // 客户标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.BARCODE_TYPE_VENDOR:
                // 供应商标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.BARCODE_TYPE_WORKSHOP:
                // 工作站标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.BARCODE_TYPE_WORKORDER:
                // 生产工单标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.BARCODE_TYPE_TOOL:
                // 工装夹具标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.BARCODE_TYPE_SN:
                // SN标签打印
                //封装模板数据
                //String sampleCode =params.get("sampleCode");
                //String qcObject =params.get("qcObject");
                //String sampleTime =params.get("sampleTime");
                //String batchCode =params.get("batchCode");
                //PrintMessageProt//o.Printer.IQCPrintMessage iQCPrintMessage = PrintMessageProto.Printer.IQCPrintMessage.newBuilder().setSampleCode(sampleCode).setQcObject(qcObject).setSampleTime(sampleTime).setBatchCode(batchCode).build();
                // 构造对应的消息对象
//                msg = PrintMessageProto.Printer.newBuilder().setIqcPrintMessage(iQCPrintMessage).setDataType(dataType).setPrintInfo(printClientInfoMessage).build();
                break;
            case UserConstants.TEST_TYPE_PRINTER:
                // 测试模板
                //封装模板数据
                String title =params.get("title");
                String text =params.get("text");
                PrintMessageProto.Printer.TestPrinter testPrinter = PrintMessageProto.Printer.TestPrinter.newBuilder().setTitle(title).setText(text).build();
                // 构造对应的消息对象
                msg = PrintMessageProto.Printer.newBuilder().setTestPrinter(testPrinter).setDataType(dataType).setPrintInfo(printInfo).build();
                break;
            default:
                result.put("data","打印机不支持当前模板：" + "(" + businessType + ")");
                return result;
        }
        result.put("msg","SUCESS");
//        result.put("clientIp",clientIp);
        result.put("data",msg);
        return result;
    }
}
