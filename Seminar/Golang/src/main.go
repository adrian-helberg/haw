package main

import "fmt"
import "reflect"

type Person struct {
	Vorname string
	Nachname string
}

func main() {
	p := Person{"Hans", "Peter"}
	fmt.Println(getField(&p, "Vorname"))
}

func getField(p *Person, field string) string {
	r := reflect.ValueOf(p)
	f := reflect.Indirect(r).FieldByName(field)
	return f.String()
}