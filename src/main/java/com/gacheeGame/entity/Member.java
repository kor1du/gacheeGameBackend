package com.gacheeGame.entity;

import com.gacheeGame.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId; //PK

    @Column(name = "o_auth_id", nullable = false, unique = true)
    private Long oAuthId; //카카오 회원번호

    @Column(name = "name", nullable = false)
    private String name; //이름

    @Column(name = "email", nullable = false)
    private String email; //이메일

    @Column(name = "gender", nullable = false)
    private String gender; //성별

    @Column(name = "profile_image")
    private String profileImage; //프로필 이미지 경로

    @Column(name = "social", nullable = false)
    private String social; //소셜 로그인 종류 (ex) kakao, google, naver, ...etc)

    @Column(name = "is_first_time")
    private boolean isFirstTime; //처음 가입 여부

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role; //권한 종류

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
