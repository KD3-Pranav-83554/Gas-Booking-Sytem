package com.app.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusAndOrderReplyDto {
    Long orderId;
    String orderReply;
    String orderStatus;
}
