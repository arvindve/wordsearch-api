package com.word.search.services;

import java.util.List;

public interface WordGridService {

    public char[][] generateGrid(int gridSize, List<String> words);
}
