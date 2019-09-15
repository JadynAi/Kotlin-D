package com.jadynai.kotlindiary;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @version:
 * @FileDescription:
 * @Author:Jing
 * @Since:2019-08-07
 * @ChangeList:
 */
public class TTT {

    private static volatile TTT sSingleton = null;

    private TTT() {
    }

    public static TTT getInstance() {
        if (sSingleton == null) {
            synchronized (TTT.class) {
                if (sSingleton == null) {
                    sSingleton = new TTT();
                }
            }
        }

        int[] sfsf = {1, 2};

        new Stack<Integer>().peek();

        ArrayList<Integer> ins = new ArrayList<>();
        for (Integer i : ins) {
            i.byteValue();
        }
        return sSingleton;
    }

}
