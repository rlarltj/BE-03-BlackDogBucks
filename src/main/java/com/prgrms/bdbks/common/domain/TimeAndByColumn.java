package com.prgrms.bdbks.common.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class TimeAndByColumn extends AbstractTimeColumn {

	@Column(name = "created_by", updatable = false)
	@CreatedBy
	private Long createdBy;

	@Column(name = "modified_by")
	@LastModifiedBy
	private Long modifiedBy;

}
