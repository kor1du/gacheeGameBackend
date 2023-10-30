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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(name = "gachee_id", nullable = false)
    private Long gacheeId; //멤버 PK키

    @Column(name = "user_id", nullable = false)
    private Long userId; //카카오 회원번호

    @Column(name = "name", nullable = false)
    private String name; //이름

    @Column(name = "gender", nullable = false)
    private String gender; //성별

    @Column(name = "profile_image", nullable = true)
    private String profileImage; //프로필 이미지 경로

    @Column(name = "social", nullable = false)
    private String social; //소셜 로그인 종류 (ex) kakao, google, naver, ...etc)

    @Column(name = "is_first")
    private boolean isFirst; //처음 가입 여부

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
