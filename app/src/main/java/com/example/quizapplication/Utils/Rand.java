package com.example.quizapplication.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rand {

    public List<Integer> randomNumber(int from, int to)
    {
        Random random = new Random();
        List<Integer>holder = new ArrayList<>();
        List<Integer>number = new ArrayList<>();

        for(int i=from;i<=to;i++)
        {
            holder.add(i);
        }
        while(holder.size()>0){
            int index = random.nextInt((holder.size()));
            number.add(holder.remove(index));
        }
        return number;
    }
}
