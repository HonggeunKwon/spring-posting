package com.example.springboard.mapper;

import com.example.springboard.domain.common.enums.Status;
import com.example.springboard.domain.member.dto.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {
    @Autowired
    MemberMapper memberMapper;

    private List<Member> members;

    @BeforeEach
    public void beforeEach() {
        members = new ArrayList<>();
        Member member1 = Member.createMember("test-1", "test1", "test1");
        Member member2 = Member.createMember("test-2", "test2", "test2");
        Member member3 = Member.createMember("test-3", "test3", "test3");
        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.forEach(arg -> {
            memberMapper.insert(arg);
        });
    }

    @Test
    public void changeStatus() {
        Member member = members.get(0);
        memberMapper.changeStatus(member.getId(), Status.DELETED);
        Member byId = memberMapper.findById(member.getId());
    }
}