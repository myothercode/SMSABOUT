package com.domainVO;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-12
 * Time: 下午11:56
 * To change this template use File | Settings | File Templates.
 */
public class SessionVo {
    private Long id;
    private String name;
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
