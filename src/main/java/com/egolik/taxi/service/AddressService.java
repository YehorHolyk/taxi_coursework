package com.egolik.taxi.service;

import com.egolik.taxi.dao.AddressDao;
import com.egolik.taxi.dao.GenericDao;
import com.egolik.taxi.entity.Address;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.TreeMap;

@AllArgsConstructor
public class AddressService implements GenericDao<Address> {

    private final AddressDao addressDao;

    public List<Integer> createAddresses(TreeMap<Integer,Address> addressMap){
        return addressDao.create(addressMap);
    }

    @Override
    public void create(Address entity) {

    }

    @Override
    public void delete(Address entity) {

    }

    @Override
    public Address findById(int id) {
        return null;
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public Address update(Address entity) {
        return null;
    }
}
