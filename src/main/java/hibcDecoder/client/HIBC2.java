package hibcDecoder.client;

/**
 * Created by admin on 1/19/16.
 */
public class HIBC2 {
    private String barcode;
    private Decoded decoded;

    public HIBC2() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    // can add try/ catch for barcode when calling this method for not a string
    public Decoded decode(String barcode){
        decoded.barcode = barcode;

        if(barcode == null || barcode.isEmpty()){
            decoded.error = Error.EMPTY_BARCODE;
            return decoded;
        }

        // remove leading * and check
        if(barcode.startsWith("*")){
            barcode = barcode.substring(1);
            if(barcode.isEmpty()){
                decoded.error = Error.EMPTY_BARCODE;
                return decoded;
            }
        }

        // remove trailing * and check
        if(barcode.endsWith("*")){
            barcode = barcode.substring(0, barcode.length() - 1);
            if(barcode.isEmpty()){
                decoded.error = Error.EMPTY_BARCODE;
                return decoded;
            }
        }

        // check for + character
        if(barcode.charAt(0) != '+'){
            decoded.error = Error.BARCODE_NOT_HIBC;
            return decoded;
        } else {
            barcode = barcode.substring(1);
        }

        // check minimum barcode length
        if(barcode.length() < 4){
            decoded.error = Error.INVALID_BARCODE;
            return decoded;
        }

        String potentialCheckAndLinkCharacters = barcode.substring(barcode.length() - 2);
        barcode = barcode.substring(0, barcode.length() - 2);

        String[] lines = barcode.split("/");

        if(lines.length == 1){
            if(Character.isLetter(lines[0].charAt(0))){
                decoded = processLine1(decoded, Type.LINE_1, lines[0] + potentialCheckAndLinkCharacters);
            } else {
                decoded = processLine2(decoded, Type.LINE_2, lines[0] + potentialCheckAndLinkCharacters);
            }
            return decoded;

        }else if(lines.length == 2){
            decoded = processLine1(decoded, Type.CONCATENATED, lines[0]);


        } else {
            decoded.error = Error.INVALID_BARCODE;
            return decoded;
        }

        return decoded;

    }


    private Decoded processLine1(Decoded decoded, Type line1, String barcode) {
        decoded.type = line1;
        if(barcode.length() < 4){
            decoded.error = Error.INVALID_LINE_1;
            return decoded;
        }

        decoded.labelerId = barcode.substring(0, 4);
        barcode = barcode.substring(4);

        if(barcode.isEmpty()){
            decoded.error = Error.INVALID_LINE_1;
            return decoded;
        }

        // if type is (not?) Concatenated, the check char is in the second part of the string
        if (decoded.type != Type.CONCATENATED) {
            decoded.check = barcode.charAt(barcode.length() - 1);
            barcode = barcode.substring(0, barcode.length() - 1);
            if (barcode.isEmpty()) {
                decoded.error = Error.INVALID_LINE_1;
                return decoded;
            }
        }



        return decoded;
    }

    private Decoded processLine2(Decoded decoded, Type line2, String barcode) {
        return decoded;
    }

    private class Decoded{
        Type type;
        Error error;
        String barcode;
        String labelerId;
        char check;
        String product;
    }

    public enum Type{
        CONCATENATED(1),
        LINE_1(2),
        LINE_2(3);

        public int typeCode;

        Type(int typeCode) {
            this.typeCode = typeCode;
        }

    }

    public enum Error {
//        BARCODE_NOT_A_STRING,
        EMPTY_BARCODE,
        BARCODE_NOT_HIBC,
        INVALID_BARCODE,
        INVALID_DATE,
        EMPTY_CHECK_CHARACTER,
        EMPTY_LINK_CHARACTER,
        INVALID_QUANTITY,
        INVALID_LINE_1

//        public String errorCode;
//
//        Error(String errorCode) {
//            this.errorCode = errorCode;
//        }
    }

}
