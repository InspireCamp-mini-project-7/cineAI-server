package com.amcamp.cineAI.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String profileImageUrl;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(
            String nickname,
            String profileImageUrl,
            MemberStatus status,
            String email,
            MemberRole role) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public static Member createMember(String nickname, String profileImageUrl, String email) {
        return Member.builder()
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .email(email)
                .status(MemberStatus.NORMAL)
                .role(MemberRole.USER)
                .build();
    }

    public void reEnroll() {
        this.status = MemberStatus.NORMAL;
    }
}
