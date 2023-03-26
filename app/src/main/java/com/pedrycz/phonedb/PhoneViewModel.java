package com.pedrycz.phonedb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pedrycz.phonedb.entities.Phone;
import com.pedrycz.phonedb.repositories.PhoneRepository;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {

    private final PhoneRepository repository;
    private final LiveData<List<Phone>> phones;

    public PhoneViewModel(@NonNull Application application){
        super(application);
        repository = new PhoneRepository(application);
        phones = repository.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones(){
        return phones;
    }

    public void addPhone(Phone phone){
        repository.addPhone(phone);
    }

    public void updatePhone(Long id, String brand, String model, String version, String url){
        repository.updatePhone(id,brand, model,version, url);
    }

    public void deletePhone(Phone phone) { repository.deletePhone(phone);}

    public int getDataCount(){
        return repository.getDataCount();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
