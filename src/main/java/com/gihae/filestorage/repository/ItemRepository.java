package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where i.id = :itemId")
    Optional<Item> findById(@Param("itemId") Long itemId);

    @Modifying
    @Query("delete Item i where i.id = :itemId")
    void deleteById(@Param("itemId") Long itemId);
}
