package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.manager.FilmManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmManager manager;

    //Метод getAll, выводит все имеющиеся фильмы
    @GetMapping("/getAll")
    private FilmGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    //Метод searchByName, совершает поиск фильмов по имени
    @RequestMapping("/searchByName")
    public FilmSearchByNameResponseDTO searchByName(@RequestParam String text) {
        return manager.searchByName(text);
    }

    //Метод searchByGenre, совершает поиск фильмов по жанру
    @RequestMapping("/searchByGenre")
    public FilmSearchByGenreResponseDTO searchByGenre(@RequestParam String text) {
        return manager.searchByGenre(text);
    }

    //Метод getById, выводит фильм по заданному id
    @GetMapping("/getById")
    private FilmGetByIdResponseDTO getByIdFromParam(@RequestParam long id) {
        return manager.getById(id);
    }

    //Метод getById, выводит фильм по id, альтернативы предыдущему методу, отличия в анотациях
    @GetMapping("/getById/{id}")
    private FilmGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
        return manager.getById(id);
    }

    //Метод save, добавляет новый фильм к списку или изменяет имеющийся. В зависимости от заданного id
    @PostMapping("/save")
    private FilmSaveResponseDTO save(@RequestBody FilmSaveRequestDTO requestDTO) {
        return manager.save(requestDTO);
    }

    //Метод removeById, удаляет(добавляет в корзину) фильм по заданному id
    @PostMapping("/removeById")
    private void removeByIdFromParam(@RequestParam long id) {
        manager.removeById(id);
    }

    //Метод removeById, удаляет(добавляет в корзину) фильм по id, альтернатива предыдущему методу, отличия в анотации
    @PostMapping("/removeById/{id}")
    private void removeByIdFromPath(@PathVariable long id) {
        manager.removeById(id);
    }

    //Метод restoreById, востанавливает(возвращает из карзины в список) фильм по заданному id
    @PostMapping("/restoreById")
    private void restoreByIdFromParam(@RequestParam long id) {
        manager.restoreById(id);
    }
}

