package com.word.search.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridServiceImpl implements WordGridService {

    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE
    }

    private static class Coordinate {
        private final int x;
        private final int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    @Override
    public char[][] generateGrid(int gridSize, List<String> words) {

        char[][] contents = new char[gridSize][gridSize];
        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
              coordinates.add(new Coordinate(i, j));
              contents[i][j] = '-';
            }
        }
        Collections.shuffle(coordinates);
        for (String word : words) {

            for (Coordinate coordinate : coordinates) {

                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getDirectionForFit(contents, word, coordinate);

                if (selectedDirection != null) {
                    switch (selectedDirection) {

                        case HORIZONTAL:
                            for (char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                            break;
                    }
                    break;
                }

            }
        }
        randomFillGrid(contents);
        return contents;
    }

    private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {

        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);

        for (Direction direction : directions) {

            if (doesFit(contents, word, coordinate, direction)) {
                return direction;
            }
        }

        return null;
    }

    private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {

        int wordLength = word.length();
        int gridLength = contents[0].length;
        switch (direction) {
            case HORIZONTAL:
                if (wordLength + coordinate.y > gridLength) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y + i] != '-') return false;
                }
                break;

            case VERTICAL:
                if (wordLength + coordinate.x > gridLength) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y] != '-') return false;
                }
                break;

            case DIAGONAL:
                if (wordLength + coordinate.y > gridLength || wordLength + coordinate.x > gridLength) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y + i] != '-') return false;
                }
                break;
            case HORIZONTAL_INVERSE:
                if (coordinate.y < wordLength) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y - i] != '-') return false;
                }
                break;
            case VERTICAL_INVERSE:
                if (coordinate.x < wordLength) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y] != '-') return false;
                }
                break;

            case DIAGONAL_INVERSE:
                if (coordinate.y < wordLength || coordinate.x < wordLength) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y - i] != '-') return false;
                }
                break;

        }
        return true;

    }

    private void randomFillGrid(char[][] contents) {
        String allCapitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int gridLength = contents[0].length;
        for (int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridLength; j++) {

                if (contents[i][j] == '-') {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapitalLetters.length());
                    contents[i][j] = allCapitalLetters.charAt(randomIndex);
                }
            }

        }
    }

}
