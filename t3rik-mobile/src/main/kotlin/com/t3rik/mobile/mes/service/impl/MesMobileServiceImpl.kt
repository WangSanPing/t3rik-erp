package com.t3rik.mobile.mes.service.impl

import com.t3rik.common.constant.UserConstants
import com.t3rik.common.enums.mes.DefaultDataEnum
import com.t3rik.mes.common.dto.SelectInfoDTO
import com.t3rik.mes.common.service.ICommonService
import com.t3rik.mobile.mes.service.IMesMobileService
import jakarta.annotation.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.springframework.stereotype.Service


/**
 * 手机端共通service
 * @author t3rik
 * @date 2024/8/23 17:21
 */
@Service
class MesMobileServiceImpl : IMesMobileService {

    @Resource
    lateinit var commonService: ICommonService


    /**
     * 获取所有仓库信息，包括库区库位
     */
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getWarehouseInfo(): HashMap<String, MutableList<SelectInfoDTO>> {
        val result = GlobalScope.async {
            // 仓库
            val whList = async {
                commonService.selectAllWarehouse(
                    mutableListOf(
                        DefaultDataEnum.VIRTUAL_WH.code,
                        DefaultDataEnum.WASTE_VIRTUAL_WH.code
                    )
                )
            }
            // 库区
            val wsList = async {
                commonService.selectAllLocation(
                    mutableListOf(
                        DefaultDataEnum.VIRTUAL_WS.code,
                        DefaultDataEnum.WASTE_VIRTUAL_WS.code
                    )
                )
            }
            // 库位
            val waList = async {
                commonService.selectAllArea(
                    mutableListOf(
                        DefaultDataEnum.VIRTUAL_WA.code,
                        DefaultDataEnum.WASTE_VIRTUAL_WA.code
                    )
                )
            }
            HashMap<String, MutableList<SelectInfoDTO>>().apply {
                put(UserConstants.WAREHOUSE, whList.await())
                put(UserConstants.STORAGE_LOCATION, wsList.await())
                put(UserConstants.STORAGE_AREA, waList.await())
            }
        }
        return result.await()
    }
}