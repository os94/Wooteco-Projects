package com.woowacourse.dsgram.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity{

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    LocalDateTime renewTime;

}
