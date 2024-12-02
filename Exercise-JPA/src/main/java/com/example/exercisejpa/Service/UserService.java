package com.example.exercisejpa.Service;

import com.example.exercisejpa.Model.Product;
import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    ArrayList<Product> wishList = new ArrayList<>();



    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public boolean updateUser(Integer id, User user){
        User oldUser = userRepository.getOne(id);
        if(oldUser == null){
            return false;
        }
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            if(userRepository.findAll().get(i).getId().equals(id)){
                oldUser.setUsername(user.getUsername());
                oldUser.setPassword(user.getPassword());
                oldUser.setEmail(user.getEmail());
                oldUser.setRole(user.getRole());
                oldUser.setBalance(user.getBalance());
            }
        }
        userRepository.save(oldUser);
        return true;
    }

    public boolean deleteUser(Integer id){
        User oldUser = userRepository.getOne(id);
        if(oldUser == null){
            return false;
        }
        userRepository.delete(oldUser);
        return true;
    }



    public Integer buyProduct(Integer userId, Integer productId, Integer merchantId) {
        boolean checkUserId = false;
        boolean checkMerchantId = false;
        boolean checkProductId = false;
        boolean checkMerchantStock = false;
        boolean checkOutOfBalance = false;
        Integer price = 0;

        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId().equals(productId)) {
                checkProductId = true;
                price = productService.getProducts().get(i).getPrice();
            }

        }
        if (!checkProductId) {
            return 0;
        }
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            if (userRepository.findAll().get(i).getId().equals(userId)) {
                checkUserId = true;
                if (userRepository.findAll().get(i).getBalance() >= price) {
                    checkOutOfBalance=true;
                }
            }
        }

        if (!checkUserId) {
            return 1;
        }
        if (!checkOutOfBalance) {
            return 2;
        }

        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getId().equals(merchantId)) {
                checkMerchantId = true;
            }
        }
        if (!checkMerchantId) {
            return 3;
        }

        for (int i = 0; i < merchantStockService.getMerchantStocks().size(); i++) {
            if (merchantStockService.getMerchantStocks().get(i).getMerchantId().equals(merchantId)) {
                if (merchantStockService.getMerchantStocks().get(i).getStock()>=1) {
                    checkMerchantStock = true;
                }
            }
        }

        if (!checkMerchantStock) {
            return 4;
        }

        for (int i = 0; i < userRepository.findAll().size(); i++) {
            for (int j = 0; j < merchantStockService.getMerchantStocks().size(); j++) {
                double totalprice = price-applyDiscount(userId,price);
                userRepository.findAll().get(i).setBalance((int) (userRepository.findAll().get(i).getBalance()-totalprice));
                merchantStockService.getMerchantStocks().get(j).setStock(merchantStockService.getMerchantStocks().get(j).getStock()-1);
                userRepository.findAll().get(i).setCounter(userRepository.findAll().get(i).getCounter()+1);
            }
        }
        return 5;
    }

    public double applyDiscount(Integer userId, Integer price) {
        Integer discount = 0;
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            if (userRepository.findAll().get(i).getId().equals(userId)) {
                if (userRepository.findAll().get(i).getCounter()==0) {
                    discount = price *(5/100);
                    return discount;
                }
                if(userRepository.findAll().get(i).getIsPrime()){
                    discount = price *(10/100);
                }
            }
        }

        return discount;
    }


    // method for endpoint 1
    public Integer primeSubscription(Integer userId) {
        boolean checkUserId = false;
        boolean checkBalance = false;
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            if (userRepository.findAll().get(i).getId().equals(userId)) {
                checkUserId = true;
            }
            if (userRepository.findAll().get(i).getBalance()>=16){
                checkBalance = true;
            }
            if (checkUserId && checkBalance) {
                userRepository.findAll().get(i).setBalance(userRepository.findAll().get(i).getBalance()-16);
                userRepository.findAll().get(i).setIsPrime(true);
                return 3;
            }
        }
        if (!checkUserId) {
            return 1;
        }
        if (checkBalance) {
            return 2;
        }
        return 3;

    }













    //6 endpoint
    public ArrayList addToWishList(Integer idUser,Integer idProdect){
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            for (int j = 0; j < productService.getProducts().size(); j++) {
                if (productService.getProducts().get(j).getId().equals(idProdect)){
                  wishList.add(productService.getProducts().get(j));
                }
            }
        }
        return wishList;
    }
}
