package com.balt.database_writer.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "handling_unit_queue")
public class HandlingUnit{

    @EmbeddedId
    private HandlingUnitId handlingUnitId;

    @Column
    double quan;

    @Column
    long qdat;

    @Column
    @Nullable
    long idat;

    @Column
    @Convert(converter = StatusConverter.class)
    HandlingUnitStatus status;

}