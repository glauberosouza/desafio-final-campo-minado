package br.com.projeto.model;

import br.com.projeto.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int lines;
    private int columns;
    private int mine;
    private List<Field> fields = new ArrayList<>();

    public Board(int lines, int columns, int mine) {
        this.lines = lines;
        this.columns = columns;
        this.mine = mine;

        generateFields();
        drawMines();
        associateNeighbors();
    }

    //DONE: Criar o método -> GENERATE_FIELDS|VOID|
    public void generateFields() {
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                fields.add(new Field( line, column));
            }
        }
    }

    //DONE: Criar o método -> ASSOCIATE_NEIGHBORS|VOID|
    public void associateNeighbors() {
        for (var field : fields) {
            for (var neighbor : fields) {
                field.addNeighbor(neighbor);
            }
        }
    }

    //DONE: Criar o método -> DRAW_MINES|VOID|
    public void drawMines() {
        long minedFields = 0;
        do {
            var random = new Random().nextInt(fields.size());
            fields.get(random).setMined();
            minedFields = fields.stream().filter(Field::isMined).count();

        } while (minedFields < mine);
    }
    public boolean goalAchieved() {
        return fields.stream().allMatch(Field::goalAchieved);
    }

    //DONE: Criar o método -> +RESTART_GAME|BOOLEAN|
    public void restartGame() {
        for (Field field : fields) {
            field.restart();
        }
        drawMines();
    }

    //DONE: Criar o método -> +OPEN(int line, int column)|VOID|
    public void open(int line, int column) {
        try{
            fields.parallelStream()
                    .filter(field -> field.getLine() == line && field.getColumn() == column)
                    .findFirst().ifPresent(Field::open);
        }catch (ExplosionException e){
            for (Field field : fields){
                field.setOpened(true);
            }
            throw e;
        }
    }


    //DONE: Criar o método -> +SWITCH_MARKED(int line, int column)|VOID|
    public void SwitchMarked(int line, int column) {
        fields.parallelStream()
                .filter(field -> field.getLine() == line && field.getColumn() == column)
                .findFirst()
                .ifPresent(Field::SwitchMarked);
    }

    //DONE: Criar o método -> +TO_STRING|VOID|
    public void boardToString() {
        StringBuilder board = new StringBuilder();
        board.append("  0  1  2  3  4  5\n");
        for (var i = 0; i < lines; i++) {
            board.append(i).append("");
            for (var j = 0; j < columns; j++) {
                board.append(" ? ");
            }
            board.append("\n");
        }
        System.out.println(board);
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append("  ");
        for (int column = 0; column < columns; column++){
            board.append(" ");
            board.append(column);
            board.append(" ");
        }
        board.append("\n");
        var i = 0;
        for (var line = 0; line < lines; line++){
            board.append(line);
            board.append(" ");
            for (var column = 0; column < columns; column++){
                board.append(" ");
                board.append(fields.get(i));
                board.append(" ");
                i++;
            }
            board.append("\n");
        }
        return board.toString();
    }
}