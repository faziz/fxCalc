
export class Result {
    public covertedAmount: number;
    /** Indicates if the calculation was successful or not. **/
    public successful: boolean;
    /** Error message if conversion fails. **/
    public errorMessage:string ;
    /** Message to show after successful conversion. **/
    public message:string;
}