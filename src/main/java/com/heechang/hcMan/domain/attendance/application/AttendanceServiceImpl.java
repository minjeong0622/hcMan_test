package com.heechang.hcMan.domain.attendance.application;

import com.heechang.hcMan.domain.attendance.dto.AttendanceDTO;
import com.heechang.hcMan.domain.attendance.entity.Attendance;
import com.heechang.hcMan.domain.attendance.repository.AttendanceRepository;
import com.heechang.hcMan.domain.attendance.repository.AttendanceSearchCond;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public List<AttendanceDTO> findAll(AttendanceSearchCond attendanceSearchCond) {
        List<Attendance> attendances = attendanceRepository.findAttendancesByCondition(attendanceSearchCond);

        return attendances.stream()
                .map(AttendanceDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceDTO findById(Integer id) {
        return attendanceRepository.findById(id)
                .map(AttendanceDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 데이터가 존재하지 않습니다: " + id));
    }

    @Override
    public void updateAttendance(Integer id, AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 존재하지 않습니다."));

        attendance.updateAttendance(attendanceDTO); // 변경 감지 발생

        attendanceRepository.save(attendance);
    }
}
