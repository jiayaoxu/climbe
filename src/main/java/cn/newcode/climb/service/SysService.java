package cn.newcode.climb.service;

import cn.newcode.climb.po.Version;

/**
 * @Description:
 * @author: shine
 * @CreateDate: 19:47 2018/3/1 0001
 * @Version: 1.0
 */
public interface SysService {

    Version getVersion();

    void updateVersion(String version);

}
