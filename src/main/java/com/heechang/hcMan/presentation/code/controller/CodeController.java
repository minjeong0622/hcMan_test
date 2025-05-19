package com.heechang.hcMan.presentation.code.controller;

import com.heechang.hcMan.domain.code.application.CodeService;
import com.heechang.hcMan.domain.code.entity.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @GetMapping
    public ResponseEntity<List<Code>> getCodes(@RequestParam String parent) {
        return ResponseEntity.ok(codeService.getCodesByParent(parent));
    }
}
