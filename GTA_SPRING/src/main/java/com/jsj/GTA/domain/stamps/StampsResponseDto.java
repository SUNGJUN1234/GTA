package com.jsj.GTA.domain.stamps;

import lombok.Getter;

@Getter
public class StampsResponseDto {

    private Long id;
    private String touristAttractionsId;
    private Long usersId;
    private String name;
    private String issueDate;
    private String expirationDate;

    public StampsResponseDto(Stamps entity) {
        this.id = entity.getId();
        this.touristAttractionsId = entity.getTouristAttractionsId();
        this.usersId = entity.getUsersId();
        this.name = entity.getName();
        this.issueDate = entity.getIssueDate();
        this.expirationDate = entity.getExpirationDate();
    }
}
