package com.heechang.hcMan.domain.attendance.repository;

import com.heechang.hcMan.domain.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>, AttendanceRepositoryCustom {
}
