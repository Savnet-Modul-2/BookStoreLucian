package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.dto.ExemplaryCreateDTO;
import com.javaacademy.learning.bookstore.dto.ExemplraryDTO;
import com.javaacademy.learning.bookstore.entities.Exemplary;
import com.javaacademy.learning.bookstore.mapper.ExemplraryMapper;
import com.javaacademy.learning.bookstore.repository.ExemplaryRepository;
import com.javaacademy.learning.bookstore.service.ExemplaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplary")
public class ExemplaryController {
    @Autowired
    private ExemplaryService exemplaryService;
    @Autowired
    private ExemplaryRepository exemplaryRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ExemplaryCreateDTO exemplaryCreateDTO) {
        List<Exemplary> exemplariesToCreate = ExemplraryMapper.exemplariesCreateDTO2exemplaries(exemplaryCreateDTO);
        List<Exemplary> createdExemplaries = exemplaryService.create(exemplariesToCreate, exemplaryCreateDTO.getBookID());
        return ResponseEntity.ok(createdExemplaries.stream().map(ExemplraryMapper::exemplary2exemplaryDTO).toList());

    }

    @GetMapping
    public ResponseEntity<?> listPaginated(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "5") Integer numberOfElements) {

        Page<Exemplary> exemplaries = exemplaryService.listPaginated(pageNumber, numberOfElements);
        List<ExemplraryDTO> exemplraryDTOS = exemplaries.stream()
                .map(ExemplraryMapper::exemplary2exemplaryDTO)
                .toList();

        return ResponseEntity.ok(exemplraryDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        exemplaryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
