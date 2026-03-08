package io.github.auggud.projectmanager.repository;

import io.github.auggud.projectmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Long> {
}
