package com.credentialvault.common.mapper;
import com.credentialvault.domain.model.UserAccount;
import com.credentialvault.application.dto.user.ResponseUserAccount;

public class MapperUserAccount {

    public static ResponseUserAccount toDTO(UserAccount user){
        return new ResponseUserAccount(
                user.getUsername(),
                user.getEmail()
        );
    }
}
