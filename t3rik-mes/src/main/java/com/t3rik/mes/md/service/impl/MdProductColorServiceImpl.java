package com.t3rik.mes.md.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.md.mapper.MdProductColorMapper;
import com.t3rik.mes.md.domain.MdProductColor;
import com.t3rik.mes.md.service.IMdProductColorService;

/**
 * 颜色Service业务层处理
 *
 * @author t3rik
 * @date 2024-08-29
 */
@Service
public class MdProductColorServiceImpl  extends ServiceImpl<MdProductColorMapper, MdProductColor>  implements IMdProductColorService
{
    @Autowired
    private MdProductColorMapper mdProductColorMapper;

}
