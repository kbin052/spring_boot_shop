package com.apple.shop.comment;

import com.apple.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {


    private final CommService commService;

    //    [ 별도의 행에 보관하기 - 정규화 ]
    //    1. 한칸에 데이터 매우 많이 넣어야 하는경우
    //    2. 데이터마다 종속된 컬럼이 필요한 경우

    //댓글 등록
    @PostMapping("/addComment")
    String addComment(Long id, @RequestParam String content,@RequestParam Long parent, Authentication auth){

        CustomUser user = (CustomUser) auth.getPrincipal();

        commService.saveComm(user.getUsername(),content,parent);
        return "redirect:/detail/"+parent;
    }


}
