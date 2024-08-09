package com.app.entities.Policy;

import com.app.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="cancellation_policy")
public class CancellationPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cancellation_policy_id")
    Long policyId;

    @Column(name="refund_deduc_percentage")
    Integer refundDeductionPercentage;

    @Column(name="allowed_days_after_order")
    Integer days;

}
