package arts5

import "strings"

func convert(s string, numRows int) string {
	if numRows < 2 {
		return s
	}
	// 申明一个二维表格,每行都是一个字符串Builder
	rows := make([]strings.Builder, numRows)
	var i int = 0
	var flag int = -1
	for _, c := range s {
		rows[i].WriteString(string(c))
		if i == 0 || i == numRows-1 {
			flag = -flag
		}
		i += flag
	}
	var res strings.Builder

	for _, row := range rows {
		res.WriteString(row.String())
	}
	return res.String()
}
