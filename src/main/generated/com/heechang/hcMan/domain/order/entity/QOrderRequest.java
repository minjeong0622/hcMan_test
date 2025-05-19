package com.heechang.hcMan.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderRequest is a Querydsl query type for OrderRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderRequest extends EntityPathBase<OrderRequest> {

    private static final long serialVersionUID = 533472101L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderRequest orderRequest = new QOrderRequest("orderRequest");

    public final com.heechang.hcMan.domain.user.entity.QUser buyer;

    public final DatePath<java.time.LocalDate> dueDate = createDate("dueDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath note = createString("note");

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final StringPath orderNo = createString("orderNo");

    public final EnumPath<OrderStatus> preCancelStatus = createEnum("preCancelStatus", OrderStatus.class);

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    public final com.heechang.hcMan.domain.partner.entity.QPartner supplier;

    public final StringPath warehouseLocation = createString("warehouseLocation");

    public QOrderRequest(String variable) {
        this(OrderRequest.class, forVariable(variable), INITS);
    }

    public QOrderRequest(Path<? extends OrderRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderRequest(PathMetadata metadata, PathInits inits) {
        this(OrderRequest.class, metadata, inits);
    }

    public QOrderRequest(Class<? extends OrderRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.heechang.hcMan.domain.user.entity.QUser(forProperty("buyer")) : null;
        this.supplier = inits.isInitialized("supplier") ? new com.heechang.hcMan.domain.partner.entity.QPartner(forProperty("supplier")) : null;
    }

}

