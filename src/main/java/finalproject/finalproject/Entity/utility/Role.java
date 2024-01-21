package finalproject.finalproject.Entity.utility;//package entity.user;
//
//
//import base.entity.BaseEntity;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = Role.TABLE_NAME)
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Role extends BaseEntity<Integer> {
//
//    public static final String TABLE_NAME = "roles";
//
//    public static final String NAME = "name";
//    public static final String ROLE_PERMISSIONS_JOIN_TABLE = "roles_permissions";
//
//    @Column(name = NAME)
//    private String name;
//
//    @ManyToMany
//    @JoinTable(name = ROLE_PERMISSIONS_JOIN_TABLE)
//    private Set<Permission> permissions = new HashSet<>();
//
//}