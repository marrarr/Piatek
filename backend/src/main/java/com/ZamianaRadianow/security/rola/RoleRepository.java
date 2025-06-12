package com.ZamianaRadianow.security.rola;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<DBRole, Long> {
    DBRole findByName(String name);
}