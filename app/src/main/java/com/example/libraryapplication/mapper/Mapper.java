package com.example.libraryapplication.mapper;

import com.example.libraryapplication.dto.LibraryDTO;
import com.example.libraryapplication.models.Library;

public class Mapper {
    public static Library dtoToModel(LibraryDTO dto) {
        return new Library(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getOpenTime(),
                dto.getCloseTime()
        );
    }

    public static LibraryDTO modelToDto(Library model) {
        return new LibraryDTO(
                model.getId(),
                model.getName(),
                model.getAddress(),
                model.getOpenTime(),
                model.getCloseTime()
        );
    }
}
