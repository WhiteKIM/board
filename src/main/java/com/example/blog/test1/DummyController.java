package com.example.blog.test1;

import com.example.blog.Model.RoleType;
import com.example.blog.Model.User;
import com.example.blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyController {
    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id)
    {
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "제거 실패";
        }
        return "제거 완료";
    }
    //email, password 수정
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User requestUser)
    {
        System.out.println("id : "+id);
        System.out.println("password : "+requestUser.getPassword());
        System.out.println("email :"+requestUser.getEmail());

        User user =  userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list()
    {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public Page<User> pageList(@PageableDefault(size = 2, sort="id", direction=Sort.Direction.DESC) Pageable pageable)
    {
        Page<User> users = userRepository.findAll(pageable);

        List<User> newUsers = users.getContent();
        return users;
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id)
    {
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 존재하지 않는다");
            }
        });
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user)
    {
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입";
    }
}
