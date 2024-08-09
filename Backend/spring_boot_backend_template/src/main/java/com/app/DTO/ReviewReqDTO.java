package com.app.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewReqDTO {
    Long orderId;
    Double reviewPoints;
    String reviewText;
}
