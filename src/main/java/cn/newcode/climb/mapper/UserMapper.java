package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User;
import cn.newcode.climb.vo.IndexVo;
import cn.newcode.climb.vo.PersonalInf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User seletcByUsername(@Param("username") String username);

    PersonalInf seletcPersonalInf(Integer id);

    IndexVo selectIndex(Integer uid);
}