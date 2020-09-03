package pers.mihao.toolset.music.ntes.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import pers.mihao.toolset.music.ntes.dto.Music163;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
public interface Music163Dao extends BaseMapper<Music163> {


    @Select("SELECT * FROM music163 ORDER BY RAND() LIMIT 1")
    Music163 selectRandom();
}
