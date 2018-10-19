import { Unit } from "./unit.model";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: "searchByName" })
export class SearchByNamePipe implements PipeTransform {
  transform(units: Array<Unit>, searchText: string) {
    return units.filter(unit => unit.billerName.indexOf(searchText) !== -1 || unit.flatNo.indexOf(searchText) !== -1);
  }
}
