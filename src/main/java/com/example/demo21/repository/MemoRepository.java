package com.example.demo21.repository;

import com.example.demo21.entity.Memo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    //기존 dao, mapper과 같은 역할을 한다.
    //단 JpaRepository를 상속함에 따라 jpa를 사용해서 db에 접근가능하다.
    //jpa java 표준 orm 기술이다.
    //JpaRepository<사용할 class, pk타입>
    //findbyId(id) 찾기 id로 찾기 읽기getOne   //select
    //findbyAll 찾기 전부 찾기            //select
    //save(entity) 저장                  //insert
    //save(entity) 수정                   //update
    //deletebyId(id) 삭제                     /delete

    //페이징 / 정령 처리하기
    //jpa에서는 페이징 처리를 pageable를 사용하여 페이징 처리를 합니다.
    //sql 코드는 기존 limit를 사용합니다.

    //Query Methods

    Memo findByMno (Long a);
    Memo findByMemoText (String memoText);

    //select * from memo where memo_text like concat('%', #{memoText}, '%')
    List<Memo> findByMemoTextContains (String memoText);

    List<Memo> findByMemoTextContainsOrMno(String memoText, Long mno);

    //select * from memo where
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long a, Long b);

    List<Memo> findByMnoBetween(Long a, Long b, Pageable pageable);
    List<Memo> findByMemoTextContainsAndMnoBetween(String a, int b, int c);

}
