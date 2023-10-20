package com.binar.binarfud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(unique = true)
    private String productCode;

    @NotBlank(message = "product name is required")
    private String productName;

    private Integer stock;

    @NotNull
    private Double price;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "merchant_code")
    private Merchant merchant;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}
