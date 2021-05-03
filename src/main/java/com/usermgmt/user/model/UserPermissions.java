package com.usermgmt.user.model;

import com.usermgmt.user.dto.PermissionsDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "user_permissions")
public @Data class UserPermissions {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "permissionId")
    private UUID permissionId;
    private UUID id;
    private boolean toAdd;
    private boolean toModify;
    private boolean toDelete;
    private boolean toRead;

    public UserPermissions(PermissionsDTO permissionsDTO) {
        this.updatePermissions(permissionsDTO);
    }

    public void updatePermissions(PermissionsDTO permissionsDTO) {
        this.toAdd = permissionsDTO.isToAdd();
        this.toModify = permissionsDTO.isToModify();
        this.toDelete = permissionsDTO.isToDelete();
        this.toRead = permissionsDTO.isToRead();
    }
}
