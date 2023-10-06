package com.binar.binarfud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String merchantName;
    private String merchantLocation;
    private boolean open;

    @OneToMany(mappedBy = "merchant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> products;
}
