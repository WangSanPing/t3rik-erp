package com.t3rik.mes.api.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.entity.SysDictData;
import com.t3rik.common.utils.DictUtils;
import com.t3rik.mes.api.domain.WorkOrderParams;
import com.t3rik.mes.api.service.IMesWorkOrderService;
import com.t3rik.mes.api.utils.UrlCode;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//mes工单
@Service
public class MesWorkOrderServiceImpl implements IMesWorkOrderService {


    @Override
    @Transactional
    public AjaxResult pushMesWorkOrder(SalesOrder salesOrder) {
        List<SysDictData> datas = JSONArray.parseArray(DictUtils.getDictCache("order_type").toString(),SysDictData.class);
        List<SalesOrderItem> list = salesOrder.getSalesOrderItemList();
        List<WorkOrderParams> workOrderParamList = new ArrayList<>();
        Map<String, SysDictData> typeMap = datas.stream().collect
                ((Collectors.toMap(SysDictData::getDictValue, sysDictData -> sysDictData)));
        list.stream().forEach(f -> {
            WorkOrderParams workOrderParams = new WorkOrderParams();
            workOrderParams.setWorkorderNo(f.getSalesOrderItemCode());
            workOrderParams.setSalesorderNo(f.getSalesOrderItemCode());
            workOrderParams.setWorkorderType(typeMap.get(salesOrder.getOrderType()).getDictLabel());
//            workOrderParams.setUrgentLevel(3);
            workOrderParams.setMaterialNo(f.getProductCode());
            workOrderParams.setMaterialDesc(f.getProductName());
            workOrderParams.setMaterialSpec(f.getProductSpec());
            workOrderParams.setUnit(f.getUnitOfMeasure());
            workOrderParams.setPlanQty(f.getSalesOrderQuantity().intValue());
            workOrderParams.setStartTime(f.getSalesOrderDate().getTime());
            workOrderParams.setEndTime(f.getDeliveryDate().getTime());
            workOrderParams.setCustomerNo(f.getClientCode());
            workOrderParams.setCustomerRequirement("");
            workOrderParams.setCustomizationNo("");
            workOrderParams.setOrderMaster(salesOrder.getFollowerMan());
            workOrderParams.setSales(salesOrder.getSalesMan());
            workOrderParams.setPlanFollowWordNo("");
            workOrderParams.setRemark(f.getRemark());
            workOrderParams.setRoutingsMaterialNo("");
            workOrderParamList.add(workOrderParams);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("workorderParams", workOrderParamList);

        JSONObject msgEn = this.filter(new JSONObject(map), UrlCode.PUSH_ORDER);
        AjaxResult ajaxResult = JSON.parseObject(msgEn.getString("data"), AjaxResult.class);
        return ajaxResult;
    }

    public JSONObject filter(JSONObject date, String url) {
        HttpClient client = HttpClients.createDefault();
        // 要调用的接口方法
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            StringEntity s = new StringEntity(date.toString(),"UTF-8");
//            s.setContentEncoding("UTF-8");
//            s.setContentType("UTF-8");
            post.setEntity(s);
            post.addHeader("content-type", "application/json");
            post.addHeader("Host", "a.lightmes.cn");
            post.addHeader("AccessKeyId", "BD9FDEF6AA1FEA84A071F82C68A9D4AD");
            post.addHeader("AccessKeySecret", "EC9B73C812378717650F6586A6329E53");
            HttpResponse res = client.execute(post);
            String response1 = EntityUtils.toString(res.getEntity());
            System.out.println("----------------------------------------");
            System.out.println(res.getStatusLine());
            System.out.println(response1);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                jsonObject = JSONObject.parseObject(response1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}
