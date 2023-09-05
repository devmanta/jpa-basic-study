package com.example.jpabook.jpashop.service;

import com.example.jpabook.jpashop.domain.Member;
import com.example.jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // == @RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    void create_member() throws Exception {
        Member m = new Member();
        m.setName("manta");

        Long saveId = memberService.join(m);

        assertEquals(m, memberRepository.findOne(saveId));
    }

    @Test
    void duplicated_member() throws Exception {
        Member m1 = new Member();
        m1.setName("manta");

        Member m2 = new Member();
        m2.setName("manta");

        memberService.join(m1);

        assertThrows(IllegalStateException.class, () ->
                memberService.join(m2));
    }

}