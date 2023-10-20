package my.arts.arts5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XieLe on 2023/10/20
 */
public class Solution {

    /**
     * Z字形变换
     *
     * @param s 输入字符串
     * @param numRows 行数
     */
    public String convert(String s, int numRows) {
        if (numRows < 2) { // 只有1行
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        // 初始化表格
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }
        int i = 0, flag = -1; // flag=-1 比较巧妙
        for (char c : s.toCharArray()) {
            StringBuilder row = rows.get(i); // 当前行
            row.append(c);
            if (i == 0 || i == numRows - 1) { // 改变遍历方向
                flag = -flag;
            }
            i += flag; // 下一行
        }
        // 最后将表格rows按行输出
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) {
            res.append(row);
        }
        return res.toString();
    }

}
