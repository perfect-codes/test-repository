package com.ruubypay.type2.service;

import com.ruubypay.type2.domain.SysUser;

public interface SysUserService {
    SysUser findByUsername(String username);
}
