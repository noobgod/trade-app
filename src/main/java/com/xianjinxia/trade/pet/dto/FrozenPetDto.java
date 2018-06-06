package com.xianjinxia.trade.pet.dto;

/**
 * Created by Myron on 2018/2/28.
 */
public class FrozenPetDto {
    private Long userId;
    private Long  id;

    public FrozenPetDto(Long userId, Long id) {
        this.userId = userId;
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
