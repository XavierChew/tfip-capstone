package com.gotbufffetleh.backend.controller;


import com.gotbufffetleh.backend.dto.CatererDTO;
import com.gotbufffetleh.backend.processor.CatererProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caterer")
@CrossOrigin(origins = "http://localhost:5173")
public class CatererController {
    private final CatererProcessor catererProcessor;

    //autowire
    public CatererController(CatererProcessor catererProcessor) {
        this.catererProcessor = catererProcessor;
    }


//    @GetMapping("/catererPage")
//    public ResponseEntity<CatererDTO> getCaterer(@RequestParam("catererId") long catererId) {
//
//        CatererDTO caterer = this.catererProcessor.findByCatererId(catererId);
//
//    }
}
