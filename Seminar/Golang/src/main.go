package main

import (
	"fmt"
	"strconv"
)

func intSeq(x string) func() string {
	i := 0
	return func() string {
		i++
		return x + ": " + strconv.Itoa(i)
	}
}

func main() {
	nextInt := intSeq("Test 1")

	fmt.Println(nextInt())
	fmt.Println(nextInt())
}