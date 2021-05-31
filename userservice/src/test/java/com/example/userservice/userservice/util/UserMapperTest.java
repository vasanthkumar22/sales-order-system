package com.example.userservice.userservice.util;

import com.example.userservice.userservice.dto.UserDto;
import com.example.userservice.userservice.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserMapperTest {

    @Test
    public void mapUserToUserDto(){
        User user = new User();
        user.setId(new Long(1));
        user.setName("Colin But");
        user.setUsername("colinbut");
        user.setPassword("password");
        user.setEmail("colin.but@email.com");
        user.setRoles("ADMIN,DEV,TEST");

        UserDto userDto = UserMapper.mapUserToUserDto(user);

        assertEquals("Colin But", userDto.getName());
        assertEquals("colinbut", userDto.getUsername());
        assertEquals("password", userDto.getPassword());
        assertEquals("colin.but@email.com", userDto.getEmail());

        List<String> roles = userDto.getRoles();
        assertFalse(roles.isEmpty());
        assertEquals(3, roles.size());
        assertEquals("ADMIN", roles.get(0));
        assertEquals("DEV", roles.get(1));
        assertEquals("TEST", roles.get(2));
    }
}
