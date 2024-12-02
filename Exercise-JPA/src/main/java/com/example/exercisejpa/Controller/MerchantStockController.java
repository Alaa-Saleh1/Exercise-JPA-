package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.MerchantStock;
import com.example.exercisejpa.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/amazon-merchant-stock")
public class MerchantStockController{

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStock(){
        List<MerchantStock> merchantStocks= merchantStockService.getMerchantStocks();
        return ResponseEntity.status(200).body(merchantStocks);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result = merchantStockService.addMerchantStock(merchantStock);
        if (result == 2){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added successfully."));
        }else if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("Product ID not found."));
        }else return ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable Integer id, @RequestBody @Valid MerchantStock merchantStock,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        int result = merchantStockService.updateMerchantStock(id, merchantStock);
        switch (result) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("Invalid merchant ID"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("Invalid product ID."));
            case 2:
                return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully."));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found."));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable Integer id){
        Boolean idDeleted = merchantStockService.deleteMerchantStock(id);
        if (idDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Stock deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Stock not found"));
    }

    @PutMapping("/add-to-stock/{productId}/{merchantId}/{amount}")
    public ResponseEntity<?> addToStock(@PathVariable Integer productId,@PathVariable Integer merchantId,@PathVariable Integer amount){
        Integer isAdded= merchantStockService.addToStock(productId,merchantId,amount);
        if (isAdded == 2){
            return ResponseEntity.status(200).body(new ApiResponse("Stock updated successfully"));
        } else if (isAdded == 1){
            return ResponseEntity.status(400).body(new ApiResponse("Product ID not found"));
        } return ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));
    }

    //endpoint3
    @GetMapping("/search/{id}/{city}")
    public ResponseEntity<?> findMerchantsByProductAndCity(@PathVariable Integer id, @PathVariable String city){
        List result= merchantStockService.findMerchantsByProductAndCity(id, city);
        if (result.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No merchant was found in the same city"));
        }
        return ResponseEntity.status(200).body(result);
    }

}
