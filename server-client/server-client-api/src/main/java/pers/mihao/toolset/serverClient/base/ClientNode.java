package pers.mihao.toolset.serverClient.base;

import pers.mihao.toolset.dto.TreeDo;

public class ClientNode extends TreeDo<ClientNode> {

    private String icon;
    private boolean isRoot;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

}
