package pers.mihao.toolset.pansousouTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.mihao.toolset.music.seed.controller.SourceController;
import pers.mihao.toolset.music.seed.dto.ResourceDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetSourceTest {

    @Autowired
    SourceController sourceController;

    @Test
    public void testSourceController() throws IOException {
        List<ResourceDTO> resourceDTOS = sourceController.findResourceListByName("纸短情长");
        List<ResourceDTO> resourceDTOS1 = new ArrayList<>();
        resourceDTOS.forEach(resourceDTO -> {
            try {
                ResourceDTO resourceDTO1 = sourceController.getRealUrl(resourceDTO.getUrl());
                resourceDTOS1.add(resourceDTO1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println(resourceDTOS1.size());

    }
}
