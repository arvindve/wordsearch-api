package com.word.search.controllers;

import com.word.search.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class WordGridController {

    @Autowired
    private WordGridService wordGridService;

    @GetMapping("wordgrid")
    @CrossOrigin(origins = "http://localhost:1234")
    public String createWordGrid(@RequestParam int gridSize, @RequestParam String words) {
        List<String> wordList = Arrays.asList(words.split(","));
        char[][] grid = wordGridService.generateGrid(gridSize, wordList);
        StringBuilder gridToString = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString.append(grid[i][j]).append(" ");
            }
            gridToString.append("\r\n");
        }
        return gridToString.toString();
    }
}
