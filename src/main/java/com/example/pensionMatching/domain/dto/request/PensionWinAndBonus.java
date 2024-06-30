package com.example.pensionMatching.domain.dto.request;

import com.example.pensionMatching.domain.entity.PensionBonusNum;
import com.example.pensionMatching.domain.entity.PensionWinNum;

public record PensionWinAndBonus(
    PensionWinNum pensionWinNum,
    PensionBonusNum pensionBonusNum
) {

}
