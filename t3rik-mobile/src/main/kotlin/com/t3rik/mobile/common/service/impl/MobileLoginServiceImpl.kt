package com.t3rik.mobile.common.service.impl

import com.t3rik.mobile.common.service.IMobileLoginService
import com.t3rik.mobile.common.vo.DictVo
import com.t3rik.system.service.ISysDictDataService
import org.springframework.stereotype.Service
import javax.annotation.Resource


@Service
class MobileLoginServiceImpl : IMobileLoginService {

    @Resource
    lateinit var dictDataService: ISysDictDataService

    /**
     * 获取移动端需要的字典集合
     */
    override fun getDictList(): MutableList<DictVo> {
        val dictList = this.dictDataService.selectDictDataList(null).groupBy { it.dictType }
        return dictList.map { (dictKey, data) ->
            val dataLists =
                data.map { DictVo.DataItem(label = it.dictLabel, value = it.dictValue, color = "") }.toMutableList()
            DictVo(dictKey = dictKey, data = dataLists)
        }.toMutableList()
    }
}