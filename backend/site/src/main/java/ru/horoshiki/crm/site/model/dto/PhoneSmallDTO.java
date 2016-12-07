package ru.horoshiki.crm.site.model.dto;

import ru.horoshiki.crm.site.model.entity.Phone;
import ru.horoshiki.crm.site.model.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onyushkindv on 07.12.2016.
 */
public class PhoneSmallDTO {
    private Long id;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static PhoneSmallDTO valueOf(Phone phone){
        PhoneSmallDTO phoneSmallDTO = new PhoneSmallDTO();
        phoneSmallDTO.setId(phone.getId());
        phoneSmallDTO.setPhone(phone.getPhone());
        return phoneSmallDTO;
    }

    public static List<PhoneSmallDTO> valueOf(List<Phone> phones){
        List phonesSmalDto = new ArrayList<>();
        for(Phone p: phones) {
            phonesSmalDto.add(valueOf(p));
        }
        return phonesSmalDto;
    }
}
