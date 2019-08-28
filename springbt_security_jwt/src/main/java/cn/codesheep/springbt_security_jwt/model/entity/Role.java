package cn.codesheep.springbt_security_jwt.model.entity;

/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public class Role {

    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
