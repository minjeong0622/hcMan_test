package com.heechang.hcMan.domain.attendance.dto;

import com.heechang.hcMan.domain.attendance.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceDTO {

    private Integer id;
    private String employeeName;
    private String departmentName;
    private String positionName;
    private String date;
    private String startHourStr;
    private String startMinuteStr;
    private String endHourStr;
    private String endMinuteStr;
    private String in01;

    public static AttendanceDTO from(Attendance attendance) {
        return new AttendanceDTO(
                attendance.getId(),
                attendance.getEmployeeName(),
                attendance.getDepartmentName(),
                attendance.getPositionName(),
                attendance.getDate(),
                attendance.getStartHourStr(),
                attendance.getStartMinuteStr(),
                attendance.getEndHourStr(),
                attendance.getEndMinuteStr(),
                attendance.getIn01()
        );
    }
}
