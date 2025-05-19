package com.heechang.hcMan.domain.item.repository;

import com.heechang.hcMan.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
