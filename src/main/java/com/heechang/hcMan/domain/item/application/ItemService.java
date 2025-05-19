package com.heechang.hcMan.domain.item.application;

import com.heechang.hcMan.domain.item.entity.Item;
import com.heechang.hcMan.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("품목이 존재하지 않습니다: " + id));
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item newItem) {
        Item item = findById(id);
        item.setItemName(newItem.getItemName());
        item.setUnitCode(newItem.getUnitCode());
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
