package com.sparta.post.sevice;


import com.sparta.post.dto.*;
import com.sparta.post.entity.Post;
import com.sparta.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class PostService {

    private final PostRepository postRepository;

    //Repository에서 List<Post>로 받아온 데이터들을 List<PostResponseDto> 로 변환시켜준 다음, 리턴한다.
    public List<PostResDto> getAll() {
        List<PostResDto> result = new ArrayList<>();
        List<Post> postList = postRepository.findAllOrderByModifiedAtDesc();

        for (Post post : postList) {
            PostResDto PostResDto = new PostResDto(post);
            result.add(PostResDto);
        }
        return result;
    }


    public PostDetailDto getOne(Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return new PostDetailDto(optionalPost.get());
        }
    }

    @Transactional //데이터 베이스의내용을 바꿀때 사용
    public PostResDto create(PostReqDto postReqDto){   //게시글 저장

        Post post = postReqDto.toEntity();

        postRepository.save(post);

        return new PostResDto(post);

    }

    //비밀번호 확인
    //1.확인할 게시물을 가져온다 2.비교한다.
    public PasswordCheckResDto passwordCheck(Long id, @RequestBody PasswordCheckReqDto passwordCheckReqDto){

        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isEmpty()){
            throw new IllegalArgumentException();
        }else{
            Post post = optionalPost.get(); //내부에 값이 존재하므로 꺼낸다.

            //비밀번호 값이 일치하는지 화인한다.
            boolean match = post.passwordMatch(passwordCheckReqDto.getPassword());

            //원하는 리턴타입을 만든 후 리턴한다.
            return new PasswordCheckResDto(match);
        }
    }

    //게시글 수정
    //1.db에 저장된 수정할 게시글 가져오기 2.게시글을 수정 3.게시글을 저장
    @Transactional
    public PostResDto update(Long id, PostReqDto postReqDto){

        Optional<Post> optionalPost =  postRepository.findById(id);

        if (optionalPost.isEmpty()) { //게시글이 없다면.
            throw new IllegalArgumentException();
        }else {
            Post post = optionalPost.get(); //게시글 있다.

            post.update(
                    postReqDto.getTitle(),
                    postReqDto.getAuthor(),
                    postReqDto.getContent(),
                    postReqDto.getPassword());

            postRepository.save(post);

            return new PostResDto(post);
        }

    }



    //게시글 삭제
    @Transactional
    public PostDeleteResDto delete(Long id){

        postRepository.deleteById(id);

        return new PostDeleteResDto(true);

    }
}
