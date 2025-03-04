package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 저장하기
    @SneakyThrows
    public void saveItem(String title, Long price, MultipartFile file){


        String imgUrl = "/files/" + fileUploder(file);

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
    //파일 업로드
    public String fileUploder(MultipartFile file) throws IOException {
// 파일 업로드 처리 시작
        String projectPath = System.getProperty("user.dir") // 프로젝트 경로를 가져옴
                + "\\src\\main\\resources\\static\\files"; // 파일이 저장될 폴더의 경로

        File uploadFolder = new File(projectPath);

        if (!uploadFolder.exists()) {
            boolean created = uploadFolder.mkdirs();
            if (created) {
                System.out.println("업로드 폴더 생성됨: " + uploadFolder);
            } else {
                System.out.println("업로드 폴더 생성 실패");
            }
        }
        UUID uuid = UUID.randomUUID(); // 랜덤으로 식별자를 생성

        String fileName = uuid + "_" + file.getOriginalFilename(); // UUID와 파일이름을 포함된 파일 이름으로 저장


        File saveFile = new File(projectPath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

        file.transferTo(saveFile);
        String imgUrl = "/files/" + fileName;

        // 파일 업로드 처리 끝
        return fileName;
    }
}
