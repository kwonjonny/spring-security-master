package spring.security.master.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.security.master.exception.handler.APIResponseDTO;
import spring.security.master.member.dto.request.CreateMemberDTO;
import spring.security.master.member.service.MemberService;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/management/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<APIResponseDTO<String>> createMember(@RequestBody CreateMemberDTO requestDTO) {
        final Integer createResult = memberService.createMember(requestDTO);
        return APIResponseDTO.ofSuccessCreateResponse(createResult > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
