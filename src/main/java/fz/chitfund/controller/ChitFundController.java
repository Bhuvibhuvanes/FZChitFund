package fz.chitfund.controller;

import org.springframework.web.bind.annotation.*;
import fz.chitfund.service.ChitFundService;
import fz.chitfund.entity.*;
import java.util.List;

@RestController
@RequestMapping("/api/chit")
public class ChitFundController {
    private final ChitFundService chitFundService;

    public ChitFundController(ChitFundService chitFundService) {
        this.chitFundService = chitFundService;
    }

    @PostMapping("/schemes")
    public ChitScheme createScheme(@RequestBody ChitScheme scheme) {
        return chitFundService.createScheme(scheme);
    }

    @PostMapping("/join/{userId}/{schemeId}")
    public ChitMember joinScheme(@PathVariable Long userId, @PathVariable Long schemeId) {
        return chitFundService.joinScheme(userId, schemeId);
    }

    @GetMapping("/schemes/active")
    public List<ChitScheme> getActiveSchemes() {
        return chitFundService.getActiveSchemes();
    }

    @GetMapping("/members/{userId}/schemes")
    public List<ChitMember> getMemberSchemes(@PathVariable Long userId) {
        return chitFundService.getMemberSchemes(userId);
    }

    @GetMapping("/schemes/{schemeId}")
    public ChitScheme getScheme(@PathVariable Long schemeId) {
        return chitFundService.getSchemeById(schemeId);
    }

    @GetMapping("/schemes")
    public List<ChitScheme> getAllSchemes() {
        return chitFundService.getAllSchemes();
    }

    @PutMapping("/schemes/{schemeId}")
    public ChitScheme updateScheme(@PathVariable Long schemeId, @RequestBody ChitScheme scheme) {
        return chitFundService.updateScheme(schemeId, scheme);
    }
} 
