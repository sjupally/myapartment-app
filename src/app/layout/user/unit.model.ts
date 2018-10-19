export class Unit {
  public id: number;
  public block: string;
  public floor: string;
  public flatNo: string;
  public sqft: number;
  public parkingSlots: number;
  public parkingSqft: number;
  public billerName: string;
  public unitCategory: string;

  constructor(
    id: number,
    block: string,
    floor: string,
    flatNo: string,
    sqft: number,
    parkingSlots: number,
    parkingSqft: number,
    billerName: string,
    unitCategory: string
  ) {
    this.id = id;
    this.block = block;
    this.floor = floor;
    this.flatNo = flatNo;
    this.sqft = sqft;
    this.parkingSlots = parkingSlots;
    this.parkingSqft = parkingSqft;
    this.billerName = billerName;
    this.unitCategory = unitCategory;
  }
}
