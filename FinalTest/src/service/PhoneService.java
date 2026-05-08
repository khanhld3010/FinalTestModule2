package service;

import models.Phone;

import java.util.List;

public interface PhoneService {
    void addPhone(Phone phone) throws Exception;

    boolean deletePhone(int id);

    List<Phone> displayAllPhones();

    List<Phone> searchPhone(String keyword);
}
