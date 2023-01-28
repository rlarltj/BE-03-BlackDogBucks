package com.prgrms.bdbks.domain.star.dto;

import lombok.Getter;

@Getter
public class StarSearchResponse {

	private final Long starId;

	private final Long userId;

	private final short count;

	public StarSearchResponse(Long starId, Long userId, short count) {
		this.starId = starId;
		this.userId = userId;
		this.count = count;
	}
}