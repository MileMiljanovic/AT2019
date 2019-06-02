import { AType } from "./atype";
import { ACenter } from "./acenter";

export interface AID{
    name: string,
    host: ACenter,
    type: AType
}