package com.heechang.hcMan.domain.attendance.entity;

import com.heechang.hcMan.domain.attendance.dto.AttendanceDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "bs01")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 PK
    @Column(name = "bs01_key")
    private Integer id; // 기본 키

    @Column(name = "bs01_bcod", nullable = false, length = 5)
    private String departmentCode; // 부서 코드

    @Column(name = "bs01_bona", nullable = false, length = 50)
    private String departmentName; // 부서 이름

    @Column(name = "bs01_scod", nullable = false, length = 6)
    private String employeeCode; // 직원 코드

    @Column(name = "bs01_sona", nullable = false, length = 50)
    private String employeeName; // 직원 이름

    @Column(name = "bs01_ccod", nullable = false, length = 5)
    private String positionCode; // 직급 코드

    @Column(name = "bs01_cona", nullable = false, length = 50)
    private String positionName; // 직급 이름

    @Column(name = "bs01_date", nullable = false, length = 8)
    private String date; // 날짜 (yyyyMMdd 형식)

    @Column(name = "bs01_stis", nullable = false)
    private Integer startHour; // 시작 시간(시)

    @Column(name = "bs01_stib", nullable = false)
    private Integer startMinute; // 시작 시간(분)

    @Column(name = "bs01_etis", nullable = false)
    private Integer endHour; // 종료 시간(시)

    @Column(name = "bs01_etib", nullable = false)
    private Integer endMinute; // 종료 시간(분)

    @Column(name = "bs01_stisn", nullable = false, length = 10)
    private String startHourStr; // 시작 시간(시)

    @Column(name = "bs01_stibn", nullable = false, length = 10)
    private String startMinuteStr; // 시작 시간(분)

    @Column(name = "bs01_etisn", nullable = false, length = 10)
    private String endHourStr; // 종료 시간(시)

    @Column(name = "bs01_etibn", nullable = false, length = 10)
    private String endMinuteStr; // 종료 시간(분)

    @Column(name = "bs01_in01", length = 100)
    private String in01; // 업무 설명 1

    @Column(name = "bs01_in02", length = 100)
    private String in02; // 업무 설명 2

    @Lob
    @Column(name = "bs01_in03")
    private String in03; // 업무 상세 설명 1

    @Lob
    @Column(name = "bs01_in04")
    private String in04; // 업무 상세 설명 2

    @Lob
    @Column(name = "bs01_in05")
    private String in05; // 업무 상세 설명 3

    @Column(name = "bs01_bdat", nullable = false, length = 8)
    private String baseDate; // 기준 날짜

    @Column(name = "bs01_time", nullable = false, length = 20)
    private String time; // 시간 정보

    @Column(name = "bs01_user", length = 20)
    private String user; // 신청자

    @Column(name = "bs01_dpdo", length = 200)
    private String documentPath; // 문서 경로

    // 기본 생성자
    public Attendance() {}

    // 생성자
    public Attendance(
            String companyCode, String departmentName, String employeeCode, String employeeName,
            String positionCode, String positionName, String date, Integer startHour, Integer startMinute,
            Integer endHour, Integer endMinute, String startHourStr, String startMinuteStr,
            String endHourStr, String endMinuteStr, String in01, String in02, String in03,
            String in04, String in05, String baseDate, String time, String user, String documentPath
    ) {
        this.departmentCode = companyCode;
        this.departmentName = departmentName;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.positionCode = positionCode;
        this.positionName = positionName;
        this.date = date;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.startHourStr = startHourStr;
        this.startMinuteStr = startMinuteStr;
        this.endHourStr = endHourStr;
        this.endMinuteStr = endMinuteStr;
        this.in01 = in01;
        this.in02 = in02;
        this.in03 = in03;
        this.in04 = in04;
        this.in05 = in05;
        this.baseDate = baseDate;
        this.time = time;
        this.user = user;
        this.documentPath = documentPath;
    }

    public void updateAttendance(AttendanceDTO attendanceDTO) {
        this.departmentName = attendanceDTO.getDepartmentName();
        this.employeeName = attendanceDTO.getEmployeeName();
        this.positionName = attendanceDTO.getPositionName();
        this.date = attendanceDTO.getDate();
        this.startHourStr = attendanceDTO.getStartHourStr();
        this.startMinuteStr = attendanceDTO.getStartMinuteStr();
        this.endHourStr = attendanceDTO.getEndHourStr();
        this.endMinuteStr = attendanceDTO.getEndMinuteStr();
        this.in01 = attendanceDTO.getIn01();
    }

}
