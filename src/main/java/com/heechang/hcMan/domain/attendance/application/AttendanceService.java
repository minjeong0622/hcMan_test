package com.heechang.hcMan.domain.attendance.application;

import com.heechang.hcMan.domain.attendance.dto.AttendanceDTO;
import com.heechang.hcMan.domain.attendance.repository.AttendanceSearchCond;
import java.util.List;

public interface AttendanceService {
    List<AttendanceDTO> findAll(AttendanceSearchCond attendanceSearchCond);
    AttendanceDTO findById(Integer id);
    void updateAttendance(Integer id, AttendanceDTO attendanceDTO);
}
