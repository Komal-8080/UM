package com.usermgmt.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PermissionsDTO {

    private boolean toAdd;
    private boolean toModify;
    private boolean toDelete;
    private boolean toRead;
}
