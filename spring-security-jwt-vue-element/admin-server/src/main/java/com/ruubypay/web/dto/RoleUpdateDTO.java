package com.ruubypay.web.dto;

import lombok.Data;

@Data
public class RoleUpdateDTO {
    private Long id;
    private String name;
    private Boolean status;
}
