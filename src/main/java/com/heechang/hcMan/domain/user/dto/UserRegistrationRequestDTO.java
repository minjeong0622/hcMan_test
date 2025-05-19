package com.heechang.hcMan.domain.user.dto;

import com.heechang.hcMan.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequestDTO {
    @NotBlank(message = "사업자등록번호를 입력해주세요")
    private String businessRegNumber;
    @NotBlank(message = "협력사 유형을 선택해주세요")
    private String partnerType;
    @NotBlank(message = "국가를 선택해주세요")
    private String country;
    @NotBlank(message = "내외자 구분을 선택해주세요")
    private String domesticOrForeign;
    @NotBlank(message = "사업자 유형을 선택해주세요")
    private String businessType;
    @NotBlank(message = "회사명을 입력해주세요")
    private String companyName;
    private String corporateRegNumber;
    @NotBlank(message = "회사 규모를 선택해주세요")
    private String companySize;
    @NotBlank(message = "대표자 성명을 입력해주세요")
    private String ceoName;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String contactEmail;

    public User toEntity() {
        return User.builder()
                .businessRegNumber(this.businessRegNumber)
                .partnerType(this.partnerType)
                .country(this.country)
                .domesticOrForeign(this.domesticOrForeign)
                .businessType(this.businessType)
                .companyName(this.companyName)
                .corporateRegNumber(this.corporateRegNumber)
                .companySize(this.companySize)
                .ceoName(this.ceoName)
                .contactEmail(this.contactEmail)
                .build();
    }
}