package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.AddWishListDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.entities.Book;
import com.bridgelabz.bookstore.entities.WishList;
import com.bridgelabz.bookstore.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/wishList")
public class WishListController {


    @Autowired
    IWishListService wishListService;

    @GetMapping("/getAllWishList")
    public ResponseEntity<ResponseDTO> getAllWishListItem(){
        List<WishList> allWishLists = wishListService.getAllWishLists();
        List<Book> books = allWishLists.stream().map(item -> item.bookId).toList();
        ResponseDTO responseDTO = new ResponseDTO("getCall success" , books);
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping("/addToWishList")
    public ResponseEntity<ResponseDTO> addToWishList(@RequestBody AddWishListDTO addWishListDTO){

        WishList wishList = wishListService.addToWishList(addWishListDTO.bookId);

        ResponseDTO responseDTO = new ResponseDTO("Post call success" , wishList);
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }


    @DeleteMapping ("/deleteFromWishList/{wishListId}")
    public ResponseEntity<ResponseDTO> deleteFromWishList(@PathVariable("wishListId") int wishListId){
        wishListService.deleteFromWishList(wishListId);
        ResponseDTO responseDTO = new ResponseDTO(" delete call success" , "book deleted from wish-list Successfully");
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @DeleteMapping ("/deleteFromWishListByBookId/{bookId}")
    public ResponseEntity<ResponseDTO> deleteFromWishListByBookId(@PathVariable("bookId") Long bookId){
        wishListService.deleteFromWishListByBookId(bookId);
        ResponseDTO responseDTO = new ResponseDTO(" delete call success" , "book deleted from wish-list Successfully");
        return new ResponseEntity<ResponseDTO>(responseDTO , HttpStatus.OK);
    }

}
