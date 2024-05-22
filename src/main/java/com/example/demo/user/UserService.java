package com.example.demo.user;
import com.example.demo.database.entities.User;
import com.example.demo.user.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.utils.NullCheck;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setHash(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }


    protected	NullCheck nullcheck = new NullCheck();
    protected PageRequest getListUserOderbyIDDes(int page) {
        return PageRequest.of(page, 20, Sort.Direction.DESC, "id");
    }
    protected PageRequest getListUserPostNotActive(int page,int size) {
        return PageRequest.of(page, size, Sort.Direction.DESC, "id");
    }
    protected PageRequest getListUserPostInActive(int page,int size) {
        return PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    }

    protected User getCurentUser(Authentication authentication) {
        authentication=SecurityContextHolder.getContext().getAuthentication();
        User user=(User) userService.findByUsername(authentication.getName());
        return user;
    }
}