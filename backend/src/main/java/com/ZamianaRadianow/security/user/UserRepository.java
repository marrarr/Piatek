package com.ZamianaRadianow.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DBUser, Long> {
    DBUser findByUsername(String username);
}
