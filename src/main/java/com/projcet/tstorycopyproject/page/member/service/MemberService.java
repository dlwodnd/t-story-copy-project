package com.projcet.tstorycopyproject.page.member.service;

import com.projcet.tstorycopyproject.page.blog.errorcode.BlogErrorCode;
import com.projcet.tstorycopyproject.global.entity.BlogEntity;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.BlogRepository;
import com.projcet.tstorycopyproject.global.utils.MyFileUtils;
import com.projcet.tstorycopyproject.page.manage.request.BlogRegisterRq;
import com.projcet.tstorycopyproject.page.member.response.BlogInfoRp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final BlogRepository blogRepository;
    private final MyFileUtils myFileUtils;

    // 운영중인 블로그 리스트

    // 대표 블로그 설정

    // 블로그 생성
    public BlogInfoRp registerBlog(BlogRegisterRq dto, MultipartFile blogProfileImg, UserEntity user){
        BlogEntity blogEntity = BlogEntity.builder()
                .userEntity(user)
                .blogTitle(dto.getBlogTitle())
                .blogAddress(dto.getBlogAddress())
                .blogNickname(dto.getBlogNickname())
                .build();
        blogRepository.saveAndFlush(blogEntity);
        String target = "/blog/" + blogEntity.getBlogPk();
        String fileNm = myFileUtils.transferTo(blogProfileImg, target);
        blogEntity.changeBlogPic(fileNm);

        return BlogInfoRp.builder()
                .blogPk(blogEntity.getBlogPk())
                .blogInfo(blogEntity.getBlogInfo())
                .blogTitle(blogEntity.getBlogTitle())
                .blogAddress(blogEntity.getBlogAddress())
                .blogPic(blogEntity.getBlogPic())
                .blogNickname(blogEntity.getBlogNickname())
                .build();
    }

    // 블로그 삭제
    public void closeBlog(String blogAddr, UserEntity user){
        BlogEntity blogEntity = checkUserBlog(blogAddr,user);
        blogRepository.delete(blogEntity);
    }

    private BlogEntity checkUserBlog(String  blogAddr, UserEntity user) {
        return blogRepository.findByBlogAddressAndUserEntity(blogAddr, user)
                .orElseThrow(() -> new CustomException(BlogErrorCode.NOT_FOUND_BLOG));
    }
}
