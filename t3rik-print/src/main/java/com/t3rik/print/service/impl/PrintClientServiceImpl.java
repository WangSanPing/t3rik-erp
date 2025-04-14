package com.t3rik.print.service.impl;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.md.domain.MdWorkstation;
import com.t3rik.mes.md.service.IMdWorkshopService;
import com.t3rik.mes.md.service.IMdWorkstationService;
import com.t3rik.print.domain.PrintClient;
import com.t3rik.print.domain.vo.ClientDictVO;
import com.t3rik.print.mapper.PrintClientMapper;
import com.t3rik.print.service.IPrintClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PrintClientServiceImpl implements IPrintClientService {

    @Autowired
    private PrintClientMapper clientMapper;

    @Autowired
    private IMdWorkshopService mdWorkshopService;

    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Override
    public List<PrintClient> getClientList(PrintClient client) {
        return clientMapper.getClientList(client);
    }

    @Override
    public AjaxResult insertClient(PrintClient client) {
        // 校验客户端编码是否存在
        if (UserConstants.NOT_UNIQUE.equals(checkClientCodeUnique(client))) {
            return AjaxResult.error("客户端编码已存在");
        }
        client.setCreateTime(DateUtils.getNowDate());
        return AjaxResult.success(clientMapper.insertClient(client));
    }

    @Override
    public AjaxResult selectClientById(Long clientId) {
        PrintClient printClient = clientMapper.selectById(clientId);
        return AjaxResult.success(printClient);
    }

    @Override
    public AjaxResult updateClient(PrintClient client) {
        // 校验客户端编码是否存在
        if (UserConstants.NOT_UNIQUE.equals(checkClientCodeUnique(client))) {
            return AjaxResult.error("客户端编码已存在");
        }
        client.setUpdateTime(DateUtils.getNowDate());
        clientMapper.updateClient(client);
        return null;
    }

    @Override
    public AjaxResult remove(String clientIds) {
        List<String> list = Arrays.asList(clientIds.split(","));
        return AjaxResult.success(clientMapper.deleteByIds(list));
    }

    @Override
    public AjaxResult getWorkshopAndWorkstation() {
        ClientDictVO clientDictVO = new ClientDictVO();
        // 获取工作站列表
        MdWorkstation mdWorkstation = new MdWorkstation();
        List<MdWorkstation> workstations = mdWorkstationService.selectMdWorkstationList(mdWorkstation);
        clientDictVO.setWorkstations(workstations);
        return AjaxResult.success(clientDictVO);
    }

    @Override
    public AjaxResult getAll() {
        PrintClient client = new PrintClient();
        return AjaxResult.success(clientMapper.getClientList(client));
    }

    /**
     * 校验客户端编码是否唯一
     * @param client
     * @return
     */
    private String checkClientCodeUnique(PrintClient client) {
        PrintClient printClient = clientMapper.checkCilentCodeUnique(client);

        Long clientId = client.getClientId() == null ? -1L : client.getClientId();
        if (StringUtils.isNotNull(printClient) && clientId.longValue() != printClient.getClientId().longValue()) {
            return UserConstants.NOT_UNIQUE;
        } else {
            return UserConstants.UNIQUE;
        }
    }
}
