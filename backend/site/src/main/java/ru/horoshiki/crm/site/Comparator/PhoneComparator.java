package ru.horoshiki.crm.site.Comparator;

import ru.horoshiki.crm.site.model.entity.Phone;

import java.util.Comparator;

/**
 * Created by onyushkindv on 12.12.2016.
 */
public class PhoneComparator  implements Comparator<Phone> {
    @Override
    public int compare(Phone p1, Phone p2) {
            return p1.getId().compareTo(p2.getId());
    }
}
