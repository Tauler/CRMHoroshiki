package ru.horoshiki.crm.site.Comparator;

import ru.horoshiki.crm.site.model.entity.Address;

import java.util.Comparator;

/**
 * Created by onyushkindv on 12.12.2016.
 */
public class AddressComparator  implements Comparator<Address> {
    @Override
    public int compare(Address a1, Address a2) {
        return a1.getId().compareTo(a2.getId());
    }
}