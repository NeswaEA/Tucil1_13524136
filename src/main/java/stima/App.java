package stima;

import main.java.stima.modules.*;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("SELAMAT DATANG DI QUEENSSOLVER!");
        System.out.println("Masukkan alamat file txt: ");
        String fileLocation = scan.nextLine();
        System.out.println("");
        try {
            LoadBoard load = new LoadBoard(fileLocation);

            System.out.println("Board berhasil dimuat.");
            System.out.println("");
            
            load.printBoard();
            System.out.println("");

            System.out.println("Mencari solusi...");
            System.out.println("");
            
            char[][] board = load.getBoard();
             
            QueensSolver solver = new QueensSolver(board);

            if (solver.solve()){
                System.out.println("Apakah anda ingin menyimpan solusinya? y / n");
                String ans = scan.nextLine();
                if (ans.equals("y")){
                    System.out.println("Masukkan alamat penyimpanan: ");
                    String saveLoc = scan.nextLine();
                    SaveBoard.saveToFile(board, saveLoc);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
