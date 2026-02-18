package main.java.stima.modules;

import java.util.*;

public class QueensSolver {

    private char[][] board;
    private char[][] region;
    private int n;

    private List<Character> regionList;
    private Map<Character, List<int[]>> regionCells;

    // Statistik
    private long casesChecked = 0;
    private long startTime;
    private long endTime;

    public QueensSolver(char[][] inputBoard) {

        n = inputBoard.length;

        board = new char[n][n];
        region = new char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = inputBoard[i][j];
                region[i][j] = inputBoard[i][j];
            }
        }

        buildRegionData();
    }

    private void buildRegionData() {

        regionCells = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                char r = region[i][j];

                regionCells.putIfAbsent(r, new ArrayList<>());
                regionCells.get(r).add(new int[]{i, j});
            }
        }

        regionList = new ArrayList<>(regionCells.keySet());
        Collections.sort(regionList);
    }

    public boolean solve() {

        startTime = System.nanoTime();

        int R = regionList.size();

        int[] index = new int[R];
        int[] limits = new int[R];

        for (int i = 0; i < R; i++) {
            limits[i] = regionCells.get(regionList.get(i)).size();
        }

        while (true) {

            casesChecked++;

            if (casesChecked % 100000 == 0) {
                System.out.println((""));
                System.out.println("Live Update:");
                System.out.println("Iterasi ke: " + casesChecked);
                applyCombination(index);
                printBoard();
            }

            if (isValidCombination(index)) {
                applyCombination(index);
                endTime = System.nanoTime();

                if (casesChecked < 100000) {
                    System.out.println("");
                    System.out.println("Live Update (Terakhir)");
                    System.out.println("Iterasi terakhir: " + casesChecked);
                    printBoard();
                }

                System.out.println("");
                System.out.println("Hasil akhir:");
                System.out.println("");
                printBoard();
                System.out.println();
                printStats(true);
                return true;
            }

            int pos = R - 1;
            while (pos >= 0) {
                index[pos]++;
                if (index[pos] < limits[pos]) {
                    break;
                } else {
                    index[pos] = 0;
                    pos--;
                }
            }

            if (pos < 0) {
                break;
            }
        }

        endTime = System.nanoTime();
        printStats(false);
        return false;
    }

    private boolean isValidCombination(int[] index) {

        boolean[] usedRow = new boolean[n];
        boolean[] usedCol = new boolean[n];

        List<int[]> queens = new ArrayList<>();

        for (int i = 0; i < index.length; i++) {

            List<int[]> cells = regionCells.get(regionList.get(i));
            int[] cell = cells.get(index[i]);

            int row = cell[0];
            int col = cell[1];

            if (usedRow[row] || usedCol[col]) {
                return false;
            }

            usedRow[row] = true;
            usedCol[col] = true;

            queens.add(cell);
        }

        for (int i = 0; i < queens.size(); i++) {
            for (int j = i + 1; j < queens.size(); j++) {

                int r1 = queens.get(i)[0];
                int c1 = queens.get(i)[1];

                int r2 = queens.get(j)[0];
                int c2 = queens.get(j)[1];

                if (Math.abs(r1 - r2) == 1 &&
                    Math.abs(c1 - c2) == 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private void applyCombination(int[] index) {

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = region[i][j];

        for (int i = 0; i < index.length; i++) {

            List<int[]> cells = regionCells.get(regionList.get(i));
            int[] cell = cells.get(index[i]);

            board[cell[0]][cell[1]] = '#';
        }
    }

    private void printStats(boolean found) {

        double durationMs = (endTime - startTime) / 1_000_000.0;

        System.out.print("Status : ");
        if (found){
            System.out.println("Solusi ditemukan");
        } else{
            System.out.println("Solusi tidak ditemukan");
        }

        System.out.printf("Waktu pencarian  : %.3f ms\n", durationMs);
        System.out.println("Kasus ditinjau   : " + casesChecked + " kasus");
    }

    public void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}