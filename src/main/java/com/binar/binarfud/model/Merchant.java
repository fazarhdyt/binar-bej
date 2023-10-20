package com.binar.binarfud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "merchant")
public class Merchant implements Serializable {

    @Id
    @Column(unique = true)
    private String merchantCode;

    @NotBlank(message = "merchant name is required")
    private String merchantName;

    private String merchantLocation;
    private Boolean open;

    @OneToMany(mappedBy = "merchant")
    private List<Product> products;

}
