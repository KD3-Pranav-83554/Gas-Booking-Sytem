package com.app.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    Long orderId;
    List<CylinderDTO> cylinders=new ArrayList<>();
    LocalDate orderDate;
    LocalDate ExpectedDeliveryDate;
    String orderStatus;
    Double orderTotal;
    String orderReply;
}
