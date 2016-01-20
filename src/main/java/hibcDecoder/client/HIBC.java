package hibcDecoder.client;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public class HIBC {

    @JsMethod(namespace = "HIBC")
    public static native HIBC decode(String barcode);

    @JsMethod(namespace = "HIBC")
    public static native HIBC isMatch(String line1, String line2);

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
