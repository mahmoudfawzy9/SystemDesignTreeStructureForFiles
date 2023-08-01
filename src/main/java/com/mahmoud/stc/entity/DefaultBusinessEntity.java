package com.mahmoud.stc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class DefaultBusinessEntity<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;

}
