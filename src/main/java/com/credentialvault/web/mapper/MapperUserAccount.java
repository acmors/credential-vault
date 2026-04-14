package com.credentialvault.web.mapper;
import com.credentialvault.domain.UserAccount;
import com.credentialvault.web.dto.ResponseUserAccount;

public class MapperUserAccount {

    public static ResponseUserAccount toDTO(UserAccount user){
        return new ResponseUserAccount(
                user.getUsername(),
                user.getEmail()
        );
    }
}
