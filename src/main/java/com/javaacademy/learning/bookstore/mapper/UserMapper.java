package com.javaacademy.learning.bookstore.mapper;
import com.javaacademy.learning.bookstore.dto.UserDTO;
import  com.javaacademy.learning.bookstore.entities.User;

public class UserMapper {

    public static User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGender(userDTO.getGender());
        user.setYearOfBirth(userDTO.getYearOfBirth());
        user.setCountry(userDTO.getCountry());
        user.setVerifiedAccount(userDTO.isVerifiedAccount());
        user.setMailCode(userDTO.getMailCode());
        return user;

    }
    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setGender(user.getGender());
        userDTO.setYearOfBirth(user.getYearOfBirth());
        userDTO.setCountry(user.getCountry());
        userDTO.setVerifiedAccount(user.isVerifiedAccount());
        userDTO.setMailCode(user.getMailCode());
        return userDTO;
    }
}
