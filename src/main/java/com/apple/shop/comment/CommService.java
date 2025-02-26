package com.apple.shop.comment;

import com.apple.shop.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommService {

    private final CommRepository commRepository;

    //댓글 저장하기
    public void saveComm( String username,String content, Long parentId){

        Comment comment = new Comment();
        comment.setUsername(username);
        comment.setContent(content);
        comment.setParentId(parentId);
        commRepository.save(comment);
    }
}
