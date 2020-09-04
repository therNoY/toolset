package pers.mihao.toolset.dto;

import java.util.List;

/**
 * 返回的有便于现实的tree
 */
public class TreeDo<T> {

    protected Integer id;
    protected String label;

    protected List<T> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return label;
    }

    public void setName(String name) {
        this.label = name;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
