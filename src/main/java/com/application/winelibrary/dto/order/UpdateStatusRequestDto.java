package com.application.winelibrary.dto.order;

import com.application.winelibrary.entity.Order;

public record UpdateStatusRequestDto(Order.Status status) {
}
