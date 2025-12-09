package com.gotbufffetleh.backend.dbTables.idClasses;


import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class MenuId implements Serializable {

    private String menuName;
    private int catererId;

}
