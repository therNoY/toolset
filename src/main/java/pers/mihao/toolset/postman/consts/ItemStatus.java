package pers.mihao.toolset.postman.consts;


/**
 * postManItem状态
 */
public interface ItemStatus {

    int EDIT_HISTORY = 0; // 之前处于编辑状态被修改

    int EDIT = 1; // 只有编辑

    int SAVE_AND_EDIT = 2; // 以保存在编辑

    int SAVE = 3; // 已经保存

}
