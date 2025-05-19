package com.heechang.hcMan.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderApproval is a Querydsl query type for OrderApproval
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderApproval extends EntityPathBase<OrderApproval> {

    private static final long serialVersionUID = 936267821L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderApproval orderApproval = new QOrderApproval("orderApproval");

    public final NumberPath<Long> approvedBy = createNumber("approvedBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> approvedDate = createDateTime("approvedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrderRequest order;

    public QOrderApproval(String variable) {
        this(OrderApproval.class, forVariable(variable), INITS);
    }

    public QOrderApproval(Path<? extends OrderApproval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderApproval(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderApproval(PathMetadata metadata, PathInits inits) {
        this(OrderApproval.class, metadata, inits);
    }

    public QOrderApproval(Class<? extends OrderApproval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrderRequest(forProperty("order"), inits.get("order")) : null;
    }

}

