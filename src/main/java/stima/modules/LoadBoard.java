package main.java.stima.modules;

import java.io.*;
import java.util.*;

public class LoadBoard {

    private char[][] board;
    private int size;
    private Set<Character> regions;

    public LoadBoard(String filePath) throws IOException {
        loadTXT(filePath);
        validateStructure();
    }

    public char[][] getBoard() {
        return board;
    }

    private void loadTXT(String filePath) throws IOException {

        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        br.close();

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("File kosong.");
        }

        size = lines.size();

        if (size > 26) {
            throw new IllegalArgumentException("Ukuran maksimum adalah 26x26.");
        }

        board = new char[size][size];
        regions = new HashSet<>();

        for (int i = 0; i < size; i++) {

            if (lines.get(i).length() != size) {
                throw new IllegalArgumentException("Board harus berbentuk matriks n x n.");
            }

            for (int j = 0; j < size; j++) {
                char c = lines.get(i).charAt(j);

                if (!Character.isUpperCase(c)) {
                    throw new IllegalArgumentException("Board hanya boleh berisi huruf A-Z (kapital).");
                }

                board[i][j] = c;
                regions.add(c);
            }
        }

        if (regions.size() > size){
            throw new IllegalArgumentException("Jumlah region melebihi ukuran.");
        }
    }

    private void validateStructure() {
        if (size <= 3 || size > 26) {
            throw new IllegalArgumentException("Ukuran board harus 4 <= n <= 26.");
        }
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}

