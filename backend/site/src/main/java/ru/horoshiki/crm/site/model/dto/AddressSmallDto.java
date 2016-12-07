package ru.horoshiki.crm.site.model.dto;

import ru.horoshiki.crm.site.model.entity.Address;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onyushkindv on 07.12.2016.
 */
public class AddressSmallDto {
    private Long id;

    private String address;

    private String intercom;

    private Integer storey;

    private Integer access;

    private Integer apartment;

    private String comment;

    public static AddressSmallDto valueOf(Address address){
        AddressSmallDto addressSmallDto = new AddressSmallDto();
        addressSmallDto.setId(address.getId());
        addressSmallDto.setAccess(address.getAccess());
        addressSmallDto.setAddress(address.getAddress());
        addressSmallDto.setApartment(address.getApartment());
        addressSmallDto.setComment(address.getComment());
        addressSmallDto.setIntercom(address.getIntercom());
        addressSmallDto.setStorey(address.getStorey());
        return addressSmallDto;
    }

    public static List<AddressSmallDto> valueOf(List<Address> addresses){
        List<AddressSmallDto> addressSmallDtos = new ArrayList<>();
        for(Address a: addresses){
            addressSmallDtos.add(valueOf(a));
        }
        return addressSmallDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntercom() {
        return intercom;
    }

    public void setIntercom(String intercom) {
        this.intercom = intercom;
    }

    public Integer getStorey() {
        return storey;
    }

    public void setStorey(Integer storey) {
        this.storey = storey;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
