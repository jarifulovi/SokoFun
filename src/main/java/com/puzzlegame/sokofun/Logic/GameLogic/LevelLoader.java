package com.puzzlegame.sokofun.Logic.GameLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.*;

public class LevelLoader {

    private int[][][] levelBoard;
    private int level;


    public LevelLoader(int level) {
        this.level = level;
        retrieveLevelFile();
    }

    public int[][][] getLevelBoard() {
        return this.levelBoard;
    }

    private void retrieveLevelFile() {
        int[][] groundLayer = loadLayer(LEVEL_DIR_PATH + "Level" + String.format("%02d", level) + GROUND_LAYER_FILE_PATH);
        int[][] blockLayer = loadLayer(LEVEL_DIR_PATH + "Level" + String.format("%02d", level) + BLOCK_LAYER_FILE_PATH);
        int[][] objectLayer = loadLayer(LEVEL_DIR_PATH + "Level" + String.format("%02d", level) + OBJECT_LAYER_FILE_PATH);

        mergeLayer(groundLayer, blockLayer, objectLayer);
    }

    private int[][] loadLayer(String path) {
        List<int[]> layerList = new ArrayList<>();

        try (InputStream inputStream = LevelLoader.class.getResourceAsStream(path)) {

            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.trim().split("\s+");
                    int[] row = new int[tokens.length];
                    for (int i = 0; i < tokens.length; i++) {
                        row[i] = Integer.parseInt(tokens[i]);
                    }
                    layerList.add(row);
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return layerList.toArray(new int[0][]);
    }

    private void mergeLayer(int[][] groundLayer, int[][] blockLayer, int[][] objectLayer) {
        int rows = groundLayer.length;
        int cols = groundLayer[0].length;
        levelBoard = new int[3][rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                levelBoard[0][row][col] = groundLayer[row][col];
                levelBoard[1][row][col] = blockLayer[row][col];
                levelBoard[2][row][col] = objectLayer[row][col];
            }
        }
    }

    public static void main(String[] args) {
        LevelLoader levelLoader = new LevelLoader(1);
        for(int i = 0;i < levelLoader.levelBoard.length; i++){
            for(int j = 0;j < levelLoader.levelBoard[0].length; j++) {
                for(int k = 0;k < levelLoader.levelBoard[0][0].length; k++) {
                    System.out.print(levelLoader.levelBoard[i][j][k]+ " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}