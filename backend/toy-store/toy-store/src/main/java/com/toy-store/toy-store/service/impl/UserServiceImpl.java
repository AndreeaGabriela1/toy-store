package com.petshop.petshop.service.impl;

import com.petshop.petshop.exception.AppException;
import com.petshop.petshop.mappper.ProductMapper;
import com.petshop.petshop.mappper.RoleMapper;
import com.petshop.petshop.mappper.UserMapper;
import com.petshop.petshop.mappper.dto.*;
import com.petshop.petshop.model.*;
import com.petshop.petshop.repository.ProductRepository;
import com.petshop.petshop.repository.RoleRepository;
import com.petshop.petshop.repository.UserRepository;
import com.petshop.petshop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private final UserMapper userMapper;

   // private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;

   @Autowired
    private final ProductRepository productRepository;

    private final RoleMapper roleMapper;

    private final PasswordEncoder passwordEncoder;


    private final ProductMapper productMapper;

      @Override
    public UserDto registerUser(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .password(registrationRequest.getPassword())
                .email_address(registrationRequest.getEmail_address())
               // .role((roleRepository.findByRole("USER")))
                .build();

        // nu se pot da String-uri  la cascadare si mapare

        return this.createUserDto(user);
    }


    @Override
    public UserDto getUserDtoById(Long id) {
        return userMapper.userEntityToDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public User findUserByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserDto> getAllUserDtos() {
        return userMapper.userListEntityToDto(userRepository.findAll());
    }
    @Override
    public List<ProductDto> fetchCartProducts(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            return null;
        }
        List<Product> cartProducts = user.get().getCart_products();

        if(cartProducts == null)
            return null;

        List<ProductDto> cartProductDtos = productMapper.productListEntitytoDto(cartProducts);
        for(Product p : cartProducts){
              System.out.println(p.getName());
          }
        return cartProductDtos;
    }
    @Override
    public List<ProductDto> fetchFavProducts(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            return null;
        }
        List<Product> cartProducts = user.get().getFavorite();

        if(cartProducts == null)
            return null;

        List<ProductDto> cartProductDtos = productMapper.productListEntitytoDto(cartProducts);
        for(Product p : cartProducts){
              System.out.println(p.getName());
          }
        return cartProductDtos;
    }

    @Override
    public ReceiptDto buyProducts(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            return null;
        }
        List<Product> cartProducts = user.get().getCart_products();

        if(cartProducts == null)
            return null;

        List<String> cartProductStrings= new ArrayList<>();

        for(Product p : cartProducts){
            cartProductStrings.add(p.getName());
        }

        // delete product from db
        User userUpdate = user.get();
        userRepository.delete(userUpdate);

        userUpdate.setCart_products(new ArrayList<>());
        userRepository.save(userUpdate);

        for(Product p : cartProducts){
            productRepository.delete(p);
        }

        LocalDateTime now = LocalDateTime.now();
        String timeString = now.toString();



        ReceiptDto receiptDto =  ReceiptDto.builder()
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .username(user.get().getUsername())
                .product(cartProductStrings)
                .date(timeString)
                .build();

        return receiptDto;
    }

    @Override
    public List<UserDto> loginBetween(LocalDateTime start, LocalDateTime stop) {
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty())
            return null;

        List<UserDto> users = new ArrayList<>();

        for(User u : allUsers){
            LocalDateTime date = u.getLoginTime();
            System.out.println("date here: " + u.getUsername() + " : " + date);
            if(date.isAfter(start) && date.isBefore(stop))
            {
                System.out.println("good");

                users.add(userMapper.userEntityToDto(u));
            }else{
                System.out.println("not good");

            }
        }

        return users;
    }

    @Override
    public void settingLogout(User user) {
        LocalDateTime now = LocalDateTime.now();

        user.setLogoutTime(now);
        userRepository.save(user);
    }

    @Override
    public UserDto forgotPassword(MinimalUserDto minimalUserDto) {

        Optional<User> originalUser = userRepository.findByUsername(minimalUserDto.getUsername());

        if(originalUser.isEmpty()){
            System.out.println("Service: USER NOT FOUND!");
            return null;
        }

        User user = originalUser.get();
        if(!forgotPasswordValidation(user, minimalUserDto)){
            System.out.println("Service: CREDENTIALS ARE NOT CORRECT");
            return null;
        }


        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(minimalUserDto.getPassword())));

        User saved =  userRepository.save(user);

        if(saved == null){
            return  null;
        }
        return userMapper.userEntityToDto(saved);

    }


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDto createUserDto(User user){
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());

        return userMapper.userEntityToDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUserDto(User user) {
        return userMapper.userEntityToDto(userRepository.save(user));
    }
    @Override
    public UserDto updateFromUserDto(UserDto userDto, String password, List<Role> roles) {
          User user = userMapper.userDtoToEntity(userDto, password);

        System.out.println("user edited: " + user);

        // TODO: it duplicate roles:

        user.setRoles(roles);
       User saved = userRepository.save(user);


        System.out.println("user dto init: " + userDto);

        // UserDto savedDto u
       return userDto;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }


    public UserDto login(CredentialsDto credentialsDto) {

        //todo - hashing pe parole

        System.out.println("@@@@@------------------------------------>>>> " + credentialsDto.getUsername());
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));


        String encodedPassword = passwordEncoder.encode(new String(credentialsDto.getPassword()));

        System.out.println("parole:\npasswordEncoder: "+CharBuffer.wrap(credentialsDto.getPassword()).toString()+"\nuser password: "+user.getPassword()+"\n");

        if (!passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()).toString(), user.getPassword())) {
            return null;
        }


        System.out.println("PAROLEEEEEE");
        System.out.println(CharBuffer.wrap(credentialsDto.getPassword()).toString()+ "-");
        System.out.println("password credential: " + passwordEncoder.encode(CharBuffer.wrap(credentialsDto.getPassword())));
        System.out.println("user password db: " + user.getPassword()+"-");
        System.out.println(passwordEncoder.encode(CharBuffer.wrap(credentialsDto.getPassword())).equals(user.getPassword()));
        System.out.println("end PAROLEEEEEE");

        //login time:
        user.setLoginTime(LocalDateTime.now());
        System.out.println("time:"+user.getLoginTime());
        User saved = userRepository.save(user);

        if(passwordEncoder.encode(CharBuffer.wrap(credentialsDto.getPassword())).equals(user.getPassword())) {
            return userMapper.userEntityToDto(saved);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }




    public UserDto register(MinimalUserDto userSignUp) {

        Optional<User> optionalUser = userRepository.findByUsername(userSignUp.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userSignUp);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userSignUp.getPassword())));
        System.out.println("%%%%% REGISTER PASSWORD--->"+user.getPassword());



        Role customer = roleRepository.findByRole("CUSTOMER");
        this.assignRoleToUser(user, customer.getId());
        User savedUser = userRepository.save(user);

        return userMapper.userEntityToDto(savedUser);
    }

    @Override
    public UserDto addCartProducts(User user, Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty())
            return null;

        List<Product> addedToCart = user.getCart_products();

        if(addedToCart == null)
            addedToCart = new ArrayList<>();

        addedToCart.add(product.get());

        user.setCart_products(addedToCart);
        User saved = userRepository.save(user);

        if(saved == null)
            return null;

        List<Product> savedProd = saved.getCart_products();

        UserDto savedDto = userMapper.userEntityToDto(saved);

        return savedDto;
    }


    @Override
    public UserDto addFavProducts(User user, Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty())
            return null;

        List<Product> addedToFav = user.getFavorite();

        if(addedToFav == null)
            addedToFav = new ArrayList<>();

        if(!addedToFav.contains(product.get()))
            addedToFav.add(product.get());

        user.setFavorite(addedToFav);
        User saved = userRepository.save(user);

        if(saved == null)
            return null;

        List<Product> savedProd = saved.getCart_products();

        UserDto savedDto = userMapper.userEntityToDto(saved);

        return savedDto;
    }


    @Override
    public UserDto removeFavProducts(User user, Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty())
            return null;

        List<Product> toRemoveFromFav = user.getFavorite();


        if(toRemoveFromFav == null || !toRemoveFromFav.contains(product.get()))
            return userMapper.userEntityToDto(user);


        toRemoveFromFav.remove(product.get());
        user.setFavorite(toRemoveFromFav);
        User saved = userRepository.save(user);

        if(saved == null)
            return null;

        List<Product> savedProd = saved.getCart_products();

        UserDto savedDto = userMapper.userEntityToDto(saved);

        return savedDto;
    }


    @Override
    public void assignRoleToUser(User user, Long roleId) {
       // User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("!!!! User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("!!!! Role not found"));

        List<Role>  roles = user.getRoles();

        if(roles == null)
            roles = new ArrayList<>();

        roles.add(role);
        user.setRoles(roles);

//        user.setRole(RoleE.SELLER);
        userRepository.save(user);
    }

    private Product findProductById(List<Product> products, int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null; // Product not found
    }

    private boolean forgotPasswordValidation(User originalUser, MinimalUserDto minimalUserDto){



        if(originalUser.getUsername().equalsIgnoreCase(minimalUserDto.getUsername())
          && originalUser.getFirstName().equalsIgnoreCase(minimalUserDto.getFirstName())
          && originalUser.getLastName().equalsIgnoreCase(minimalUserDto.getLastName())
          && originalUser.getEmail_address().equalsIgnoreCase(minimalUserDto.getEmail_address())) {
            return true;
        }

        return false;

    }

}
