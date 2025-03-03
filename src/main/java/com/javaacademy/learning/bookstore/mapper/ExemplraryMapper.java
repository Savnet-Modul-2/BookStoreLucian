package com.javaacademy.learning.bookstore.mapper;


import com.javaacademy.learning.bookstore.dto.ExemplaryCreateDTO;
import com.javaacademy.learning.bookstore.entities.Exemplary;
import com.javaacademy.learning.bookstore.dto.ExemplraryDTO;

import java.util.ArrayList;
import java.util.List;

public class ExemplraryMapper {

    public static ExemplraryDTO exemplary2exemplaryDTO(Exemplary exemplary) {
        ExemplraryDTO exemplraryDTO = new ExemplraryDTO();
        exemplraryDTO.setId(exemplary.getId());
        exemplraryDTO.setEditura(exemplary.getEditura());
        exemplraryDTO.setDurataMaximaRezervare(exemplary.getDurataMaximaRezervare());
        exemplraryDTO.setBook(exemplary.getBook());
        return exemplraryDTO;

    }

    public static List<Exemplary> exemplariesCreateDTO2exemplaries(ExemplaryCreateDTO exemplaryCreateDTO) {
        List<Exemplary> exemplaries = new ArrayList<>();
        for (int i = 0; i < exemplaryCreateDTO.getNumarExemplare(); i++) {
            Exemplary exemplary = new Exemplary();
            exemplary.setEditura(exemplaryCreateDTO.getEditura());
            exemplary.setDurataMaximaRezervare(exemplaryCreateDTO.getDurataMaximaRezervare());
            exemplaries.add(exemplary);
        }
        return exemplaries;
    }


}
