package hibcDecoder;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public class HIBC {
    // TODO
    @JsMethod
    public static native HIBC decode(String barcode);

    @JsMethod
    public static native HIBC processLine1(String decoded, int type, String barcode);

    @JsMethod
    public static native HIBC processLine2(String decoded, int type, String barcode);

    @JsMethod
    public static native HIBC decodeLotSerialCheckLink(String string, int barcodeType, String propertyName, boolean hasQty);

    @JsMethod
    public static native HIBC extractMomentFromString(Object object, String stringProperty, String momentProperty);

    @JsMethod
    public static native HIBC extractQuantityFromString(Object object, String string, String quantityProperty);

    @JsMethod
    public static native HIBC isMatch(String line1, String line2);

    @JsMethod
    public static native HIBC matchesLetters(char character);

    @JsMethod
    public static native HIBC matchesNumbers(char character);

    /*
     function decode(barcode) {
         function processLine1(decoded, t, barcode) {
   function processLine2(decoded, type, barcode) {
        function decodeLotSerialCheckLink(string, barcodeType, propertyName, hasQty) {
     function extractMomentFromString(object, stringProperty, momentProperty)
          function extractQuantityFromString(object, string, quantityProperty) {
    function isMatch(line1, line2) {
    function matchesLetters(character) {
    function matchesNumbers(character) {
    */



}
