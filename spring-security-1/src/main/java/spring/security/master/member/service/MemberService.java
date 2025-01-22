package spring.security.master.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.master.member.dto.request.CreateMemberDTO;
import spring.security.master.member.dto.request.CreateRoleDTO;
import spring.security.master.member.mapper.MemberMapper;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private static final String USER = "USER";
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    @Transactional
    public Integer createMember(@NotNull final CreateMemberDTO requestDTO) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        memberMapper.createRole(CreateRoleDTO.builder()
                .roleName(USER)
                .id(requestDTO.getId())
                .build());
        memberMapper.createMember(requestDTO);
        return requestDTO.getMemberID();
    }
}
