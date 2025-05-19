package com.heechang.hcMan.domain.inspection.application;

import com.heechang.hcMan.domain.inspection.entity.Inspection;
import com.heechang.hcMan.domain.inspection.repository.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InspectionService {

    private final InspectionRepository inspectionRepository;

    //검수 목록 조회
    @Transactional(readOnly = true)
    public List<Inspection> getAllInspections() {
        return inspectionRepository.findAll();
    }

    //단건 검수 조회
    @Transactional(readOnly = true)
    public Inspection getInspection(Long inspectionId) {
        return inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new IllegalArgumentException("Inspection not found"));
    }

    // 검수 수정
    public Inspection updateInspection(Long inspectionID, Inspection updatedData) {
        Inspection inspection = getInspection(inspectionID);
        // 수정 가능한 필드를 업데이트합니다.
        if (updatedData.getInspectionDate() != null) {
            inspection.setInspectionDate(updatedData.getInspectionDate());
        }
        if (updatedData.getInspectionResult() != null) {
            inspection.setInspectionResult(updatedData.getInspectionResult());
        }
        return inspectionRepository.save(inspection);
    }
}
