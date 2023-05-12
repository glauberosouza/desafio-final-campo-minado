package br.com.projeto.vision;

import br.com.projeto.exception.ExitException;
import br.com.projeto.exception.ExplosionException;
import br.com.projeto.model.Board;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BoardConsole {
    private Board board;
    private Scanner input = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;
        playGame();
    }

    private void playGame() {
        try {
            var gameRuning = true;
            gameLoop();
            System.out.println("Outra partida? (S/n)");
            var answer = input.nextLine();
            if ("n".equalsIgnoreCase(answer)) {
                gameRuning = false;
            } else {
                board.restartGame();
            }
        } catch (ExitException e) {
            System.out.println("See ya!");
        } finally {
            input.close();
        }
    }

    private void gameLoop() {
        try {
            while (!board.goalAchieved()) {
                System.out.println(board);
                var response = captureTypedValue("Digite (x, y)").trim();

                List<Integer> responseXY = Arrays.stream(response.split(","))
                        .map(s -> Integer.parseInt(s.trim()))
                        .collect(Collectors.toList());

                response = captureTypedValue(("1 - Abrir ou 2 - (Des)Marcar: "));

                if ("1".equalsIgnoreCase(response)) {
                    board.open(responseXY.get(0), responseXY.get(1));
                } else if ("2".equalsIgnoreCase(response)) {
                    board.SwitchMarked(responseXY.get(0), responseXY.get(1));
                }
            }
            System.out.println(board);
            System.out.println("You Won!!!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("You loosed");
        }


    }

    private String captureTypedValue(String text) {
        System.out.println(text);
        String response = input.nextLine();
        if ("sair".equalsIgnoreCase(response)) {
            throw new ExitException();
        }
        return response.toLowerCase();
    }
}