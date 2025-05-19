package com.heechang.hcMan.domain.attendance.repository;

import lombok.Getter;

@Getter
public class AttendanceSearchCond {

    private String employeeName;
    private String date;

    public AttendanceSearchCond() {
    }

    public AttendanceSearchCond(String employeeName, String date) {
        this.employeeName = employeeName;
        this.date = date;
    }
}
