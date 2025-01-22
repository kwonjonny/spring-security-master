package spring.security.master.member.security.serivce;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import spring.security.master.member.dto.response.MemberDTO;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class CustomUserDetails extends User implements OAuth2User {

    private final MemberDTO member;

    public CustomUserDetails(MemberDTO member) {
        super(member.getId(), member.getPassword(), member.getRoleNames()
                .stream()
                .map(role -> new SimpleGrantedAuthority("role" + role))
                .collect(Collectors.toList()));
        this.member = member;
    }

    @Override
    public String getName() {
        return member.getId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "userId", member.getId(),
                "roleNames", member.getRoleNames()
        );
    }
}
