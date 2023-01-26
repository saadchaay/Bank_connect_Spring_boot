import {Customer} from "./customer";

export interface Account {
  id: number,
  number: String,
  type: String,
  balance: number,
  customer: Customer,
}
