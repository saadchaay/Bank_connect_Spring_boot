import {Customer} from "./customer";

export interface Request {
  id: number,
  typeAccount: String,
  customer: Customer,
  created: Date,
  updated: Date,
}
