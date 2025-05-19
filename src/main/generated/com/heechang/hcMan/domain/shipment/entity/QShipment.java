package com.heechang.hcMan.domain.shipment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShipment is a Querydsl query type for Shipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipment extends EntityPathBase<Shipment> {

    private static final long serialVersionUID = 1911753112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShipment shipment = new QShipment("shipment");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.heechang.hcMan.domain.order.entity.QOrderRequest order;

    public final DateTimePath<java.time.LocalDateTime> shippedDate = createDateTime("shippedDate", java.time.LocalDateTime.class);

    public final StringPath trackingNumber = createString("trackingNumber");

    public QShipment(String variable) {
        this(Shipment.class, forVariable(variable), INITS);
    }

    public QShipment(Path<? extends Shipment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShipment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShipment(PathMetadata metadata, PathInits inits) {
        this(Shipment.class, metadata, inits);
    }

    public QShipment(Class<? extends Shipment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.heechang.hcMan.domain.order.entity.QOrderRequest(forProperty("order"), inits.get("order")) : null;
    }

}

