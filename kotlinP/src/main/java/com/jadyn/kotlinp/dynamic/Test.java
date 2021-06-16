package com.jadyn.kotlinp.dynamic;

/**
 * JadynAi since 6/11/21
 */
class Test {

//    public static String main(String formula) {
//        Map<String, Integer> element_map = new HashMap<String, Integer>();
//        int index = count_element(formula, 0, element_map);
//
//        String result_output = "";
//
//        for (Map.Entry<String, Integer> entry : element_map.entrySet()) {
//            result_output += entry.getKey();
//            result_output += entry.getValue();
//        }
//        return result_output;
//    }
//
//    public static int count_element(String formula, int index, Map<String, Integer> element_map) {
//        int formula_size = formula.length();
//        if (index >= formula_size) {
//            return index + 1;
//        }
//        while (index < formula_size) {
//            if (formula.charAt(index) == '(') {
//                Map<String, Integer> temp_element_map = new HashMap<String, Integer>();
//                index = index + 1;
//                int cnt = 0;
//                while (index < formula_size && formula.charAt(index) >= '0' && formula.charAt(index) <= '9') {
//                    cnt = cnt * 10 + formula.charAt(index++) - '0';
//                }
//                if (cnt > 0) {
//                    for (Map.Entry<String, Integer> entry : temp_element_map.entrySet()) {
//                        entry.setValue(entry.getValue() * cnt);
//                    }
//                }
//                for (Map.Entry<String, Integer> entry : temp_element_map.entrySet()) {
//                    if (_____(3) _____)
//                    element_map.put(entry.getKey(), element_map.get(entry.getKey()) + entry.getValue());
//                     else
//                    element_map.put(entry.getKey(), entry.getValue());
//                }
//            } else if (formula.charAt(index) == ')') {
//                _____(4) _____;
//            } else {
//                String element_name = String.valueOf(formula.charAt(index++));
//                while (index < formula_size && formula.charAt(index) >= 'a' && formula.charAt(index) <= 'z') {
//                    _____(5) _____;
//                }
//                int cnt = 0;
//                while (index < formula_size && formula.charAt(index) >= '0' && formula.charAt(index) <= '9') {
//                    cnt = cnt * 10 + formula.charAt(index++) - '0';
//                }
//                if (cnt > 0) {
//                    if (element_map.containsKey(element_name))
//                        element_map.put(element_name, element_map.get(element_name) + cnt);
//                    else
//                        element_map.put(element_name, cnt);
//                } else {
//                    if (element_map.containsKey(element_name))
//                        element_map.put(element_name, element_map.get(element_name) + 1);
//                    else
//                        element_map.put(element_name, 1);
//                }
//            }
//        }
//        return index;
//    }

    public static boolean judgeWordIsInMatrix(String word) {
        if (word == null) {
            return false;
        }
        String wordResult = word.trim();
        if (wordResult.length() == 0) {
            return false;
        }
        char[][] matrix = {
                {'W', 'O', 'T', 'E'},
                {'S', 'R', 'D', 'G'},
                {'E', 'C', 'H', 'V'},
                {'A', 'R', 'L', 'O'},
        };
        int x = -1;
        int y = -1;
        char firstChar = word.charAt(0);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (firstChar == matrix[i][j]) {
                    x = j;
                    y = i;
                    break;
                }
            }
        }
        if (x == -1 || y == -1) {
            return false;
        }
        
        for (int i = 1; i < word.length(); i++) {
            if (x + 1 < matrix.length && word.charAt(i) == matrix[x + 1][y]) {
                x++;
            } else if (y + 1 < matrix[i].length && word.charAt(i) == matrix[x][y + 1]) {
                y++;
            } else {
                return false;
            }
        }
        return true;
    }
}
