package pers.mihao.toolset.client.net.http.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装的
 *
 * @param <T>
 */
public class MapEntry<T> implements Serializable {
    protected String k;
    protected T v;

    public MapEntry() {
    }

    public MapEntry(String k, T v) {
        this.k = k;
        this.v = v;
    }

    public  static List<MapEntry<String>> mapToMapEntryList(Map<String, String> map){
        List<MapEntry<String>> mapEntries = null;
        if (map != null && map.size() > 0) {
            mapEntries = new ArrayList<>();
            List<MapEntry<String>> finalMapEntries = mapEntries;
            map.forEach((s, t)-> finalMapEntries.add(new MapEntry<>(s, t)));
        }
        return mapEntries;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public T getV() {
        return v;
    }

    public void setV(T v) {
        this.v = v;
    }
}
