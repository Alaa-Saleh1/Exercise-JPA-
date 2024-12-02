package com.example.exercisejpa.Controller;

import com.example.exercisejpa.ApiResponse.ApiResponse;
import com.example.exercisejpa.Model.User;
import com.example.exercisejpa.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/amazon-user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUser(){
        List user = userService.getAllUser();
        return ResponseEntity.status(200).body(user);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User has been added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id,@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate = userService.updateUser(id,user);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("User has been updated successfully"));
        }
        return ResponseEntity.status(400).body("User not found");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("User has been deleted successfully"));
        }
        return ResponseEntity.status(400).body("User not found");

    }

    //12
    @PutMapping("/buyProduct/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer merchantId){
        int result = userService.buyProduct(userId, productId, merchantId);
        switch (result) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("Product ID not found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User ID not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Insufficient balance"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("Product out of stock"));

        }
        return ResponseEntity.status(200).body(new ApiResponse("Purchase successful"));
    }


    //endpoint 1
    @PostMapping("/prime-subscription/{userId}")
    public ResponseEntity<?> primeSubscription(@PathVariable Integer userId) {
        int result = userService.primeSubscription(userId);
        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("User ID not found"));
        } else if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Insufficient balance"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Prime subscription activated successfully"));

    }

    //endpoint 6
    @GetMapping("/wishList/{userId}/{productId}")
    public ResponseEntity<?> addToWishList(@PathVariable Integer userId, @PathVariable Integer productId){
        ArrayList wishList = userService.addToWishList(userId,productId);
        if(wishList==null){
            return ResponseEntity.status(400).body(new ApiResponse("You don't have any wish list"));
        }
        return ResponseEntity.status(200).body(wishList);
    }

}
