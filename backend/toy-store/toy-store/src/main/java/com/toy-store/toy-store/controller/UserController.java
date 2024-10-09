package com.petshop.petshop.controller;

import com.petshop.petshop.controller.validation.GlobalExceptionHandlerController;
import com.petshop.petshop.mappper.dto.*;
import com.petshop.petshop.model.*;
import com.petshop.petshop.service.ChatMessageService;
import com.petshop.petshop.service.ProductService;
import com.petshop.petshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class UserController extends GlobalExceptionHandlerController {

    private final UserService userService;
    private final ProductService productService;
    private final ChatMessageService chatMessageService;



    @PostMapping("/chatroom")
    public ResponseEntity<String> createChatRoom(@RequestBody(required = false) ChatRoomDto chatRoomDto) {


        // search for existing roomId
        System.out.println("----- first" + chatRoomDto.first_userId() + " second " + chatRoomDto.second_userId());
        String userId1 = chatRoomDto.first_userId();
        String userId2 = chatRoomDto.second_userId();

        if(userId1 == null || userId2 == null){
            return ResponseEntity.badRequest().build();
        }


        Long id1 = Long.parseLong(userId1);
        Long id2 = Long.parseLong(userId2);

        User user1 = userService.findUserByID(id1);
        User user2 = userService.findUserByID(id2);

        if(user1 == null || user2 == null){
            return ResponseEntity.badRequest().build();
        }

        String roomId = generateUniqueString(id1, id2);

        ChatMessage chatMessage = chatMessageService.findChatByRoomId(roomId, Long.parseLong(chatRoomDto.first_userId()));
        if(chatMessage != null){
            return ResponseEntity.ok(roomId);
        }


        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.setSender(user1);
        chatMessage1.setRoomId(roomId);

        ChatMessage chatMessage2 = new ChatMessage();
        chatMessage2.setSender(user2);
        chatMessage2.setRoomId(roomId);

        System.out.println("---> " + roomId);

        chatMessageService.save(chatMessage1);
        chatMessageService.save(chatMessage2);

        return ResponseEntity.ok(roomId);
    }


    private  String generateUniqueString(long id1, long id2) {
        String input = id1 + "-" + id2; // Concatenate the IDs
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
        return new String(encodedBytes);
    }
    @PostMapping("/user-messages")
    public ResponseEntity<List<String>> getAllMessages(@RequestBody(required = false) String userString) {

        String userId = userString.substring(7,userString.length() - 2);

        if(userId == null){
            System.out.println("NULL userID FOR getAllMessages");
            return ResponseEntity.badRequest().build();
        }

        Long id = Long.parseLong(userId);

        if(id == null){
            System.out.println("NULL ID FOR getAllMessages");
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findUserByID(id);
        ChatMessage chatMessage = user.getSenderChatMessage();

        if(chatMessage == null || user == null){
            System.out.println("user/char null");
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = chatMessageService.getMessagesByUser(user);

        List<String> messageContents = new ArrayList<>();

        for (Message message : messages) {
            messageContents.add(message.getMessage_content());
        }

        return ResponseEntity.ok(messageContents);
    }

    @PostMapping("/save-message")
    public void saveMessage(@RequestBody(required = false) ChatMessageDto chatMessageDto) {


        System.out.println("chatMessageDto: " +  chatMessageDto.getMessage() + " - user:" + chatMessageDto.getUser() + " - room" + chatMessageDto.getRoomId());

        Long senderId = Long.parseLong(chatMessageDto.getUser());
        ChatMessage chatMessage = chatMessageService.findChatByRoomId(chatMessageDto.getRoomId(), senderId);
        if(chatMessage == null){
            System.out.println("E NULL CHAT MESSAGE CAUTAT PRIN ROOM ID!!!!!!!");
            return;
        }

        chatMessageService.saveMessage(chatMessage, chatMessageDto.getMessage());
    }


    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUserDtos();
        System.out.println("----> " + userDtos.get(0).getUsername()  + " " + userDtos.get(0).getLogoutTime() );
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("/user-delete")
    public ResponseEntity<UserDto> deleteUser(@RequestBody(required = false) UserDto userDto) {
        if (userDto == null || userDto.getId() == null) {
            // Handle case when product or product ID is missing
            return ResponseEntity.badRequest().build();
        }

        User deletedUser = userService.findUserByID(userDto.getId());

        if (deletedUser != null) {
            userService.deleteUser(deletedUser);
            return ResponseEntity.ok().body(userDto); // Product successfully deleted
        } else {
            return ResponseEntity.notFound().build(); // Product not found
        }
    }


    //todo add @Valid - it might fail (same for user-delete)
    @PostMapping("/user-edit")
    public ResponseEntity<UserDto> editUser(@RequestBody(required = false)  UserDto userDto) {

        if (userDto == null || userDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findUserByID(userDto.getId());

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        UserDto editedUser = userService.updateFromUserDto(userDto, user.getPassword(), user.getRoles());

        if (editedUser == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.created(URI.create("/user-edit" + editedUser.getId())).body(editedUser);
    }


    @PostMapping("/add-cart")
    public ResponseEntity<UserDto> addToCart(@RequestBody(required = false) @Valid CartDto cart) {

        if (cart.productId() == null || cart.productId() == null || cart.username() == null) {
            return ResponseEntity.badRequest().build();
        }

        Product product = productService.getProductById(cart.productId());
        Optional<User> user = userService.findByUsername(cart.username());

        if (!user.isPresent() || product == null) {
            return ResponseEntity.badRequest().build();
        }

        UserDto userSaved = userService.addCartProducts(user.get(), cart.productId());

        return ResponseEntity.created(URI.create("/add-cart" + userSaved.getId())).body(userSaved);
    }


    @PostMapping("/add-fav")
    public ResponseEntity<UserDto> addToFavs(@RequestBody(required = false) @Valid CartDto fav) {

        if (fav.productId() == null || fav.productId() == null || fav.username() == null) {
            return ResponseEntity.badRequest().build();
        }

        Product product = productService.getProductById(fav.productId());
        Optional<User> user = userService.findByUsername(fav.username());

        if (!user.isPresent() || product == null) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println("ADD TO fav!!!!" + user.get().getUsername() + " " + fav.productId() + " " + fav.productId() + "--" + fav.username());

        UserDto userSaved = userService.addFavProducts(user.get(), fav.productId());

        return ResponseEntity.created(URI.create("/add-fav" + userSaved.getId())).body(userSaved);
    }


    @PostMapping("/remove-fav")
    public ResponseEntity<UserDto> removedFromFavs(@RequestBody(required = false) @Valid CartDto fav) {

        if (fav.productId() == null || fav.productId() == null || fav.username() == null) {
            return ResponseEntity.badRequest().build();
        }

        Product product = productService.getProductById(fav.productId());
        Optional<User> user = userService.findByUsername(fav.username());

        if (!user.isPresent() || product == null) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println("REMOVED FROM fav!!!!" + user.get().getUsername() + " " + fav.productId() + " " + fav.productId() + "--" + fav.username());

        UserDto userSaved = userService.removeFavProducts(user.get(), fav.productId());

        return ResponseEntity.created(URI.create("/remove-fav" + userSaved.getId())).body(userSaved);
    }

    @PostMapping("/cart-product")
    public ResponseEntity<List<ProductDto>> getCartProducts(@RequestBody(required = false) @Valid String username) {
        //todo -- get username from frontend without quotes

        String usernameTest = username.substring(1, username.length() - 1);

        List<ProductDto> productDtos = userService.fetchCartProducts(usernameTest);

        return ResponseEntity.ok(productDtos);
    }


    @PostMapping("/buy-product")
    public ResponseEntity<ReceiptDto> buyProducts(@RequestBody(required = false) @Valid String username) {
        //todo -- get username from frontend without quotes

        if (username == null) {
            return ResponseEntity.badRequest().build();
        }
        String usernameTest = username.substring(1, username.length() - 1);

        System.out.println("username: " + usernameTest);
        ReceiptDto receiptDto = userService.buyProducts(usernameTest);

        System.out.println("receipt:: " + receiptDto);
        return ResponseEntity.ok().body(receiptDto);
    }

    // roles assignment
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<?> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userService.findUserByID(userId);

        if(user == null){
            return ResponseEntity.badRequest().build();
        }

        userService.assignRoleToUser(user, roleId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/fav-prod")
    public ResponseEntity<List<ProductDto>> getFavProd(@RequestBody(required = false) @Valid String username) {
        //todo -- get username from frontend without quotes

        if (username == null) {
            return ResponseEntity.badRequest().build();
        }
        String usernameTest = username.substring(1, username.length() - 1);

        System.out.println("username: " + usernameTest);
        List<ProductDto> productDtos = userService.fetchFavProducts(usernameTest);

        System.out.println("products" + productDtos);
        return ResponseEntity.ok(productDtos);
    }


}