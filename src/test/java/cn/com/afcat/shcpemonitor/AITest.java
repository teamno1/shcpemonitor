package cn.com.afcat.shcpemonitor;

import java.util.Scanner;

public class AITest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String question;
        while (true){
            question = scanner.next();
            question = question.replace("吗","");
            question = question.replace("我","我也");
            question = question.replace("吧","嗯");
            question = question.replace("？","!");


            System.out.println(question);
        }
    }
}
