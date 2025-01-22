package spring.security.master.member.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDTO {
    private String id;
    private String password;
    private Integer memberID;
}
