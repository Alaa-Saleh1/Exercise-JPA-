package com.example.exercisejpa.Service;


import com.example.exercisejpa.Model.Merchant;
import com.example.exercisejpa.Model.MerchantStock;
import com.example.exercisejpa.Repository.MerchantStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final MerchantStockRepository merchantStockRepository;



    public List<MerchantStock> getMerchantStocks() {

        return merchantStockRepository.findAll();
    }

    public Integer addMerchantStock(MerchantStock merchantStock) {


        boolean checkMerchantId = false;
        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getId() == merchantStock.getMerchantId()){
                checkMerchantId = true;
                break;
            }
        }
        if (!checkMerchantId) {
            return 0;
        }
        boolean checkProductId = false;
        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId() == merchantStock.getProductId()){
                checkProductId = true;
            }
        }
        if (!checkProductId) {
            return 1;
        }
        merchantStockRepository.save(merchantStock);
        return 2;
    }



    public Integer updateMerchantStock(Integer id, MerchantStock updatedStock) {
        boolean checkMerchantId = false;
        boolean checkProductId = false;
        MerchantStock merchantStock = merchantStockRepository.getById(id);

        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getId() == updatedStock.getMerchantId()) {
                checkMerchantId = true;
                break;
            }
        }
        if (!checkMerchantId) {
            return 0;
        }

        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId()==(updatedStock.getProductId())) {
                checkProductId = true;
                break;
            }
        }
        if (!checkProductId) {
            return 1;
        }

        for (int i = 0; i < merchantStockRepository.findAll().size(); i++) {
            if (merchantStockRepository.findAll().get(i).getId() == id) {
                merchantStock.setMerchantId(updatedStock.getMerchantId());
                merchantStock.setProductId(updatedStock.getProductId());
                merchantStock.setStock(updatedStock.getStock());
            }
        }
        merchantStockRepository.save(merchantStock);
        return 2;
    }



    public Boolean deleteMerchantStock(Integer id){
        MerchantStock merchantStock = merchantStockRepository.getById(id);
        if (merchantStock == null) {
            return false;
        }
        merchantStockRepository.delete(merchantStock);
        return true;
    }

    //11
    public int addToStock (Integer idProduct,Integer idMerchant,int amount){
        boolean checkMerchantId = false;
        boolean checkProductId = false;

        for (int i = 0; i < merchantStockRepository.findAll().size(); i++) {

            if (merchantStockRepository.findAll().get(i).getMerchantId().equals(idMerchant)) {
                checkMerchantId = true;
            }
            if (merchantStockRepository.findAll().get(i).getProductId().equals(idProduct)) {
                checkProductId = true;
            }

            if (checkMerchantId && checkProductId) {
                merchantStockRepository.findAll().get(i).setStock(merchantStockRepository.findAll().get(i).getStock() + amount);
                return 2;
            }
        }

        if (!checkMerchantId) {
            return 0;
        }
        if (!checkProductId) {
            return 1;
        }

        return 2;
    }


    //method 3 for endpoint 3
    public List<Merchant> findMerchantsByProductAndCity(Integer productId, String city) {
        List<Merchant> result = new ArrayList<>();
        for (int i = 0; i < merchantStockRepository.findAll().size(); i++) {
            if (merchantStockRepository.findAll().get(i).getProductId().equals(productId)) {
                for (int j = 0; j < merchantService.getMerchants().size(); j++) {
                    if (merchantService.getMerchants().get(j).getCity().equalsIgnoreCase(city) && merchantService.getMerchants().get(j).getId().equals(merchantStockRepository.findAll().get(i).getMerchantId())) {
                        result.add(merchantService.getMerchants().get(j));
                    }
                }
            }
        }
        return result;
    }
}
