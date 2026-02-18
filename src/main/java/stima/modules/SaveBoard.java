package main.java.stima.modules;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveBoard {

    public static void saveToFile(char[][] board, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            int n = board.length;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    writer.write(board[i][j]);
                }
                writer.newLine();
            }

            System.out.println("Board berhasil disimpan ke: " + filePath);

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan file.");
            e.printStackTrace();
        }
    }
}