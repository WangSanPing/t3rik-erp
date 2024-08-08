package com.t3rik.mobile.common.service.impl

import com.t3rik.mobile.common.service.IMobileLoginService
import com.t3rik.mobile.common.vo.DictVo
import com.t3rik.system.service.ISysDictDataService
import org.springframework.stereotype.Service
import jakarta.annotation.Resource


@Service
class MobileLoginServiceImpl : IMobileLoginService {

    @Resource
    lateinit var dictDataService: ISysDictDataService

    /**
     * 获取移动端需要的字典集合
     */
    override fun getDictList(): MutableList<DictVo> {
        // 获取字典列表 -> 分组 -> 重组前端需要的格式
        val dictList = this.dictDataService.selectDictDataList(null).groupBy { it.dictType }
            .map { (dictKey, data) ->
                val dataLists =
                    data.map { dictData ->
                        DictVo.DataItem(
                            label = dictData.dictLabel,
                            value = dictData.dictValue,
                            color = ""
                        )
                    }.toMutableList()
                DictVo(dictKey = dictKey, data = dataLists)
            }.toMutableList()
        return dictList
    }
}