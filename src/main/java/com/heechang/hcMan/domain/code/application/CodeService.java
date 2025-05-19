package com.heechang.hcMan.domain.code.application;

import com.heechang.hcMan.domain.code.entity.Code;
import com.heechang.hcMan.domain.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public List<Code> getCodesByParent(String parent) {
        return codeRepository.findByParentCode(parent);
    }
}
