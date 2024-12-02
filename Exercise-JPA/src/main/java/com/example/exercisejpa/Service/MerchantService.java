package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Merchant;
import com.example.exercisejpa.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getMerchants() {

        return merchantRepository.findAll();
    }

    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public boolean updateMerchant(Integer id, Merchant merchant) {
        Merchant oldMerchant = merchantRepository.getById(id);
        if (oldMerchant == null) {
            return false;
        }
        for (int i = 0; i < merchantRepository.findAll().size(); i++) {
            if (oldMerchant.getId().equals(merchantRepository.findAll().get(i).getId())) {
                oldMerchant.setName(merchant.getName());
                oldMerchant.setCity(merchant.getCity());
            }
        }
        merchantRepository.save(merchant);
        return true;
    }

    public boolean deleteMerchant(Integer id) {
        Merchant merchant = merchantRepository.getById(id);
        if (merchant == null) {
            return false;
        }
        merchantRepository.delete(merchant);
        return true;
    }
}
