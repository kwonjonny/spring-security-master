package spring.security.master.member.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleDTO {
    private Integer roleID;
    private String id;
    private String roleName;
}
