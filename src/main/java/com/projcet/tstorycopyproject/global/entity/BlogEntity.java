package com.projcet.tstorycopyproject.global.entity;

import com.projcet.tstorycopyproject.global.entity.base.BaseEntity;
import com.projcet.tstorycopyproject.page.manage.request.BlogModifyRq;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_blog")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk", referencedColumnName = "userPk", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String blogTitle;

    private String blogInfo;

    @Column(nullable = false,updatable = false,unique = true)
    private String blogAddress;

    private String blogPic;

    @Column(nullable = false)
    private String blogNickname;

    // 대표블로그
    @ColumnDefault("'0'")
    private Long blogRep;

    /*@ColumnDefault("'0'")
    @Column(nullable = false)
    private Long guestBookOpen;

    @ColumnDefault("'0'")
    @Column(nullable = false)
    private Long guestBookOnlyLogin;*/

    @OneToMany(mappedBy = "blogEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.PERSIST
            , orphanRemoval = true)
    private List<CategoryEntity> categoryEntityList = new ArrayList<>();

    public void modifyCategoryEntityList(List<CategoryEntity> categoryEntityList){
        this.categoryEntityList.clear();
        this.categoryEntityList.addAll(categoryEntityList);
    }

    public void changeBlogPic (String saveFileName) {
        this.blogPic = saveFileName;
    }

    public void modifyBlogInfo(BlogModifyRq dto){
        if(!dto.getBlogInfo().isEmpty()){
            this.blogInfo = dto.getBlogInfo();
        }
        if (!dto.getBlogNickname().isEmpty()){
            this.blogNickname = dto.getBlogNickname();
        }
        if (!dto.getBlogTitle().isEmpty()){
            this.blogTitle = dto.getBlogTitle();
        }
    }
}
