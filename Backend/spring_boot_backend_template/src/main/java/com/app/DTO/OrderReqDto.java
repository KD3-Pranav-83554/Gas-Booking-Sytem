package com.app.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderReqDto {
    Long userId;
    List<CylinderDTO> cylinderDTOList;
    String orderInstruction;
    Long paymentId;
    Long agencyId;
}
