package com.heechang.hcMan.domain.code.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "common_code")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Code {

    @Id
    @Column(name="code_id", length=20)
    private String codeId;  // 예: "UNIT_EA"

    @Column(name="parent_code", length=20)
    private String parentCode; // 예: "UNIT"

    @Column(name="code_name", length=100, nullable=false)
    private String codeName;   // 예: "EA"

    @Column(name="use_yn", length=1)
    private String useYn;      // 'Y'/'N'
}
