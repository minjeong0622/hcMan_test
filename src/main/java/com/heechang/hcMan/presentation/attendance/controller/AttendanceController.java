package com.heechang.hcMan.presentation.attendance.controller;

import com.heechang.hcMan.domain.attendance.dto.AttendanceDTO;
import com.heechang.hcMan.domain.attendance.application.AttendanceService;
import com.heechang.hcMan.domain.attendance.repository.AttendanceSearchCond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping()
    public List<AttendanceDTO> searchAttendances(@RequestBody AttendanceSearchCond attendanceSearchCond) {
        logger.info("Search Attendances called with condition: {}", attendanceSearchCond);

        List<AttendanceDTO> attendances = attendanceService.findAll(attendanceSearchCond);

        logger.info("Found {} attendance records", attendances.size());
        return attendances;
    }

    @GetMapping("/{id}")
    public AttendanceDTO getAttendanceById(@PathVariable Integer id) {
        logger.info("Fetching Attendance by ID: {}", id);
        return attendanceService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAttendanceById(@PathVariable Integer id, @RequestBody AttendanceDTO attendanceDTO) {
        logger.info("Updating Attendance by ID: {}", id);
        attendanceService.updateAttendance(id, attendanceDTO);
        return ResponseEntity.ok("수정완료");
    }
}