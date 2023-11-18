package com.jsj.GTA.domain.stamps;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class StampsSaveRequestDto {

    // Entity 클래스가 변경되면 여러 클래스에 영향을 끼치기 때문에
    // Controller 에서 쓸 Dto 분리

    private String touristAttractionsId;
    private Long usersId;
    private String name;
    private String  issueDate = new Date().toString();
    private String  expirationDate;

    @Builder
    public StampsSaveRequestDto(String touristAttractionsId, Long usersId, String name, String issueDate, String expirationDate) {
        this.touristAttractionsId = touristAttractionsId;
        this.usersId = usersId;
        this.name = name;
//        this.issueDate = ;
        this.expirationDate = expirationDate;
    }

    public Stamps toEntity() {
        return Stamps.builder()
                .touristAttractionsId(touristAttractionsId)
                .usersId(usersId)
                .name(name)
                .issueDate(issueDate)
                .build();
    }
}
