package per.codeant.jqgrid;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private String id; // 节点ID，对加载远程数据很重要。
    private String text; // 显示节点文本。
    private String state; // 节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
    private boolean checked; // 表示该节点是否被选中。
    private String parentId;
    private List<TreeNode> children; // 一个节点数组声明了若干节点。

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void addChildren(TreeNode node) {
        if (null == children) children = new ArrayList<>();
        children.add(node);
    }
}
