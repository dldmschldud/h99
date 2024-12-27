package com.h99.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kid;

    private boolean isUploadEnable = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User(Long kid){
        this.kid = kid;
        this.isUploadEnable=false;
        this.role= Role.USER;
    }

    public void changeRole(){
        this.role = Role.UPLOADER;
    }
}
