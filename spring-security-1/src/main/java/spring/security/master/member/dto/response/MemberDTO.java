package spring.security.master.member.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String id;
    private String password;

    @Builder.Default
    private List<String> roleNames = new ArrayList<>();
}
