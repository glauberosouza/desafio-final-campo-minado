package br.com.projeto;

import br.com.projeto.model.Board;
import br.com.projeto.vision.BoardConsole;

public class App {
    public static void main(String[] args) {
        Board board = new Board(6, 6, 10);
        new BoardConsole(board);
    }
}