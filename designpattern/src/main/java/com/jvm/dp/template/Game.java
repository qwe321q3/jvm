package com.jvm.dp.template;

public class Game extends AbstractGame {
    @Override
    public void initial() {
        System.out.println("初始化游戏！");
    }

    @Override
    public void choosePerson() {
        System.out.println("选择人物 !");
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

}
