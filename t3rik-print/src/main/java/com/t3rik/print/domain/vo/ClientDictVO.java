package com.t3rik.print.domain.vo;

import com.t3rik.mes.md.domain.MdWorkshop;
import com.t3rik.mes.md.domain.MdWorkstation;
import lombok.Data;

import java.util.List;

@Data
public class ClientDictVO {

    // 车间列表
    private List<MdWorkshop> workshopList;

    // 工作站列表
    private List<MdWorkstation> workstations;

}
