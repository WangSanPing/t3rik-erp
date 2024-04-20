package com.t3rik.mes.wm.service;

import java.util.List;
import com.t3rik.mes.wm.domain.WmOutsourceRecptLine;

/**
 * 外协入库单行Service接口
 * 
 * @author yinjinlu
 * @date 2023-10-30
 */
public interface IWmOutsourceRecptLineService 
{
    /**
     * 查询外协入库单行
     * 
     * @param lineId 外协入库单行主键
     * @return 外协入库单行
     */
    public WmOutsourceRecptLine selectWmOutsourceRecptLineByLineId(Long lineId);

    /**
     * 查询外协入库单行列表
     * 
     * @param wmOutsourceRecptLine 外协入库单行
     * @return 外协入库单行集合
     */
    public List<WmOutsourceRecptLine> selectWmOutsourceRecptLineList(WmOutsourceRecptLine wmOutsourceRecptLine);

    /**
     * 根据外协入库单ID查询所有入库单行
     * @param recptId
     * @return
     */
    public List<WmOutsourceRecptLine> selectWmOutsourceRecptLineByRecptId(Long recptId);

    /**
     * 新增外协入库单行
     * 
     * @param wmOutsourceRecptLine 外协入库单行
     * @return 结果
     */
    public int insertWmOutsourceRecptLine(WmOutsourceRecptLine wmOutsourceRecptLine);

    /**
     * 修改外协入库单行
     * 
     * @param wmOutsourceRecptLine 外协入库单行
     * @return 结果
     */
    public int updateWmOutsourceRecptLine(WmOutsourceRecptLine wmOutsourceRecptLine);

    /**
     * 批量删除外协入库单行
     * 
     * @param lineIds 需要删除的外协入库单行主键集合
     * @return 结果
     */
    public int deleteWmOutsourceRecptLineByLineIds(Long[] lineIds);

    /**
     * 删除外协入库单行信息
     * 
     * @param lineId 外协入库单行主键
     * @return 结果
     */
    public int deleteWmOutsourceRecptLineByLineId(Long lineId);

    /**
     * 根据外协入库单头ID删除所有行
     * @param recptId
     * @return
     */
    public int deleteWmOutsourceRecptLineByRecptId(Long recptId);
}
