package com.hw.githubclient.mvp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
    private List<Integer> counters = new ArrayList<>(Arrays.asList(0,0,0));

    public int getCurrent(int index){
        return counters.get(index);
    }

    public void set(int index, int value){
        counters.set(index, value);
    }

    /**
     * Инкремент счетчика
     * @param index - индекс счетчика
     * @return - инкрементированное значение
     */
    public int next(int index){
        Integer nextValue = counters.get(index) + 1;
        counters.set(index,nextValue);
        return nextValue;
    }
}
