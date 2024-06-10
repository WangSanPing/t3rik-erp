package com.t3rik.pad.mes.controller.md.controller;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.service.IMdItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("物料信息")
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/md/item")
public class MdItemMobController extends BaseController {

    @Autowired
    private IMdItemService mdItemService;

    /**
     * 列表查询
     * @param mdItem
     * @return
     */
    @ApiOperation("查询物料清单（分页）")
    @GetMapping("/list")
    public TableDataInfo list(MdItem mdItem){
        startPage();
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return getDataTable(list);
    }
}
