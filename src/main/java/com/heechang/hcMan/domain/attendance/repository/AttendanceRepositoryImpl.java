package com.heechang.hcMan.domain.attendance.repository;

import static com.heechang.hcMan.domain.attendance.entity.QAttendance.attendance;

import com.heechang.hcMan.domain.attendance.entity.Attendance;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.util.StringUtils;

public class AttendanceRepositoryImpl implements AttendanceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public AttendanceRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Attendance> findAttendancesByCondition(AttendanceSearchCond attendanceSearchCond) {
        return queryFactory
                .select(attendance)
                .from(attendance)
                .where(
                        likeAttendanceEmployeeName(attendanceSearchCond.getEmployeeName()),
                        likeAttendanceDate(attendanceSearchCond.getDate())
                )
                .fetch();
    }

    private BooleanExpression likeAttendanceEmployeeName(String employeeName) {
        return StringUtils.hasText(employeeName) ? attendance.employeeName.like("%" + employeeName + "%") : null;
    }

    private BooleanExpression likeAttendanceDate(String date) {
        return StringUtils.hasText(date) ? attendance.date.like("%" + date + "%") : null;
    }
}
