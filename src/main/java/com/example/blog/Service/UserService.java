package com.example.blog.Service;

import com.example.blog.Model.RoleType;
import com.example.blog.Model.User;
import com.example.blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public User findUser(String username)
    {
        User user = userRepository.findByUsername(username).orElseGet(()->{
          return new User();
        });
        return user;
    }

    @Transactional
    public void register(User entity)
    {
        String rawPassword = entity.getPassword();
        String encPassword = encoder.encode(rawPassword);
        entity.setPassword(encPassword);
        entity.setRole(RoleType.USER);
        userRepository.save(entity);
    }

    @Transactional
    public void modify(User user)
    {
        //수정시에 영속성 컨텐스트 user 오브젝트를 영속화하고, 영속화된 오브젝트 수정
        User persistence1 = userRepository.findById(user.getId()).orElseThrow(() ->{
           return new IllegalArgumentException("회원 찾기 실패");
        });

        if(persistence1.getOauth() == null || persistence1.getOauth().equals(""))
        {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistence1.setPassword(encPassword);
            persistence1.setEmail(user.getEmail());
        }
    }

    /*
    @Transactional(readOnly = true) //정확성 유지, 서비스 종료시 트랜잭션 종료
    public User login(User entity)
    {
        return userRepository.findByUsernameAndPassword(entity.getUsername(), entity.getPassword());
    }
    */
}
