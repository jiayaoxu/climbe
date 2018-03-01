package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_limit;
import cn.newcode.climb.vo.UserLimitVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_limitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_limit record);

    int insertSelective(User_limit record);

    User_limit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_limit record);

    int updateByPrimaryKey(User_limit record);

    List<UserLimitVo> selectLimit(@Param("name") String name);

    String isGm(Integer uid);
}