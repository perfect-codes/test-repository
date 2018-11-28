package com.ruubypay.service.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleTransferBO {
    private Long key;
    private String label;
    private Boolean disabled;
}
