package spring.security.master.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.security.master.member.dto.request.CreateMemberDTO;
import spring.security.master.member.dto.request.CreateRoleDTO;
import spring.security.master.member.dto.response.MemberDTO;

@Mapper
public interface MemberMapper {
    MemberDTO findMemberById(String id);
    void createMember(CreateMemberDTO requestDTO);
    void createRole(CreateRoleDTO requestDTO);
}
