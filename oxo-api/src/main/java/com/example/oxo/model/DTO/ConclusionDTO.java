package com.example.oxo.model.DTO;

import com.example.oxo.model.enums.ConclusionType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConclusionDTO {
	private String message;
    private ConclusionType type;

    private ConclusionDTO(ConclusionDTOBuilder builder){
        this.message = builder.message;
        this.type = builder.type;
    }

    public static ConclusionDTOBuilder builder(){
        return new ConclusionDTOBuilder();
    }
    public static class ConclusionDTOBuilder {
        public ConclusionType type;
        public String message;

        public ConclusionDTOBuilder message(String message){
            this.message = message;
            return this;
        }

        public ConclusionDTOBuilder type(ConclusionType type){
            this.type = type;
            return this;
        }

        public ConclusionDTO build(){
            return new ConclusionDTO(this);
        }
    }

}
