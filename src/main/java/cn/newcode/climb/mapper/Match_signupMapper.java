package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Match_signup;
import org.springframework.stereotype.Repository;

@Repository
public interface Match_signupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Match_signup record);

    int insertSelective(Match_signup record);

    Match_signup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Match_signup record);

    int updateByPrimaryKey(Match_signup record);

    int selectMatched(Integer mid);

    void updateSignUp(Match_signup match_signup);
}