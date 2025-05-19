package com.heechang.hcMan.domain.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttendance is a Querydsl query type for Attendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendance extends EntityPathBase<Attendance> {

    private static final long serialVersionUID = 2096216374L;

    public static final QAttendance attendance = new QAttendance("attendance");

    public final StringPath baseDate = createString("baseDate");

    public final StringPath date = createString("date");

    public final StringPath departmentCode = createString("departmentCode");

    public final StringPath departmentName = createString("departmentName");

    public final StringPath documentPath = createString("documentPath");

    public final StringPath employeeCode = createString("employeeCode");

    public final StringPath employeeName = createString("employeeName");

    public final NumberPath<Integer> endHour = createNumber("endHour", Integer.class);

    public final StringPath endHourStr = createString("endHourStr");

    public final NumberPath<Integer> endMinute = createNumber("endMinute", Integer.class);

    public final StringPath endMinuteStr = createString("endMinuteStr");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath in01 = createString("in01");

    public final StringPath in02 = createString("in02");

    public final StringPath in03 = createString("in03");

    public final StringPath in04 = createString("in04");

    public final StringPath in05 = createString("in05");

    public final StringPath positionCode = createString("positionCode");

    public final StringPath positionName = createString("positionName");

    public final NumberPath<Integer> startHour = createNumber("startHour", Integer.class);

    public final StringPath startHourStr = createString("startHourStr");

    public final NumberPath<Integer> startMinute = createNumber("startMinute", Integer.class);

    public final StringPath startMinuteStr = createString("startMinuteStr");

    public final StringPath time = createString("time");

    public final StringPath user = createString("user");

    public QAttendance(String variable) {
        super(Attendance.class, forVariable(variable));
    }

    public QAttendance(Path<? extends Attendance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttendance(PathMetadata metadata) {
        super(Attendance.class, metadata);
    }

}

