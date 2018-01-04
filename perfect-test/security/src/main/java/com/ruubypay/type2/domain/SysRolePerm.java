package com.ruubypay.type2.domain;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class SysRolePerm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private SysRole role;
    private SysPerm perm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysPerm getPerm() {
        return perm;
    }

    public void setPerm(SysPerm perm) {
        this.perm = perm;
    }
}
