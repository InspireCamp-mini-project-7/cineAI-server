package com.amcamp.cineAI.domain.member.application;

import com.amcamp.cineAI.domain.member.domain.Member;
import com.amcamp.cineAI.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.cineAI.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberUtil memberUtil;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo() {
        Member currentMember = memberUtil.getCurrentMember();
        return MemberInfoResponse.of(currentMember);
    }
}
