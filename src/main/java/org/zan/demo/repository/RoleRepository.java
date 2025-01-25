package org.zan.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zan.demo.entity.Role;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Retrieves a role by its authority.
     *
     * @param authority The authority of the role to be retrieved.
     * @return An Optional containing the Role with the specified authority if found, otherwise empty.
     */
    Optional<Role> findByAuthority(String authority);
}
