package com.app.DTO;


import com.app.entities.Policy.CancellationPolicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CancellationPolicyDto {
    Integer days;
    Integer cancellationPercentage;
}
