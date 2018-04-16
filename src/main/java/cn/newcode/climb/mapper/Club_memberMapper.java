package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Club_member;
import cn.newcode.climb.vo.ClubMemberVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Club_memberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Club_member record);

    int insertSelective(Club_member record);

    Club_member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Club_member record);

    int updateByPrimaryKey(Club_member record);

    /**
     * 分页查询俱乐部成员
     * @param cid
     * @return
     */
    List<ClubMemberVo> selectMember(@Param("startPos")Integer startPos, @Param("pageSize") Integer pageSize,
                                    @Param("cid")Integer cid,@Param("name") String name,@Param("uid")Integer uid);

    /**
     * 查询俱乐部成员总数
     * @param cid
     * @param name
     * @return
     */
    Integer selectCount(@Param("cid")Integer cid,@Param("name") String name);

    /**
     * 通过uid查询cid
     * @param id
     * @return
     */
    Integer selectClub(Integer id);

    /**
     * 退出俱乐部
     */
    void quiteClub(Integer uid);

    /**
     * 查询自己属于哪个俱乐部
     * @param uid
     * @return
     */
    Integer selectBelong(Integer uid);

    /**
     * 查询俱乐部成员
     * @param cid
     * @return
     */
    List<Club_member> selectClubMember(Integer cid);
}