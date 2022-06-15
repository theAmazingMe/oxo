package com.example.oxo.model.DTO;

import com.example.oxo.model.enums.ConclusionType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConclusionDTO {
	private String message;
    private ConclusionType type;

}
