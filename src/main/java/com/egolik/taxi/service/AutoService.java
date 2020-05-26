package com.egolik.taxi.service;

import com.egolik.taxi.dao.AutoDao;
import com.egolik.taxi.dao.GenericDao;
import com.egolik.taxi.entity.Auto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AutoService implements GenericDao<Auto> {

    private final AutoDao autoDao;

    public List<Auto> takeAutos(){
        return autoDao.getAllAuto();
    }

    public void deleteAuto(String licensePlate) {autoDao.deleteAutoByLicensePlate(licensePlate);}

    public Auto takeAuto(String licensePlate){
        return autoDao.getByLicensePlate(licensePlate);
    }

    @Override
    public void create(Auto entity) {
        autoDao.create(entity);
    }

    @Override
    public void delete(Auto entity) {

    }

    @Override
    public Auto findById(int id) {
        return null;
    }

    @Override
    public List<Auto> findAll() {
        return autoDao.getAllAuto();
    }

    @Override
    public Auto update(Auto entity) {
        return null;
    }
}
