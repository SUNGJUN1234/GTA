package com.jsj.GTA.domain.stamps;

import lombok.Getter;

import java.util.Date;

@Getter
public class StampsListResponseDto {
    private Long id;
    private String touristAttractionsId;
    private Long usersId;
    private String name;
    private String issueDate;
    private String expirationDate;

    public StampsListResponseDto(Stamps entity) {
        this.id = entity.getId();
        this.touristAttractionsId = entity.getTouristAttractionsId();
        this.usersId = entity.getUsersId();
        this.name = entity.getName();
        this.issueDate = entity.getIssueDate();
        this.expirationDate = entity.getExpirationDate();
    }
}
