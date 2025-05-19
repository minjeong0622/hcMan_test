package com.heechang.hcMan.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_USERS_BUSINESS_REG_NUMBER", columnNames = "business_reg_number"),
                @UniqueConstraint(name = "UK_USERS_LOGIN_ID", columnNames = "login_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*** 권한 (ROLE_USER 등) ***/
    @Column(name = "role", columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String role;

    /*** 로그인 아이디 (최초에는 null, 이후 시스템에서 할당) ***/
    @Column(name = "login_id", columnDefinition = "VARCHAR(50)", length = 50, unique = true, nullable = true)
    private String loginId;

    /*** 비밀번호 (최초에는 자동 생성, 이후 변경 가능) ***/
    @Column(name = "password", columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String password;

    /*** 필수 기본 정보 ***/
    @Column(name = "partner_type", columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String partnerType;

    @Column(name = "country", columnDefinition = "VARCHAR(50)", length = 50, nullable = false)
    private String country;

    @Column(name = "domestic_or_foreign", columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String domesticOrForeign;

    @Column(name = "business_type", columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String businessType;

    @Column(name = "company_name", columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String companyName;

    @Column(name = "contact_email", columnDefinition = "VARCHAR(100)", length = 100, nullable = false)
    private String contactEmail;

    /*** 사업자등록번호: 로그인 임시 ID로 사용 (중복 방지) ***/
    @Column(name = "business_reg_number", columnDefinition = "VARCHAR(20)", length = 20, unique = true, nullable = false)
    private String businessRegNumber;

    /*** 법인등록번호 (선택) ***/
    @Column(name = "corporate_reg_number", columnDefinition = "VARCHAR(20)", length = 20)
    private String corporateRegNumber;

    @Column(name = "company_size", columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String companySize;

    @Column(name = "ceo_name", columnDefinition = "VARCHAR(50)", length = 50, nullable = false)
    private String ceoName;

    /*** 업태/종목 ***/
    @Column(name = "industry_type", columnDefinition = "VARCHAR(50)", length = 50)
    private String industryType;

    @Column(name = "industry", columnDefinition = "VARCHAR(50)", length = 50)
    private String industry;

    /*** 주소 ***/
    @Column(name = "zip_code", columnDefinition = "VARCHAR(6)", length = 6)
    private String zipCode;

    @Column(name = "address_line1", columnDefinition = "VARCHAR(200)", length = 200)
    private String addressLine1;

    @Column(name = "address_line2", columnDefinition = "VARCHAR(200)", length = 200)
    private String addressLine2;

    /*** 기타 ***/
    @Column(name = "homepage", columnDefinition = "VARCHAR(200)", length = 200)
    private String homepage;

    @Column(name = "handling_items", columnDefinition = "VARCHAR(500)", length = 500)
    private String handlingItems;

    @Column(name = "note", columnDefinition = "VARCHAR(1000)", length = 1000)
    private String note;

    /*** 은행계좌 ***/
    @Column(name = "bank_name", columnDefinition = "VARCHAR(50)", length = 50)
    private String bankName;

    @Column(name = "account_holder", columnDefinition = "VARCHAR(50)", length = 50)
    private String accountHolder;

    @Column(name = "account_number", columnDefinition = "VARCHAR(50)", length = 50)
    private String accountNumber;

    /*** 파일 업로드 경로 ***/
    @Column(name = "bank_book_file_path", columnDefinition = "VARCHAR(255)", length = 255)
    private String bankBookFilePath;

    @Column(name = "business_reg_file_path", columnDefinition = "VARCHAR(255)", length = 255)
    private String businessRegFilePath;

    /*** 담당자 목록 ***/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<ContactPerson> contactPersons;
}
