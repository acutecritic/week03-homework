package com.sparta.post.controller;


import com.sparta.post.dto.*;
import com.sparta.post.sevice.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@Controller // html
@RestController //json
@RequiredArgsConstructor

@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("posts") //게시글 전체조회
    public List<PostResDto> getAll() {
        return postService.getAll();
    }

    @GetMapping("posts/{id}")  //게시글 하나조회
    public PostDetailDto getOne(@PathVariable("id") Long id){
        return postService.getOne(id);
    }


    @PostMapping("posts")  //게시글 작성 http body에 담겨있다.
    public PostResDto createPost(@RequestBody PostReqDto postReqDto){

        return postService.create(postReqDto);
    }

    //비밀번호 확인
    @PostMapping("posts/{id}")
    public PasswordCheckResDto passwordCheck(@PathVariable("id") Long id, @RequestBody PasswordCheckReqDto passwordCheckReqDto){

        return postService.passwordCheck(id, passwordCheckReqDto);
    }

    //게시글 수정
    @PutMapping("/posts/{id}")
    public PostResDto updatePost(@PathVariable("id") Long id, @RequestBody PostReqDto postReqDto){

        return postService.update(id,postReqDto);
    }

    //게시글 삭제
    @DeleteMapping("/posts{id}")
    public PostDeleteResDto deletePost(@PathVariable("id") Long id){

        return postService.delete(id);
    }


}
