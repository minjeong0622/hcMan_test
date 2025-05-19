package com.heechang.hcMan.domain.attendance.repository;

import com.heechang.hcMan.domain.attendance.entity.Attendance;
import java.util.List;

public interface AttendanceRepositoryCustom {
    List<Attendance> findAttendancesByCondition(AttendanceSearchCond attendanceSearchCond);
}
