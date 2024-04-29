package com.example.demo21.repositoryTest;

import com.example.demo21.entity.Memo;
import com.example.demo21.repository.MemoRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        log.info(memoRepository.getClass().getName());
    }

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1, 100).forEach(
                value -> {
                    Memo memo = Memo.builder()
                            .memoText("sample" + value)
                            .build();

                    memoRepository.save(memo);
                    //repository에서 save는 기본적으로 entity타입을 가져온다.
                    //repository를 만들때 JpaRepository<사용할 Class타입 이거이거이거, pk타입>
                });


    }

    @Test
    public void testSelect(){
        Long mno = 13L;

        Optional<Memo> memo = memoRepository.findById(mno);

        log.info(memo.orElseThrow());   //orElseThrow 값이 없다면 에러
//        log.info(memo.get());
        log.info("출력");
    }

    @Test
    public void testUpdate(){
        Memo memo = new Memo(10L, "수정스");

        log.info(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){

        memoRepository.deleteById(11L);
        memoRepository.deleteById(10L);
    }

    @Test
    public void testAll(){
        List<Memo> list = memoRepository.findAll(); //전체 검색 반환타입
                                                    //내가 만든 반환타입 List형태

        list.forEach(memo -> log.info(memo));


    }

    @Test
    public void testPaging(){

        //1페이지 10개씩
        //페이징 처리만
//        Pageable pageable = PageRequest.of(0, 5);
        //정렬 조건 추가
        Sort sort = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(1, 5, sort);
//        Pageable pageable = PageRequest.of(1, 5, Sort.Direction.DESC);

//        Page<Memo> memos = memoRepository.findAll(pageable);
        Page<Memo> memos = memoRepository.findAll(pageable);

        //정렬 조건 추가
        memos.forEach(memo -> log.info(memo));

        log.info("총 페이지 : " + memos.getTotalPages());//총 몇 페이지인가
        log.info("전체 row수 : " + memos.getTotalElements());//전체 개수 row
        log.info("현제 페이지 : " + memos.getNumber());//현재 페이지 번호
        log.info("한 페이지 출력수 : " + memos.getSize());//현제 페이지의 크기 몇개씩 보여주는지
        log.info("다음 페이지 존재 여부 : " + memos.hasNext());//다음 페이지 존재 여부
        log.info("시작 페이지 여부 : " + memos.isFirst());//시작 페이지 여부
    }


    @Test
    public void testfindByMno(){

        Memo memo = memoRepository.findByMno(3L);
        log.info(memo);

//        log.info(memoRepository.findByMemoText("sample20"));
        log.info(memoRepository.findByMemoTextContains("sample20"));
        log.info(memoRepository.findByMemoTextContainsOrMno("e1", 4L));
        log.info(memoRepository.findByMnoBetweenOrderByMnoDesc(3L, 10L));
    }

    @Test
    public void searchtest(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        List<Memo> memoList = memoRepository.findByMnoBetween(34L, 87L, pageable);

        memoList.forEach(memo -> log.info(memo));


    }

}
