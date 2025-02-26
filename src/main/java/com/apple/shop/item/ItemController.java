package com.apple.shop.item; // 파일 위치 ( 경로 )

import com.apple.shop.comment.CommRepository;
import com.apple.shop.comment.CommService;
import com.apple.shop.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    //상품과 관련된 API 모음

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CommRepository commRepository;
   // 메인 리스트
    @GetMapping("/list")
    String list(Model model){
       List<Item> result =  itemRepository.findAll();

        model.addAttribute("items",result);

        return "list.html";
    }
    
    // 글쓰기
    @GetMapping("/write")
    String write(){

        return "write.html";
    }

    // 글쓰기 추가 로직
    @PostMapping("/add")
    String addPost(String title, Long price, String imgUrl){

        itemService.saveItem(title,price,imgUrl);
        return "redirect:/list/page/1";
    }

    // 상세페이지 접속
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {

        List<Comment> res =  commRepository.findAllByParentId(id);
                model .addAttribute("comm", res);

            Optional<Item> result = itemRepository.findById(id); // Optional: 변수가 비어있을수도 있을 수도 있음
            if (result.isPresent()) { // isPresent 값이 있으면
                model .addAttribute("item", result.get());
                System.out.println(result.get());
                return "/detail.html";
            } else {
                return "redirect:/list/detail/1";
            }
    }

    // 수정하기
    @GetMapping("/modify/{id}")
    String modify(@PathVariable Long id, Model model){
        Optional<Item> result = itemRepository.findById(id);
        System.out.println(" 들어온 id :"+id);
        System.out.println(" 조회해온 결과값 :"+result.get());
        model.addAttribute("data", result.get());

        return "modify.html";
    }

    // 수정하기 추가 로직
    @PostMapping("/modify")
    String modifyPost(String title, Long price, Long id, Model model){

        if(price < 0 || title.length() > 15){
          //  model.addAttribute("alertMessage","상품명을 15자 이내로 작성하시거나 가격을 음수로 적지 마세요.");
            return "redirect:/modify/"+id;
        }

        itemService.modfItem(id,title,price);
        System.out.println("수정완료");
        return "redirect:/list";
    }
    // 삭제하기
    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long selId){
       itemRepository.deleteById(selId);
        return ResponseEntity.status(200).body("삭제완료");
    }


    //페이징
    @GetMapping("/list/page/{pageNum}")
    String paging(Model model, @PathVariable Integer pageNum){

        System.out.println(pageNum);
        Page<Item> result =  itemRepository.findPageBy(PageRequest.of(pageNum-1,5));
        model.addAttribute("items", result);
        model.addAttribute("totalPage",result.getTotalPages());
        return "list.html";
    }



//    //url 연결
//    @GetMapping("presigned-url")
//    @ResponseBody
//    String getURL(@RequestParam String filename){
//
//        var result = s3Service.createPresignedUrl("test/" + filename);
//        System.out.println(result);
//        return result;
//    }

}
