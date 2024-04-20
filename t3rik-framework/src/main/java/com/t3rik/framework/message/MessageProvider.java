package com.t3rik.framework.message;

import com.alibaba.fastjson.JSON;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.entity.SysUser;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.framework.websocket.WebSocketUsers;
import com.t3rik.system.domain.SysMessage;
import com.t3rik.system.service.ISysMessageService;
import com.t3rik.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageProvider {

    @Autowired
    private ISysMessageService sysMessageService;
    @Autowired
    private ISysUserService sysUserService;

    @Transactional
    public void sendMessage(SysMessage message){
        message.setStatus(UserConstants.MESSAGE_STATUS_UNREAD);
        if(StringUtils.isNotNull(message.getRecipientId())){
            SysUser recipient = sysUserService.selectUserById(message.getRecipientId());
            message.setRecipientName(recipient.getUserName());
            message.setRecipientNick(recipient.getNickName());
        }
        if(StringUtils.isNotNull(message.getSenderId())){
            SysUser sender = sysUserService.selectUserById(message.getSenderId());
            message.setRecipientName(sender.getUserName());
            message.setRecipientNick(sender.getNickName());
        }
        message.setDeletedFlag(UserConstants.NO);
        WebSocketUsers.sendMesssageToUserByName(message.getRecipientName(), JSON.toJSONString(message));
        sysMessageService.insertSysMessage(message);
    }

}
