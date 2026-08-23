package com.wallet.wallet_app.repository;

import com.wallet.wallet_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
