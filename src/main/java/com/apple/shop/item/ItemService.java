package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    
    // 저장하기
    public void saveItem(String title, Long price, String imgUrl){
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setImgUrl(imgUrl);
        itemRepository.save(item);
    }
    //수정하기
    public void modfItem(Long id,String title, Long price){
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
}
