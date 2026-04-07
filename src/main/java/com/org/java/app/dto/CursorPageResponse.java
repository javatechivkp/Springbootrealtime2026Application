package com.org.java.app.dto;

import java.util.List;

public record CursorPageResponse<T>(List<T> data,
	        int pageSize,
	        Integer nextCursor,
	        boolean hasNext)
{
}
