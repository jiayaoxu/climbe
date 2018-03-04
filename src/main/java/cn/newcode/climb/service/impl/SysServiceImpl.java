package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.UserMapper;
import cn.newcode.climb.po.Version;
import cn.newcode.climb.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/1 0001
 * \* Time: 19:48
 * \* Description:
 * \
 */
@Service("sysService")
public class SysServiceImpl implements SysService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Version getVersion() {
        return userMapper.selectVersion();
    }

    @Override
    public void updateVersion(String version) {
        userMapper.updateVersion(version);
    }
}