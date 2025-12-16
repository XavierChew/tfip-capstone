package com.gotbufffetleh.backend.controller;


import com.gotbufffetleh.backend.dto.CatererDTO;
import com.gotbufffetleh.backend.dto.PaginatedCatererDTO;
import com.gotbufffetleh.backend.dto.TopCatererDTO;
import com.gotbufffetleh.backend.processor.CatererProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/caterer")
@CrossOrigin(origins = "http://localhost:5173")
public class CatererController {
    private final CatererProcessor catererProcessor;

    //autowire
    public CatererController(CatererProcessor catererProcessor) {
        this.catererProcessor = catererProcessor;
    }


    @GetMapping("/catererPage")
    public ResponseEntity<CatererDTO> getCaterer(@RequestParam("catererId") long catererId) {

        Optional<CatererDTO> caterer = this.catererProcessor.findByCatererId(catererId);
        if (caterer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(caterer.get(), HttpStatus.OK);

    }

    @GetMapping("/allCaterers")
    public Page<PaginatedCatererDTO> getCaterers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                                                 @RequestParam(defaultValue = "avgRating") String sortBy
                                                 ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return catererProcessor.getAllCaterers(pageable);

    }

    @GetMapping("/top3Caterers")
    public List<TopCatererDTO> getTop3Caterers(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "avgRating")  String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return catererProcessor.findTop3CatererByAvgRating();
    }


}
