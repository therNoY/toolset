package pers.mihao.toolset.JSONObjectTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import pers.mihao.toolset.common.vo.test_dto.Dog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class JsonObjectTest {

    @Test
    public void ListTest() {

        // LIST -> JSONString
        List<Dog> dogList = Dog.getList();
        String mes = JSONObject.toJSONString(dogList);

        // JSONString -> JSONObject
        Object o = JSONObject.parse(mes);

        System.out.println();
    }

    @Test
    public void ObjectTest() {
        Dog dog = new Dog(1, null);

        Map<String, Object> map = new HashMap<>();

        map.put("a", null);
        // Object -> JsonString

        String a = JSONObject.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);

        String jsonString = JSON.toJSONString(dog);
        // Object -> JsonObject
        JSONObject jsonObject = (JSONObject) JSON.toJSON(dog);

        // JsonObject -> JsonString
        String jsonString2 = jsonObject.toJSONString();
        // JsonObject -> Object
        Dog dog2 = JSON.toJavaObject(jsonObject, Dog.class);

        // JsonString -> JsonObject
        JSONObject jsonObject2 = JSONObject.parseObject(jsonString2);
        // JSONString -> Object
        Dog dog3 = JSON.parseObject(jsonString2, Dog.class);
    }

    @Test
    public void name() {
        Map < String , Object > jsonMap = new HashMap< String , Object>();
        jsonMap.put("a",1);
        jsonMap.put("b","");
        jsonMap.put("c",null);
        jsonMap.put("d","wuzhuti.cn");

        String str = JSONObject.toJSONString(jsonMap,SerializerFeature.WriteNullStringAsEmpty);
        System.out.println(str);
//输出结果:{"a":1,"b":"","c":"","d":"wuzhuti.cn"}
    }
}
