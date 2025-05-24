package fz.chitfund.controller;

import fz.chitfund.dto.MemberRequest;
import fz.chitfund.entity.Member;
import fz.chitfund.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chit/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.addMember(request));
    }

    @PostMapping("/without-scheme")
    public ResponseEntity<Member> addMemberWithoutScheme(@RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.addMemberWithoutScheme(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Member>> getMembersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.getMembersByUser(userId));
    }

    @GetMapping("/scheme/{schemeId}")
    public ResponseEntity<List<Member>> getMembersByScheme(@PathVariable Long schemeId) {
        return ResponseEntity.ok(memberService.getMembersByScheme(schemeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id) {
        memberService.removeMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
}
