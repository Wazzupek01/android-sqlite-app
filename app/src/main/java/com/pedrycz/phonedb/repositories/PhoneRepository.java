package com.pedrycz.phonedb.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.pedrycz.phonedb.DAO.PhoneDao;
import com.pedrycz.phonedb.DB.PhoneDB;
import com.pedrycz.phonedb.entities.Phone;

import java.util.List;

public class PhoneRepository {
    private final PhoneDao dao;
    private final LiveData<List<Phone>> phones;

    public PhoneRepository(Application application) {
        PhoneDB phoneDB = PhoneDB.getDatabase(application);
        dao = phoneDB.phoneDao();
        phones = dao.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return dao.getAllPhones();
    }

    public void addPhone(Phone phone){
        dao.insert(phone);
    }

    public void updatePhone(Long id, String brand, String model, String version, String url) {
        dao.updatePhone(id, brand, model, version, url);
    }

    public void deletePhone(Phone phone){ dao.deletePhone(phone);}

    public void deleteAll() {
        PhoneDB.databaseWriteExecutor.execute(dao::deleteAll);
    }

    public int getDataCount(){
        return dao.getDataCount();
    }
}
