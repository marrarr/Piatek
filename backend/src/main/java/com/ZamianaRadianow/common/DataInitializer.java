package com.ZamianaRadianow.common;

import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.rola.RoleRepository;
import com.ZamianaRadianow.security.user.UserRepository;
import com.ZamianaRadianow.zamiana.model.Jednostka;
import com.ZamianaRadianow.zamiana.model.Zamiana;
import com.ZamianaRadianow.zamiana.ZamianaRepository;
import com.ZamianaRadianow.zamiana.ZamianaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ZamianaRepository zamianaRepository;
    @Autowired
    private ZamianaService zamianaService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {

            DBRole adminDBRole = new DBRole();
            adminDBRole.setName("ADMIN");
            roleRepository.save(adminDBRole);

            DBRole userDBRole = new DBRole();
            userDBRole.setName("USER");
            roleRepository.save(userDBRole);

            //-------

            DBUser admin = new DBUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.getRoles().add(adminDBRole);
            userRepository.save(admin);

            DBUser user = new DBUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("123"));
            user.getRoles().add(userDBRole);
            userRepository.save(user);

            //-------



            System.out.println("=========================");
            System.out.println("=========================");
            System.out.println("GOTOWE GOTOWE GOTOWE GOTOWE");
            System.out.println("=========================");
            System.out.println("=========================");
        }
    }
}