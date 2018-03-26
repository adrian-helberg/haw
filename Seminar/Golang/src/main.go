package main

import (
	"fmt"
	"math"
)

type Shape interface {
Area() float64
}

type Circle struct {
radius float64
}

func (c Circle) Area() float64 {
	return math.Pi * c.radius * c.radius
}

func main() {
var shape Shape = Circle{2}
fmt.Println(shape.Area())
}