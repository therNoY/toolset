package pers.mihao.toolset.music.music163.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.mihao.toolset.music.music163.dto.Music163;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author mihao
 * @since 2019-08-10
 */
public interface Music163Service extends IService<Music163> {

    /**
     *
     * @param randomId
     * @return
     */
    Music163 getRandomMusic(double randomId);

    Music163 getRandomMusicFormDb();
}
