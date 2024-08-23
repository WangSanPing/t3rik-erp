package com.t3rik.mobile.mes.service

import com.t3rik.mes.common.dto.KeyValueDTO


/**
 * 手机端共通service
 * @author t3rik
 * @date 2024/8/23 17:15
 */
interface IMesMobileService {

    /**
     * 获取所有仓库信息，包括库区库位
     */
    suspend fun getWarehouseInfo(): HashMap<String, MutableList<KeyValueDTO>>
}