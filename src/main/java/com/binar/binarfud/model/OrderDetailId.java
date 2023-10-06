package com.binar.binarfud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class OrderDetailId implements Serializable {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "order_id")
    private String orderId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "product_code")
    private String productCode;
}
