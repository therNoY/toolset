package pers.mihao.toolset.common.fileUpload.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pers.mihao.toolset.common.fileUpload.IUploadService;
import pers.mihao.toolset.common.fileUpload.vo.Chunk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * @author vi
 * @since 2019/5/9 8:37 PM
 */
@Service
@Async
public class UploadServiceImpl implements IUploadService {


    private static final String mergeFolder = "D:/data/merge";

    private Map<String, Set<Integer>> mapService = new HashMap<>();


    @Override
    public Map<String, Object> checkChunkExits(Chunk chunk) {
        Map<String, Object> res = new HashMap<>();
        String identifier = chunk.getIdentifier();
        if (mapService.containsKey(identifier)) {
            Set<Integer> chunkNumber = mapService.get(identifier);
            res.put("chunkNumbers",chunkNumber);
        }
        return res;
    }

    @Override
    public Integer saveChunk(Integer chunkNumber, String identifier) {
        Set<Integer> oldChunkNumber = mapService.get(identifier);
        if (Objects.isNull(oldChunkNumber)) {
            Set<Integer> newChunkNumber = new HashSet<>();
            newChunkNumber.add(chunkNumber);
            mapService.put(identifier, newChunkNumber);
            return newChunkNumber.size();
        } else {
            oldChunkNumber.add(chunkNumber);
            mapService.put(identifier, oldChunkNumber);
            return oldChunkNumber.size();
        }

    }

    @Override
    public void mergeFile(String fileName, String chunkFolder) {
        try {
            if (!Files.isWritable(Paths.get(mergeFolder))) {
                Files.createDirectories(Paths.get(mergeFolder));
            }
            String target = mergeFolder + File.separator + fileName;
            Files.createFile(Paths.get(target));
            Files.list(Paths.get(chunkFolder))
                    .filter(path -> path.getFileName().toString().contains("-"))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(target), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
