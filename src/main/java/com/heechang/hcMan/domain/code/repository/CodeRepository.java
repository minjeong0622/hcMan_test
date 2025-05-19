package com.heechang.hcMan.domain.code.repository;

import com.heechang.hcMan.domain.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, String> {

    List<Code> findByParentCode(String parentCode);
}
